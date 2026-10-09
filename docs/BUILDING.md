# Building

## Requirements

- JDK 17 or later.
- Android SDK platform 36 and build-tools 36.0.0.
- `aapt2`, `javac`, `d8`, `zipalign`, `apksigner`, `keytool`, and OpenSSL.

Set `ANDROID_HOME` if the SDK is not at `~/Library/Android/sdk`, then run:

```bash
bash build.sh
```

The APK and SHA-256 file are written to `build/`. The first build creates a unique signing key and password under `~/.android/`. Back them up securely and retain them for signed updates. They must never be committed. Set `NEON_CORRIDOR_KEYSTORE` and `NEON_CORRIDOR_KEYPASS` to use a protected key in another location. CI builds use disposable credentials and do not publish signed releases.

Set `VERSION_NAME` and `VERSION_CODE` to change release metadata. Verify the final signed APK and hash together before publishing. See [release process](RELEASING.md).

To install a development build, use `python3 install.py --serial TV_IP:5555 --apk build/neon-corridor.apk`. Without `--apk`, the installer fetches and verifies the latest GitHub release.
