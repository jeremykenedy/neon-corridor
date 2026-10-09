#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")" && pwd)"
OUT="$ROOT/build/tests"
mkdir -p "$OUT"
javac -source 8 -target 8 -d "$OUT" \
  "$ROOT/src/com/jeremykenedy/neoncorridor/NeonOptions.java" \
  "$ROOT/src/com/jeremykenedy/neoncorridor/SettingsValues.java" \
  "$ROOT/tests/NeonOptionsTest.java"
java -ea -cp "$OUT" com.jeremykenedy.neoncorridor.NeonOptionsTest
python3 -m unittest -v tests.test_installer
