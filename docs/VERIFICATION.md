# Device verification

This record distinguishes emulator checks from physical device behavior. An emulator launch does not establish vendor screensaver selection, idle activation, physical display composition, or sustained performance.

| Platform | Device and OS | Result |
| --- | --- | --- |
| Android TV emulator | `sdk_google_atv64_arm64`, Android 12/API 31, emulator-5570, 1920 x 1080 | APK installed; preview and settings activities launched; D-pad opened and navigated the density menu; provider schema queried and an external color update was written and read back. Maximum density ran without a crash or ANR. |
| Fire TV | Not tested for this release | Not verified. |
| Google TV | Not tested | Not verified. |

The screenshots at [neon-corridor-preview.png](screenshots/neon-corridor-preview.png) and [settings-android-tv.png](screenshots/settings-android-tv.png) were captured from the running emulator activities. Two preview frames captured two seconds apart differed across 76% of pixels, confirming visible animation. The in-app DreamService preview image was derived from the scene capture and scaled to 1080 pixels wide. It is not evidence that the emulator's system DreamService manager activated the service.

Native 4K composition, frame pacing under sustained playback, power use, and thermal behavior have not been measured. Do not claim those behaviors from the emulator result.
