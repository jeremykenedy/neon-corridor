# Releasing

Use Semantic Versioning. Before tagging, finish code, settings, screenshots, docs, repository metadata, local checks, and the signed APK. Run the full GitHub Actions suite on the exact release candidate as the last CI operation before publishing. If the final read-only production sweep requires a code or documentation edit, rerun affected checks and CI before release.

Increment Android version code for every new APK. Keep the package ID and release signing certificate stable so users can update in place. Attach the signed `neon-corridor.apk` and matching `neon-corridor.apk.sha256` to the version tag. Verify the downloaded release hash. Release notes must explain feature changes, fixes, compatibility, commands, and upgrade behavior. Never replace assets under a published tag.
