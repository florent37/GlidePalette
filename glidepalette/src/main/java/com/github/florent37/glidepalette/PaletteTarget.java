package com.github.florent37.glidepalette;

import android.support.v4.util.Pair;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by florentchampigny on 11/05/15.
 */
public class PaletteTarget {

    @BitmapPalette.Profile.PaletteProfile
    protected int paletteProfile = GlidePalette.Profile.VIBRANT;

    protected ArrayList<Pair<View, Integer>> targetsBackground = new ArrayList<>();
    protected ArrayList<Pair<TextView, Integer>> targetsText = new ArrayList<>();

    public PaletteTarget(@BitmapPalette.Profile.PaletteProfile int paletteProfile) {
        this.paletteProfile = paletteProfile;
    }

    public void clear(){
        targetsBackground.clear();
        targetsText.clear();

        targetsBackground = null;
        targetsText = null;
    }
}
