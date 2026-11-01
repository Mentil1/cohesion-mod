#!/usr/bin/env python3
"""Compares the files made by datagen (src/main/generated) with the hand written ones (src/main/resources).

  python3 scripts/compare_generated.py                       prints a report, exit 1 if anything differs
  python3 scripts/compare_generated.py --remove-handwritten  deletes the hand written copies that match
"""
import json
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
RES = ROOT / 'src' / 'main' / 'resources'
GEN = ROOT / 'src' / 'main' / 'generated'
COVERED = ['assets/cohesion/blockstates', 'assets/cohesion/models', 'assets/cohesion/items', 'assets/cohesion/lang',
           'data/cohesion/recipe', 'data/cohesion/advancement', 'data/cohesion/loot_table', 'data/minecraft/tags']


def files_in(base):
    found = {}
    for folder in COVERED:
        for p in (base / folder).rglob('*.json'):
            found[p.relative_to(base).as_posix()] = p
    return found


def norm(obj):
    if isinstance(obj, dict):
        out = {}
        for k, v in obj.items():
            if k == 'replace' and v is False:
                continue
            if k == 'random_sequence':
                continue
            out[k] = norm(v)
        if 'id' in out and out.get('count') == 1:
            del out['count']
        return out
    if isinstance(obj, list):
        items = [norm(v) for v in obj]
        if items and all(isinstance(v, str) for v in items):
            return sorted(items)
        return items
    return obj


def load(path):
    return norm(json.loads(path.read_text(encoding='utf-8')))


def short(obj):
    text = json.dumps(obj, sort_keys=True)
    return text if len(text) < 700 else text[:700] + ' ...'


def main():
    generated, written = files_in(GEN), files_in(RES)
    if not generated:
        print('ERROR: datagen produced no files in src/main/generated')
        return 1
    only_gen = sorted(set(generated) - set(written))
    only_hand = sorted(set(written) - set(generated))
    different = [p for p in sorted(set(generated) & set(written)) if load(generated[p]) != load(written[p])]
    same = [p for p in sorted(set(generated) & set(written)) if p not in different]

    print('generated files: %d | hand written files: %d | identical: %d' % (len(generated), len(written), len(same)))
    print('only generated (new): %d | only hand written (not generated): %d | different: %d'
          % (len(only_gen), len(only_hand), len(different)))
    for p in only_gen[:40]:
        print('  only generated:', p)
    for p in only_hand[:40]:
        print('  only hand written:', p)
    for p in different[:15]:
        print('  DIFFERENT:', p)
        print('    generated   :', short(load(generated[p])))
        print('    hand written:', short(load(written[p])))

    clean = not only_gen and not only_hand and not different
    if '--remove-handwritten' in sys.argv:
        if not clean:
            print('Not removing anything: the files do not match yet.')
            return 1
        for p in same:
            written[p].unlink()
        print('Removed %d hand written files.' % len(same))
        return 0
    print('RESULT:', 'identical' if clean else 'NOT identical')
    return 0 if clean else 1


if __name__ == '__main__':
    sys.exit(main())
