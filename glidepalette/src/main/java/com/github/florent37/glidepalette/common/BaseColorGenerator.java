package com.github.florent37.glidepalette.common;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.graphics.Palette;
import android.view.View;

import com.github.florent37.glidepalette.BitmapPalette;
import com.github.florent37.glidepalette.ColorGenerator;

/**
 * @author Karim Abou Zeid (kabouzeid)
 */
public abstract class BaseColorGenerator implements ColorGenerator {
    @Override
    public int getColor(View view, @NonNull Palette.Swatch swatch, @BitmapPalette.Swatch int paletteSwatch) {
        switch (paletteSwatch) {
            case BitmapPalette.Swatch.RGB:
                return getColor(swatch);
            case BitmapPalette.Swatch.TITLE_TEXT_COLOR:
                return getTitleTextColor(swatch);
            case BitmapPalette.Swatch.BODY_TEXT_COLOR:
                return getBodyTextColor(swatch);
        }
        return 0;
    }

    @ColorInt
    public int getColor(Palette.Swatch swatch) {
        return swatch.getRgb();
    }

    @ColorInt
    public int getTitleTextColor(Palette.Swatch swatch) {
        return swatch.getTitleTextColor();
    }

    @ColorInt
    public int getBodyTextColor(Palette.Swatch swatch) {
        return swatch.getBodyTextColor();
    }
}
