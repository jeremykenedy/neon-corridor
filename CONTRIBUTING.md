# Contributing

Bug reports and focused changes are welcome. For a bug report, include the Android device or emulator, OS/API level, app version, steps to reproduce, and relevant log output. Do not attach private signing keys or personal data.

Before opening a change, run:

```bash
bash test.sh
bash scripts/test-python-coverage.sh
bash scripts/test-coverage.sh
python3 scripts/check-docs.py
python3 scripts/check-privacy.py
```

Keep the renderer animated by default, avoid text and watermarks in the scene, and preserve the no-ads, no-analytics, no-tracking, and no-runtime-network constraints. New user-facing options must be validated, documented, and exposed through the settings provider.
