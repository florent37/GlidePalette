package com.github.florent37.glidepalette;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class PaletteTarget {

    @BitmapPalette.Profile
    protected int paletteProfiles[];

    protected ArrayList<Target> targetsBackground = new ArrayList<>();
    protected ArrayList<Target<? extends TextView, ?>> targetsText = new ArrayList<>();

    protected boolean targetCrossfade = false;
    protected int targetCrossfadeSpeed = DEFAULT_CROSSFADE_SPEED;
    protected static final int DEFAULT_CROSSFADE_SPEED = 300;

    public PaletteTarget(@NonNull @BitmapPalette.Profile int... paletteProfiles) {
        this.paletteProfiles = paletteProfiles;
    }

    public void clear() {
        targetsBackground.clear();
        targetsText.clear();

        targetsBackground = null;
        targetsText = null;

        targetCrossfade = false;
        targetCrossfadeSpeed = DEFAULT_CROSSFADE_SPEED;
    }

    public static class Target<V extends View, CG extends ColorGenerator> {
        public final V view;
        public final int paletteSwatch;
        public final CG generator;

        public Target(V view, @BitmapPalette.Swatch int paletteSwatch, CG colorGenerator) {
            this.view = view;
            this.paletteSwatch = paletteSwatch;
            this.generator = colorGenerator;
        }
    }
}
