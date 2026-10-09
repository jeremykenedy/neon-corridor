package com.jeremykenedy.neoncorridor;

import java.util.Random;

public final class NeonOptions {
    public final int count;
    public final float speed;
    public final int palette;
    public final float brightness;
    public final boolean rounded;

    private NeonOptions(int count, float speed, int palette, float brightness, boolean rounded) {
        this.count = count;
        this.speed = speed;
        this.palette = palette;
        this.brightness = brightness;
        this.rounded = rounded;
    }

    public static NeonOptions resolve(String density, String motion, String color,
            String brightness, String shape, boolean randomizeAll, Random random) {
        String selectedDensity = choose(density, randomizeAll, random, "few", "handful", "many", "ton");
        String selectedMotion = choose(motion, randomizeAll, random, "slow", "normal", "fast");
        String selectedColor = choose(color, randomizeAll, random, "cyan", "synthwave", "green", "rainbow");
        String selectedBrightness = choose(brightness, randomizeAll, random, "dim", "balanced", "bright");
        String selectedShape = choose(shape, randomizeAll, random, "angular", "rounded");
        return new NeonOptions(countFor(selectedDensity), speedFor(selectedMotion),
                paletteFor(selectedColor), brightnessFor(selectedBrightness), "rounded".equals(selectedShape));
    }

    private static String choose(String selected, boolean randomizeAll, Random random, String... values) {
        if (randomizeAll || "random".equals(selected)) {
            return values[random.nextInt(values.length)];
        }
        for (String value : values) {
            if (value.equals(selected)) return selected;
        }
        return values[0];
    }

    static int countFor(String density) {
        if ("few".equals(density)) return 3;
        if ("many".equals(density)) return 11;
        if ("ton".equals(density)) return 15;
        return 7;
    }

    static float speedFor(String motion) {
        if ("slow".equals(motion)) return 0.55f;
        if ("fast".equals(motion)) return 1.65f;
        return 1.0f;
    }

    static int paletteFor(String color) {
        if ("synthwave".equals(color)) return 1;
        if ("green".equals(color)) return 2;
        if ("rainbow".equals(color)) return 3;
        return 0;
    }

    static float brightnessFor(String brightness) {
        if ("dim".equals(brightness)) return 0.55f;
        if ("bright".equals(brightness)) return 1.0f;
        return 0.78f;
    }
}
