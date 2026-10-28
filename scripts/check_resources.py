#!/usr/bin/env python3
"""Checks that the mod's files line up with each other.

Run it from anywhere:  python3 scripts/check_resources.py
It exits with 1 when something is wrong, so the GitHub build fails early.
"""
import json
import re
import struct
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
SRC = ROOT / 'src'
RES = SRC / 'main' / 'resources'
ASSETS = RES / 'assets' / 'cohesion'
DATA = RES / 'data'
COH = DATA / 'cohesion'
VANILLA_RECIPES = DATA / 'minecraft' / 'recipe'
MOD_BLOCKS = SRC / 'main' / 'java' / 'io' / 'github' / 'mentil1' / 'cohesion' / 'block' / 'ModBlocks.java'
NS = 'cohesion'
PNG_SIGNATURE = b'\x89PNG\r\n\x1a\n'
STAIRS_PATTERN = ['# ', '##']

errors = []
warnings = []


def err(msg):
    errors.append(msg)


def warn(msg):
    warnings.append(msg)


def rel(path):
    return path.relative_to(ROOT).as_posix()


def load_json(path):
    try:
        return json.loads(path.read_text(encoding='utf-8'))
    except Exception as e:
        err('%s: not valid JSON (%s)' % (rel(path), e))
        return None


def local_name(ref):
    """'cohesion:stone_tiles' -> 'stone_tiles'; ids from other namespaces -> None."""
    ns, _, name = ref.partition(':')
    return name if ns == NS and name else None


def find_values(obj, key):
    found = []
    if isinstance(obj, dict):
        for k, v in obj.items():
            if k == key and isinstance(v, str):
                found.append(v)
            else:
                found += find_values(v, key)
    elif isinstance(obj, list):
        for v in obj:
            found += find_values(v, key)
    return found


def recipe_refs(recipe):
    refs = []
    result = recipe.get('result')
    if isinstance(result, dict) and isinstance(result.get('id'), str):
        refs.append(result['id'])
    if isinstance(recipe.get('ingredient'), str):
        refs.append(recipe['ingredient'])
    for v in (recipe.get('key') or {}).values():
        if isinstance(v, str):
            refs.append(v)
    for v in recipe.get('ingredients') or []:
        if isinstance(v, str):
            refs.append(v)
    return refs


def check_stairs_recipe(label, recipe):
    if recipe.get('type') != 'minecraft:crafting_shaped':
        return
    result = recipe.get('result') or {}
    if not str(result.get('id', '')).endswith('_stairs'):
        return
    if recipe.get('pattern') != STAIRS_PATTERN:
        err('%s: stairs must use the 3 block shape' % label)
    if result.get('count') != 4:
        err('%s: a stairs recipe must make 4 stairs' % label)


def read_blocks():
    java = MOD_BLOCKS.read_text(encoding='utf-8')
    decl = re.findall(r'public static final Block (\w+)\s*=\s*register\(\s*"([a-z0-9_]+)"', java)
    names = [n for _, n in decl]
    if not names:
        err('%s: no blocks found' % rel(MOD_BLOCKS))
    for n in set(names):
        if names.count(n) > 1:
            err('block registered twice: ' + n)
    listed = set()
    for body in re.findall(r'List<Block>\s+\w+\s*=\s*List\.of\((.*?)\);', java, re.S):
        listed |= set(re.findall(r'[A-Z][A-Z0-9_]+', body))
    for const, name in decl:
        if const not in listed:
            err('%s: not in any creative tab list in ModBlocks.java (add %s)' % (name, const))
    return names


def check_names():
    for base in (ASSETS, COH):
        for p in base.rglob('*'):
            if p.is_file() and not re.fullmatch(r'[a-z0-9_.]+', p.name):
                err('%s: file names may only use lowercase letters, numbers and underscores' % rel(p))


