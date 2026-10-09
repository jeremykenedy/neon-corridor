package com.jeremykenedy.neoncorridor;

import java.util.Random;

public final class NeonOptionsTest {
    public static void main(String[] args) {
        check(NeonOptions.countFor("few") == 3, "few rings");
        check(NeonOptions.countFor("handful") == 7, "default rings");
        check(NeonOptions.countFor("many") == 11, "many rings");
        check(NeonOptions.countFor("ton") == 15, "maximum rings");
        check(NeonOptions.countFor("invalid") == 7, "unknown density fallback");
        check(NeonOptions.speedFor("slow") == 0.55f, "slow speed");
        check(NeonOptions.speedFor("normal") == 1.0f, "normal speed");
        check(NeonOptions.speedFor("fast") == 1.65f, "fast speed");
        check(NeonOptions.speedFor("invalid") == 1.0f, "unknown speed fallback");
        check(NeonOptions.paletteFor("cyan") == 0, "cyan palette");
        check(NeonOptions.paletteFor("synthwave") == 1, "synthwave palette");
        check(NeonOptions.paletteFor("green") == 2, "green palette");
        check(NeonOptions.paletteFor("rainbow") == 3, "rainbow palette");
        check(NeonOptions.paletteFor("invalid") == 0, "unknown palette fallback");
        check(NeonOptions.brightnessFor("dim") == 0.55f, "dim brightness");
        check(NeonOptions.brightnessFor("balanced") == 0.78f, "balanced brightness");
        check(NeonOptions.brightnessFor("bright") == 1.0f, "bright brightness");
        check(NeonOptions.brightnessFor("invalid") == 0.78f, "unknown brightness fallback");

        NeonOptions explicit = NeonOptions.resolve("few", "fast", "synthwave", "dim", "rounded", false,
                new Random(1));
        check(explicit.count == 3 && explicit.speed == 1.65f && explicit.palette == 1
                && explicit.brightness == 0.55f && explicit.rounded, "explicit selections");
        NeonOptions invalid = NeonOptions.resolve("x", "x", "x", "x", "x", false, new Random(2));
        check(invalid.count == 3 && invalid.speed == 0.55f && invalid.palette == 0
                && invalid.brightness == 0.55f && !invalid.rounded, "safe selections for invalid values");
        NeonOptions random = NeonOptions.resolve("random", "random", "random", "random", "random", false,
                new Random(4));
        check(random.count >= 3 && random.count <= 15 && random.speed >= 0.55f && random.speed <= 1.65f
                && random.palette >= 0 && random.palette <= 3 && random.brightness >= 0.55f
                && random.brightness <= 1f, "individual random selection ranges");
        NeonOptions all = NeonOptions.resolve("few", "slow", "cyan", "dim", "angular", true, new Random(8));
        check(all.count >= 3 && all.count <= 15 && all.speed >= 0.55f && all.speed <= 1.65f
                && all.palette >= 0 && all.palette <= 3 && all.brightness >= 0.55f
                && all.brightness <= 1f, "randomize all selection ranges");

        check(!SettingsValues.isSupported(null, "cyan"), "null key rejected");
        check(!SettingsValues.isSupported("color", null), "null value rejected");
        check(SettingsValues.isSupported("density", "few"), "few accepted");
        check(SettingsValues.isSupported("density", "handful"), "handful accepted");
        check(SettingsValues.isSupported("density", "many"), "many accepted");
        check(SettingsValues.isSupported("density", "ton"), "ton accepted");
        check(SettingsValues.isSupported("density", "random"), "density random accepted");
        check(!SettingsValues.isSupported("density", "huge"), "invalid density rejected");
        check(SettingsValues.isSupported("motion", "slow"), "slow accepted");
        check(SettingsValues.isSupported("motion", "normal"), "normal accepted");
        check(SettingsValues.isSupported("motion", "fast"), "fast accepted");
        check(SettingsValues.isSupported("motion", "random"), "motion random accepted");
        check(!SettingsValues.isSupported("motion", "instant"), "invalid motion rejected");
        check(SettingsValues.isSupported("color", "cyan"), "cyan accepted");
        check(SettingsValues.isSupported("color", "synthwave"), "synthwave accepted");
        check(SettingsValues.isSupported("color", "green"), "green accepted");
        check(SettingsValues.isSupported("color", "rainbow"), "rainbow accepted");
        check(SettingsValues.isSupported("color", "random"), "color random accepted");
        check(!SettingsValues.isSupported("color", "blue"), "invalid color rejected");
        check(SettingsValues.isSupported("brightness", "dim"), "dim accepted");
        check(SettingsValues.isSupported("brightness", "balanced"), "balanced accepted");
        check(SettingsValues.isSupported("brightness", "bright"), "bright accepted");
        check(SettingsValues.isSupported("brightness", "random"), "brightness random accepted");
        check(!SettingsValues.isSupported("brightness", "high"), "invalid brightness rejected");
        check(SettingsValues.isSupported("shape", "angular"), "angular accepted");
        check(SettingsValues.isSupported("shape", "rounded"), "rounded accepted");
        check(SettingsValues.isSupported("shape", "random"), "shape random accepted");
        check(!SettingsValues.isSupported("shape", "circle"), "invalid shape rejected");
        check(SettingsValues.isSupported("randomize_all", "true"), "randomize all accepted");
        check(SettingsValues.isSupported("randomize_all", "false"), "randomize all disabled");
        check(!SettingsValues.isSupported("randomize_all", "yes"), "invalid boolean rejected");
        check(!SettingsValues.isSupported("unknown", "value"), "unknown setting rejected");
        System.out.println("Neon Corridor settings tests passed.");
    }

    private static void check(boolean result, String message) {
        if (!result) throw new AssertionError(message);
    }
}
