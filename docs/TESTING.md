# Testing and coverage

Run the host option tests, installer tests, and line and branch coverage gates:

```bash
bash test.sh
bash scripts/test-python-coverage.sh
bash scripts/test-coverage.sh
```

The gates require 100% line and branch coverage for the isolated setting resolver, validator, and standalone installer. Android framework lifecycle and drawing are not represented in the host coverage percentage. Validate those through APK inspection, emulator interaction, and available physical TV checks.

To build and install a local APK:

```bash
bash build.sh
python3 install.py --serial TV_IP:5555 --apk build/neon-corridor.apk
```

Device verification should cover settings focus and persistence, provider discovery and update, DreamService activation, pause/resume, remote exit, install/update/uninstall, and restoration of any device settings changed during the test. Use an explicit serial and record device, OS/API, resolution, and APK hash. Current evidence is in [device verification](VERIFICATION.md).
