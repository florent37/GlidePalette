package com.github.florent37.glidepalette;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.graphics.Palette;
import android.view.View;

/**
 * @author Karim Abou Zeid (kabouzeid)
 */
public interface ColorGenerator {
    @ColorInt
    int getColor(View view, @NonNull Palette.Swatch swatch, @BitmapPalette.Swatch int paletteSwatch);
}