def check_lang(blocks):
    lang = load_json(ASSETS / 'lang' / 'en_us.json') or {}
    for n in blocks:
        if 'block.cohesion.' + n not in lang:
            err('%s: missing name in en_us.json' % n)
    for key in lang:
        if key.startswith('block.cohesion.') and key.split('.')[-1] not in blocks:
            err('en_us.json: name for a block that does not exist (%s)' % key)
    for java_file in SRC.rglob('*.java'):
        for key in re.findall(r'Component\.translatable\("([^"]+)"\)', java_file.read_text(encoding='utf-8')):
            if key not in lang:
                err('%s: translation key %s is missing in en_us.json' % (rel(java_file), key))


def check_block_files(blocks):
    for n in blocks:
        for p in (ASSETS / 'blockstates' / (n + '.json'), ASSETS / 'items' / (n + '.json'),
                  COH / 'loot_table' / 'blocks' / (n + '.json')):
            if not p.exists():
                err('missing file: ' + rel(p))
        state_path = ASSETS / 'blockstates' / (n + '.json')
        state = load_json(state_path) if state_path.exists() else None
        if not state:
            continue
        if n.endswith('_stairs') and len(state.get('variants', {})) != 40:
            err('%s: stairs blockstate should have 40 variants' % rel(state_path))
        if n.endswith('_slab') and set(state.get('variants', {})) != {'type=bottom', 'type=top', 'type=double'}:
            err('%s: slab blockstate needs bottom, top and double' % rel(state_path))
        if n.endswith('_wall') and len(state.get('multipart', [])) != 9:
            err('%s: wall blockstate should have 9 parts' % rel(state_path))
    for p in (COH / 'loot_table' / 'blocks').glob('*.json'):
        if p.stem not in blocks:
            warn('%s: loot table for a block that does not exist' % rel(p))


def check_models():
    used = set()
    for folder in ('blockstates', 'items'):
        for p in (ASSETS / folder).glob('*.json'):
            data = load_json(p)
            for ref in find_values(data, 'model'):
                name = local_name(ref)
                if name and not (ASSETS / 'models' / (name + '.json')).exists():
                    err('%s: model %s does not exist' % (rel(p), ref))
    for p in (ASSETS / 'models').rglob('*.json'):
        data = load_json(p) or {}
        parent = local_name(str(data.get('parent', '')))
        if parent and not (ASSETS / 'models' / (parent + '.json')).exists():
            err('%s: parent model does not exist' % rel(p))
        for ref in (data.get('textures') or {}).values():
            name = local_name(str(ref))
            if name:
                used.add(name)
                if not (ASSETS / 'textures' / (name + '.png')).exists():
                    err('%s: texture %s.png is missing' % (rel(p), name))
    return used


def check_textures(used):
    tex_dir = ASSETS / 'textures'
    for p in tex_dir.rglob('*.png'):
        data = p.read_bytes()
        if data[:8] != PNG_SIGNATURE or len(data) < 24:
            err('%s: not a real PNG image' % rel(p))
            continue
        width, height = struct.unpack('>II', data[16:24])
        if width != height:
            err('%s: textures must be square (this one is %dx%d)' % (rel(p), width, height))
        if p.relative_to(tex_dir).with_suffix('').as_posix() not in used:
            warn('%s: texture is not used by any model' % rel(p))
    for p in RES.rglob('*.png'):
        if tex_dir not in p.parents:
            err('%s: image is outside assets/cohesion/textures' % rel(p))


