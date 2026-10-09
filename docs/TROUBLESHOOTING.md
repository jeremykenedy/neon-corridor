# Troubleshooting

## The screensaver is not listed

Confirm the APK installed successfully, then reopen the device's Display, Ambient mode, or Screensaver page. Vendor firmware controls whether DreamService entries appear and where the selection lives.

## The screensaver is listed but does not start

Select Neon Corridor in the device's screensaver settings and check the idle timeout. The installer does not change either setting. Use the in-app preview to distinguish a renderer issue from system activation behavior.

## ADB cannot install the package

Check `adb devices`, approve the TV debugging prompt, and use its exact serial. A signature conflict means the installed app was signed by another key; for a normal update, use the release key used to sign both APKs.

## A settings host rejects an update

Read the provider schema and send a listed key and one of its values to the `settings` URI. Invalid keys and values are rejected.
