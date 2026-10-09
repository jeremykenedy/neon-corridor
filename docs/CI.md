# Continuous integration

GitHub Actions builds the APK with Android SDK 36, runs host settings and installer tests, enforces 100% line and branch coverage for project-owned pure logic, checks code style, validates docs, and scans for dependency and secret risks. CI uses disposable signing credentials and does not publish a release. Active workflow definitions are in `.github/workflows/`.

The Android app has no third-party runtime dependencies or network permission. The standalone installer contacts GitHub only after a user requests a release download. A provider badge is included only when that service is configured and its check is active.
