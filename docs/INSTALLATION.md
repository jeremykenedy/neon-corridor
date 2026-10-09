# Installation, update, and removal

The standalone installer downloads the latest stable signed APK and its `.sha256` file from this repository, verifies the APK bytes, then installs or updates Neon Corridor. The app itself has no network permission or runtime requests. The installer contacts GitHub only when you start an install or update; it sends no device or usage data.

Connect the TV to the same network as this computer, enable ADB debugging, and run:

```bash
python3 install.py --serial TV_IP:5555
```

Use `--yes` for unattended install/update. When more than one Android device is connected, `--serial` is required. For a local build, run `bash build.sh`, then use:

```bash
python3 install.py --serial TV_IP:5555 --apk build/neon-corridor.apk
```

Installation does not select the app as the active screensaver or modify system sleep, update, or power settings. Select Neon Corridor in the device's own screensaver or ambient-mode settings. Menu names and support differ by vendor and OS version.

To remove the app interactively:

```bash
python3 install.py --serial TV_IP:5555 --uninstall
```

Non-interactive removal requires `--uninstall --yes --force`. Removal deletes app preferences. For updates, use the install command, which applies the same package in place and preserves settings.
