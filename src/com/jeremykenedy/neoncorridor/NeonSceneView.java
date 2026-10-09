package com.jeremykenedy.neoncorridor;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.View;

import java.util.Random;

final class NeonSceneView extends View {
    private static final int SIDES = 8;
    private static final int MAX_PARTICLES = 180;
    private static final int[][] PALETTES = {
        {0xff33eaff, 0xff278dff, 0xff86f7ff},
        {0xffff4fc8, 0xff8e62ff, 0xff31c9ff},
        {0xff45ff94, 0xff00d5ac, 0xff86ffcf},
        {0xff24ddff, 0xffff43d0, 0xffffd360, 0xff8d72ff}
    };
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Random random = new Random(82131L);
    private final float[] ringX = new float[SIDES];
    private final float[] ringY = new float[SIDES];
    private final float[] previousX = new float[SIDES];
    private final float[] previousY = new float[SIDES];
    private final Path ringPath = new Path();
    private final float[] particleX = new float[MAX_PARTICLES];
    private final float[] particleY = new float[MAX_PARTICLES];
    private final float[] particleSpeed = new float[MAX_PARTICLES];
    private NeonOptions options;
    private long startedAt;
    private boolean running;
    private float centerX;
    private float centerY;
    private float scale;

    NeonSceneView(Context context) {
        super(context);
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        for (int i = 0; i < particleX.length; i++) {
            particleX[i] = random.nextFloat();
            particleY[i] = random.nextFloat();
            particleSpeed[i] = 0.025f + random.nextFloat() * 0.12f;
        }
        loadOptions();
    }

    void start() {
        if (!running) {
            running = true;
            startedAt = SystemClock.uptimeMillis();
            postInvalidateOnAnimation();
        }
    }

    void stop() {
        running = false;
        removeCallbacks(invalidator);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getWidth() == 0 || getHeight() == 0) return;
        float time = (SystemClock.uptimeMillis() - startedAt) / 1000f;
        canvas.drawColor(0xff03040d);
        drawAmbientGlow(canvas, time);
        drawParticles(canvas, time);
        drawTunnel(canvas, time);
        if (running) postDelayed(invalidator, 33L);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        centerX = width * 0.5f;
        centerY = height * 0.5f;
        scale = Math.min(width, height) * 0.35f;
    }

    private final Runnable invalidator = new Runnable() {
        @Override public void run() { if (running) invalidate(); }
    };

    private void loadOptions() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        options = NeonOptions.resolve(
                preferences.getString("density", "handful"),
                preferences.getString("motion", "normal"),
                preferences.getString("color", "cyan"),
                preferences.getString("brightness", "balanced"),
                preferences.getString("shape", "angular"),
                preferences.getBoolean("randomize_all", false), new Random(System.currentTimeMillis()));
    }

    private void drawAmbientGlow(Canvas canvas, float time) {
        int color = colorFor(0);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(withAlpha(color, (int) (10 * options.brightness)));
        float pulse = 1f + 0.08f * (float) Math.sin(time * 0.24f * options.speed);
        canvas.drawCircle(centerX, centerY, scale * 1.7f * pulse, paint);
    }

    private void drawParticles(Canvas canvas, float time) {
        paint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < options.count * 12; i++) {
            float y = (particleY[i] + time * particleSpeed[i] * options.speed) % 1f;
            float drift = (float) Math.sin(time * 0.19f + i) * 0.018f;
            paint.setColor(withAlpha(colorFor(i), (int) ((22 + (i % 4) * 7) * options.brightness)));
            canvas.drawCircle((particleX[i] + drift) * getWidth(), y * getHeight(), 1f + i % 3, paint);
        }
    }

    private void drawTunnel(Canvas canvas, float time) {
        float rotation = (float) (time * 0.045f * options.speed);
        float movement = (time * 1.6f * options.speed) % 1f;
        int ringCount = 7 + options.count;
        for (int ring = 0; ring < ringCount; ring++) {
            float depth = (ring / (float) ringCount + movement / ringCount) % 1f;
            float perspective = 0.13f + depth * depth * 2.45f;
            float breathing = 1f + 0.035f * (float) Math.sin(time * 0.52f * options.speed + ring * 0.48f);
            float radius = scale * perspective * breathing;
            int alpha = (int) ((92 + depth * 140) * options.brightness);
            int color = colorFor(ring);
            drawRing(canvas, radius, rotation + (float) Math.sin(time * 0.08f) * 0.08f, color, alpha, depth,
                    ring > 0);
        }
    }

    private void drawRing(Canvas canvas, float radius, float rotation, int color, int alpha, float depth,
            boolean connect) {
        float phase = options.rounded ? (float) Math.PI / SIDES : 0f;
        for (int i = 0; i < SIDES; i++) {
            double angle = Math.PI * 2 * i / SIDES + rotation + phase + depth * 0.32f;
            ringX[i] = centerX + (float) Math.cos(angle) * radius;
            ringY[i] = centerY + (float) Math.sin(angle) * radius * 0.66f;
        }
        float width = 0.8f + depth * 1.9f;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(options.rounded ? Paint.Join.ROUND : Paint.Join.MITER);
        paint.setStrokeCap(options.rounded ? Paint.Cap.ROUND : Paint.Cap.SQUARE);
        if (connect) {
            paint.setStrokeWidth(0.8f + depth * 1.1f);
            paint.setColor(withAlpha(color, alpha / 2));
            for (int side = 0; side < SIDES; side++) {
                canvas.drawLine(previousX[side], previousY[side], ringX[side], ringY[side], paint);
            }
        }
        paint.setStrokeWidth(width * 7f);
        paint.setColor(withAlpha(color, alpha / 3));
        paint.setShadowLayer(12f + depth * 18f, 0f, 0f, withAlpha(color, alpha));
        drawPolygon(canvas);
        paint.clearShadowLayer();
        paint.setStrokeWidth(width);
        paint.setColor(withAlpha(color, alpha));
        drawPolygon(canvas);
        paint.setStrokeWidth(Math.max(0.7f, width * 0.35f));
        paint.setColor(withAlpha(0xffe9fbff, alpha / 3));
        for (int side = 0; side < SIDES; side++) {
            if ((side + (int) (depth * 9)) % 2 == 0) {
                canvas.drawLine(ringX[side], ringY[side], ringX[(side + 1) % SIDES], ringY[(side + 1) % SIDES], paint);
            }
        }
        System.arraycopy(ringX, 0, previousX, 0, SIDES);
        System.arraycopy(ringY, 0, previousY, 0, SIDES);
    }

    private void drawPolygon(Canvas canvas) {
        ringPath.reset();
        ringPath.moveTo(ringX[0], ringY[0]);
        for (int i = 1; i < SIDES; i++) ringPath.lineTo(ringX[i], ringY[i]);
        ringPath.close();
        canvas.drawPath(ringPath, paint);
    }

    private int colorFor(int index) {
        int[] colors = PALETTES[options.palette];
        return colors[index % colors.length];
    }

    private static int withAlpha(int color, int alpha) {
        return (color & 0x00ffffff) | (Math.max(0, Math.min(255, alpha)) << 24);
    }
}
