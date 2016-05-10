package com.github.florent37.glidepalette;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class GlidePalette<TranscodeType> extends BitmapPalette implements RequestListener<TranscodeType> {

    protected RequestListener<TranscodeType> callback;

    protected GlidePalette() {
    }

    public static GlidePalette<Drawable> with(String url) {
        GlidePalette<Drawable> glidePalette = new GlidePalette<>();
        glidePalette.url = url;
        return glidePalette;
    }

    @SuppressWarnings("unchecked")
    public GlidePalette<GifDrawable> asGif() {
        return (GlidePalette<GifDrawable>) this;
    }

    public GlidePalette<TranscodeType> use(@Profile int paletteProfile) {
        super.use(paletteProfile);
        return this;
    }

    public GlidePalette<TranscodeType> intoBackground(View view) {
        return this.intoBackground(view, Swatch.RGB);
    }

    @Override
    public GlidePalette<TranscodeType> intoBackground(View view, @Swatch int paletteSwatch) {
        super.intoBackground(view, paletteSwatch);
        return this;
    }

    public GlidePalette<TranscodeType> intoTextColor(TextView textView) {
        return this.intoTextColor(textView, Swatch.TITLE_TEXT_COLOR);
    }

    @Override
    public GlidePalette<TranscodeType> intoTextColor(TextView textView, @Swatch int paletteSwatch) {
        super.intoTextColor(textView, paletteSwatch);
        return this;
    }

    @Override
    public GlidePalette<TranscodeType> crossfade(boolean crossfade) {
        super.crossfade(crossfade);
        return this;
    }

    @Override
    public GlidePalette<TranscodeType> crossfade(boolean crossfade, int crossfadeSpeed) {
        super.crossfade(crossfade, crossfadeSpeed);
        return this;
    }

    public GlidePalette<TranscodeType> setGlideListener(RequestListener<TranscodeType> listener) {
        this.callback = listener;
        return this;
    }

    @Override
    public GlidePalette<TranscodeType> intoCallBack(GlidePalette.CallBack callBack) {
        super.intoCallBack(callBack);
        return this;
    }

    @Override
    public GlidePalette<TranscodeType> setPaletteBuilderInterceptor(PaletteBuilderInterceptor interceptor) {
        super.setPaletteBuilderInterceptor(interceptor);
        return this;
    }

    @Override
    public GlidePalette<TranscodeType> skipPaletteCache(boolean skipCache) {
        super.skipPaletteCache(skipCache);
        return this;
    }

    @Override public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<TranscodeType> target, boolean isFirstResource) {
        return this.callback != null && this.callback.onLoadFailed(e, model, target, isFirstResource);
    }

    @Override public boolean onResourceReady(TranscodeType resource, Object model, Target<TranscodeType> target, DataSource dataSource, boolean isFirstResource) {
        boolean callbackResult = this.callback != null && this.callback.onResourceReady(resource, model, target, dataSource, isFirstResource);

        Bitmap b = null;
        if (resource instanceof BitmapDrawable) {
            b = ((BitmapDrawable) resource).getBitmap();
        } else if (resource instanceof GifDrawable) {
            b = ((GifDrawable) resource).getFirstFrame();
        } else if (target instanceof BitmapHolder) {
            b = ((BitmapHolder) target).getBitmap();
        }

        if (b != null) {
            start(b);
        }

        return callbackResult;
    }

    public interface BitmapHolder {
        @Nullable
        Bitmap getBitmap();
    }

}
