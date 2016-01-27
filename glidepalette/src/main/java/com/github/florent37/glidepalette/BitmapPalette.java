package com.github.florent37.glidepalette;

import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.v4.util.Pair;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by florentchampigny on 16/07/15.
 */
public abstract class BitmapPalette {

    private static final String TAG = "BitmapPalette";

    public interface CallBack {
        void onPaletteLoaded(Palette palette);
    }

    @IntDef({Profile.VIBRANT, Profile.VIBRANT_DARK, Profile.VIBRANT_LIGHT,
            Profile.MUTED, Profile.MUTED_DARK, Profile.MUTED_LIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Profile {
        int VIBRANT = 0;
        int VIBRANT_DARK = 1;
        int VIBRANT_LIGHT = 2;
        int MUTED = 3;
        int MUTED_DARK = 4;
        int MUTED_LIGHT = 5;
    }

    @IntDef({Swatch.RGB, Swatch.TITLE_TEXT_COLOR, Swatch.BODY_TEXT_COLOR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Swatch {
        int RGB = 0;
        int TITLE_TEXT_COLOR = 1;
        int BODY_TEXT_COLOR = 2;
    }

    private static final Map<Bitmap, Palette> CACHE = new WeakHashMap<>();

    protected String url;

    protected LinkedList<PaletteTarget> targets = new LinkedList<>();
    protected ArrayList<BitmapPalette.CallBack> callbacks = new ArrayList<>();

    protected BitmapPalette() {
    }

    public BitmapPalette use(@Profile int paletteProfile) {
        this.targets.add(new PaletteTarget(paletteProfile));
        return this;
    }

    protected BitmapPalette intoBackground(View view, @Swatch int paletteSwatch) {
        assertTargetsIsNotEmpty();

        this.targets.getLast().targetsBackground.add(new Pair<>(view, paletteSwatch));
        return this;
    }

    protected BitmapPalette intoTextColor(TextView textView, @Swatch int paletteSwatch) {
        assertTargetsIsNotEmpty();

        this.targets.getLast().targetsText.add(new Pair<>(textView, paletteSwatch));
        return this;
    }

    protected BitmapPalette crossfade(boolean crossfade) {
        assertTargetsIsNotEmpty();

        this.targets.getLast().targetCrossfade = crossfade;
        return this;
    }

    protected BitmapPalette crossfade(boolean crossfade, int crossfadeSpeed) {
        assertTargetsIsNotEmpty();

        this.targets.getLast().targetCrossfadeSpeed = crossfadeSpeed;
        return this.crossfade(crossfade);
    }

    private void assertTargetsIsNotEmpty() {
        if (this.targets.isEmpty()) {
            throw new UnsupportedOperationException("You must specify a palette with use(Profile.Profile)");
        }
    }

    protected BitmapPalette intoCallBack(BitmapPalette.CallBack callBack) {
        if (callBack != null)
            callbacks.add(callBack);
        return this;
    }


    //region apply

    /**
     * Apply the Palette Profile & Swatch to our current targets
     *
     * @param palette  the palette to apply
     * @param cacheHit true if the palette was retrieved from the cache, else false
     */
    protected void apply(Palette palette, boolean cacheHit) {

        for (CallBack c : callbacks) {
            c.onPaletteLoaded(palette);
        }

        for (PaletteTarget target : targets) {
            Palette.Swatch swatch = null;
            switch (target.paletteProfile) {
                case Profile.VIBRANT:
                    swatch = palette.getVibrantSwatch();
                    break;
                case Profile.VIBRANT_DARK:
                    swatch = palette.getDarkVibrantSwatch();
                    break;
                case Profile.VIBRANT_LIGHT:
                    swatch = palette.getLightVibrantSwatch();
                    break;
                case Profile.MUTED:
                    swatch = palette.getMutedSwatch();
                    break;
                case Profile.MUTED_DARK:
                    swatch = palette.getDarkMutedSwatch();
                    break;
                case Profile.MUTED_LIGHT:
                    swatch = palette.getLightMutedSwatch();
                    break;
            }

            if (swatch != null) {
                for (Pair<View, Integer> t : target.targetsBackground) {
                    int color = getColor(swatch, t.second);
                    //Only crossfade if we're not coming from a cache hit.
                    if (!cacheHit && target.targetCrossfade) {
                        crossfadeTargetBackground(target, t, color);
                    } else {
                        t.first.setBackgroundColor(color);
                    }
                }

                for (Pair<TextView, Integer> t : target.targetsText) {
                    int color = getColor(swatch, t.second);
                    t.first.setTextColor(color);
                }

                target.clear();
                this.callbacks = null;
            }
        }
    }

    private void crossfadeTargetBackground(PaletteTarget target, Pair<View, Integer> t, int newColor) {

        final Drawable oldColor = t.first.getBackground();
        final Drawable[] drawables = new Drawable[2];

        drawables[0] = oldColor != null ? oldColor : new ColorDrawable(t.first.getSolidColor());
        drawables[1] = new ColorDrawable(newColor);
        TransitionDrawable transitionDrawable = new TransitionDrawable(drawables);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            t.first.setBackground(transitionDrawable);
        } else {
            t.first.setBackgroundDrawable(transitionDrawable);
        }
        transitionDrawable.startTransition(target.targetCrossfadeSpeed);
    }

    protected static int getColor(Palette.Swatch swatch, @Swatch int paletteSwatch) {
        if (swatch != null) {
            switch (paletteSwatch) {
                case Swatch.RGB:
                    return swatch.getRgb();
                case Swatch.TITLE_TEXT_COLOR:
                    return swatch.getTitleTextColor();
                case Swatch.BODY_TEXT_COLOR:
                    return swatch.getBodyTextColor();
            }
        } else {
            Log.e(TAG, "error while generating Palette, null palette returned");
        }
        return 0;
    }

    protected void start(final Bitmap bitmap) {
        Palette palette = CACHE.get(bitmap);
        if (palette != null) {
            apply(palette, true);
        } else {
            new Palette.Builder(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    CACHE.put(bitmap, palette);
                    apply(palette, false);
                }
            });
        }
    }
}
