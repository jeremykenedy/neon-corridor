# Architecture

Neon Corridor is a native Android app with a DreamService, a settings activity, and no third-party runtime dependencies. The renderer uses hardware-accelerated Canvas paths and lines to draw expanding polygon frames, connecting struts, glow, and drifting particles. Geometry is computed for the current viewport, so the scene adapts to TV aspect ratios without a fixed resolution asset.

The frame loop runs while the view is active and stops when the dream or preview pauses. Settings live in default shared preferences. A validated, versioned ContentProvider lets an installed host app discover the supported visual choices and update them without app-private file access.

The Android manifest requests no network permission. The standalone Python installer downloads the signed release only when the user requests installation or update. Pure option resolution and setting validation are isolated for host-side line and branch coverage; Android lifecycle and graphics behavior are checked through builds and device captures.
