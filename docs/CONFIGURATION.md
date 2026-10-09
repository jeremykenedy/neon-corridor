# Configuration

Open Neon Corridor from the TV launcher and use the remote to change a choice. Values are saved locally and loaded when a screensaver session starts. Random values are selected once per session.

| Setting | Options | Default |
| --- | --- | --- |
| Tunnel layers | A few, a handful, many, a ton, random | A handful |
| Movement | Slow, natural, fast, random | Natural |
| Color palette | Electric cyan, synthwave, neon green, prismatic, random | Electric cyan |
| Glow brightness | Dim, balanced, bright, random | Balanced |
| Tunnel geometry | Angular, rounded, random | Angular |
| Randomize all settings each start | Off, on | Off |

The exported provider authority is `com.jeremykenedy.neoncorridor.settings`. Query `content://com.jeremykenedy.neoncorridor.settings/schema` for keys, labels, defaults, types, choices, and random support. Query `content://com.jeremykenedy.neoncorridor.settings/settings` for current values. Update the settings URI with `ContentValues` named `key` and `value`. Unsupported values fail closed. Boolean values use the strings `true` and `false`.

The provider exposes visual preferences only. It does not expose device or identity data, and the app does not make network requests.