def check_recipes(blocks):
    recipes = {}
    for p in (COH / 'recipe').glob('*.json'):
        data = load_json(p)
        if data is not None:
            recipes[p.stem] = data
    unlocks = {}
    for p in (COH / 'advancement' / 'recipes').rglob('*.json'):
        data = load_json(p)
        if data is not None:
            unlocks[p.stem] = data

    made = set()
    for rid, recipe in recipes.items():
        label = 'recipe ' + rid
        unlock = unlocks.get(rid)
        if unlock is None:
            err('%s: has no recipe unlock advancement' % label)
        elif NS + ':' + rid not in (unlock.get('rewards') or {}).get('recipes', []):
            err('%s: its unlock advancement does not give this recipe' % label)
        for ref in recipe_refs(recipe):
            name = local_name(ref)
            if name and name not in blocks:
                err('%s: uses %s, which is not a block of this mod' % (label, ref))
        result = local_name(str((recipe.get('result') or {}).get('id', '')))
        if result:
            made.add(result)
        if recipe.get('type') == 'minecraft:crafting_shaped':
            pattern = recipe.get('pattern') or []
            if len({len(row) for row in pattern}) != 1:
                err('%s: pattern rows have different widths' % label)
            if set(''.join(pattern)) - {' '} != set((recipe.get('key') or {}).keys()):
                err('%s: pattern and key do not match' % label)
        check_stairs_recipe(label, recipe)
    for rid in unlocks:
        if rid not in recipes:
            err('unlock %s has no recipe' % rid)
    for n in blocks:
        if n not in made:
            err('%s: no recipe makes this block' % n)
    return len(recipes), len(unlocks)


def check_vanilla_overrides():
    count = 0
    if VANILLA_RECIPES.exists():
        for p in VANILLA_RECIPES.glob('*.json'):
            data = load_json(p)
            if data is None:
                continue
            count += 1
            check_stairs_recipe('vanilla override ' + p.stem, data)
            if not p.stem.endswith('_stairs'):
                warn('%s: overrides a vanilla recipe that is not a stairs recipe' % rel(p))
    return count


def check_loot(blocks):
    for n in blocks:
        p = COH / 'loot_table' / 'blocks' / (n + '.json')
        if not p.exists():
            continue
        text = json.dumps(load_json(p))
        if any(k in text for k in ('"conditions"', '"functions"', '"function"')):
            err('%s: old loot table format (26.3 uses "condition" and "modifier")' % rel(p))
        if NS + ':' + n not in text:
            err('%s: loot table does not drop the block itself' % rel(p))
        if n.endswith('_slab') and 'set_count' not in text:
            err('%s: a slab must drop 2 when it is a double slab' % rel(p))


def check_tags(blocks):
    tags = DATA / 'minecraft' / 'tags'
    pickaxe = (load_json(tags / 'block' / 'mineable' / 'pickaxe.json') or {}).get('values', [])
    for n in blocks:
        if NS + ':' + n not in pickaxe:
            err('%s: missing from the pickaxe tag' % n)
    for suffix, tag in (('_stairs', 'stairs'), ('_slab', 'slabs'), ('_wall', 'walls')):
        for kind in ('block', 'item'):
            values = (load_json(tags / kind / (tag + '.json')) or {}).get('values', [])
            for n in blocks:
                if n.endswith(suffix) and NS + ':' + n not in values:
                    err('%s: missing from the %s %s tag' % (n, kind, tag))
    for p in tags.rglob('*.json'):
        for value in (load_json(p) or {}).get('values', []):
            name = local_name(value) if isinstance(value, str) else None
            if name and name not in blocks:
                err('%s: lists %s, which is not a block of this mod' % (rel(p), value))


def main():
    blocks = read_blocks()
    check_names()
    check_lang(blocks)
    check_block_files(blocks)
    used = check_models()
    check_textures(used)
    recipe_count, unlock_count = check_recipes(blocks)
    override_count = check_vanilla_overrides()
    check_loot(blocks)
    check_tags(blocks)

    print('Checked %d blocks, %d recipes, %d recipe unlocks and %d vanilla recipe overrides.'
          % (len(blocks), recipe_count, unlock_count, override_count))
    for w in warnings:
        print('warning: ' + w)
    for e in errors:
        print('ERROR: ' + e)
    if errors:
        print('%d problem(s) found.' % len(errors))
        return 1
    print('Everything lines up.')
    return 0


if __name__ == '__main__':
    sys.exit(main())
