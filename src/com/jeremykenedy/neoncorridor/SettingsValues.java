package com.jeremykenedy.neoncorridor;

public final class SettingsValues {
    private SettingsValues() {}

    public static boolean isSupported(String key, String value) {
        if (key == null || value == null) return false;
        if ("density".equals(key)) return oneOf(value, "few", "handful", "many", "ton", "random");
        if ("motion".equals(key)) return oneOf(value, "slow", "normal", "fast", "random");
        if ("color".equals(key)) return oneOf(value, "cyan", "synthwave", "green", "rainbow", "random");
        if ("brightness".equals(key)) return oneOf(value, "dim", "balanced", "bright", "random");
        if ("shape".equals(key)) return oneOf(value, "angular", "rounded", "random");
        if ("randomize_all".equals(key)) return oneOf(value, "true", "false");
        return false;
    }

    private static boolean oneOf(String value, String... allowed) {
        for (String option : allowed) if (option.equals(value)) return true;
        return false;
    }
}
