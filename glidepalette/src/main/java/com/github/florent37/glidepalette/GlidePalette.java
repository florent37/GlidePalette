package com.github.florent37.glidepalette;

import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * Created by florentchampigny on 08/05/15.
 */
public class GlidePalette<ModelType> extends BitmapPalette implements RequestListener<ModelType, GlideDrawable> {

    protected RequestListener<ModelType, GlideDrawable> callback;

    protected GlidePalette() {
    }

    public static GlidePalette with(String url) {
        GlidePalette glidePalette = new GlidePalette();
        glidePalette.url = url;
        return glidePalette;
    }

    public GlidePalette use(@Profile.PaletteProfile int paletteProfile) {
        super.use(paletteProfile);
        return this;
    }

    public GlidePalette intoBackground(View view) {
        return this.intoBackground(view, Swatch.RGB);
    }

    public GlidePalette intoBackground(View view, @Swatch.PaletteSwatch int paletteSwatch) {
        super.intoBackground(view, paletteSwatch);
        return this;
    }

    public GlidePalette intoTextColor(TextView textView) {
        return this.intoTextColor(textView, Swatch.TITLE_TEXT_COLOR);
    }

    public GlidePalette intoTextColor(TextView textView, @Swatch.PaletteSwatch int paletteSwatch) {
        super.intoTextColor(textView, paletteSwatch);
        return this;
    }

    public GlidePalette crossfade(boolean crossfade) {
        super.crossfade(crossfade);
        return this;
    }

    public GlidePalette crossfade(boolean crossfade, int crossfadeSpeed) {
        super.crossfade(crossfade, crossfadeSpeed);
        return this;
    }

    public GlidePalette setGlideListener(RequestListener<ModelType, GlideDrawable> listener) {
        this.callback = listener;
        return this;
    }

    public GlidePalette intoCallBack(GlidePalette.CallBack callBack) {
        super.intoCallBack(callBack);
        return this;
    }

    //enregion

    //region Glide.Listener

    @Override
    public boolean onException(Exception e, ModelType model, Target<GlideDrawable> target, boolean isFirstResource) {
        if (this.callback != null)
            this.callback.onException(e, model, target, isFirstResource);
        return false;
    }

    @Override
    public boolean onResourceReady(GlideDrawable resource, ModelType model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
        if (this.callback != null)
            this.callback.onResourceReady(resource, model, target, isFromMemoryCache, isFirstResource);

        if (resource instanceof GlideBitmapDrawable)
            start(GlideBitmapDrawable.class.cast(resource).getBitmap());

        return false;
    }

    //endregion

}
