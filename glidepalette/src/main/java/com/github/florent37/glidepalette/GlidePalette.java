package com.github.florent37.glidepalette;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class GlidePalette<ModelType, TranscodeType> extends BitmapPalette implements RequestListener<ModelType, TranscodeType> {

    protected RequestListener<ModelType, TranscodeType> callback;
    private PaletteBuilderInterceptor interceptor;

    public GlidePalette<ModelType, TranscodeType> use(@Profile int paletteProfile) {
        super.use(paletteProfile);
        return this;
    }

    public GlidePalette<ModelType, TranscodeType> intoBackground(View view) {
        return this.intoBackground(view, Swatch.RGB);
    }

    public GlidePalette<ModelType, TranscodeType> intoBackground(View view, @Swatch int paletteSwatch) {
        super.intoBackground(view, paletteSwatch);
        return this;
    }

    public GlidePalette<ModelType, TranscodeType> intoTextColor(TextView textView) {
        return this.intoTextColor(textView, Swatch.TITLE_TEXT_COLOR);
    }

    public GlidePalette<ModelType, TranscodeType> intoTextColor(TextView textView, @Swatch int paletteSwatch) {
        super.intoTextColor(textView, paletteSwatch);
        return this;
    }

    public GlidePalette<ModelType, TranscodeType> crossfade(boolean crossfade) {
        super.crossfade(crossfade);
        return this;
    }

    public GlidePalette<ModelType, TranscodeType> crossfade(boolean crossfade, int crossfadeSpeed) {
        super.crossfade(crossfade, crossfadeSpeed);
        return this;
    }

    public GlidePalette<ModelType, TranscodeType> setGlideListener(RequestListener<ModelType, TranscodeType> listener) {
        this.callback = listener;
        return this;
    }

    public GlidePalette<ModelType, TranscodeType> intoCallBack(GlidePalette.CallBack callBack) {
        super.intoCallBack(callBack);
        return this;
    }

    public GlidePalette<ModelType, TranscodeType> setPaletteBuilderInterceptor(PaletteBuilderInterceptor interceptor) {
        this.interceptor = interceptor;
        return this;
    }

    @Override
    public boolean onException(Exception e, ModelType model, Target<TranscodeType> target, boolean isFirstResource) {
        return this.callback != null && this.callback.onException(e, model, target, isFirstResource);
    }

    @Override
    public boolean onResourceReady(TranscodeType resource, ModelType model, Target<TranscodeType> target, boolean isFromMemoryCache, boolean isFirstResource) {
        boolean callbackResult = this.callback != null && this.callback.onResourceReady(resource, model, target, isFromMemoryCache, isFirstResource);

        Bitmap b = null;
        if (resource instanceof Bitmap) {
            b = (Bitmap) resource;
        } else if (resource instanceof GlideBitmapDrawable) {
            ((GlideBitmapDrawable) resource).getBitmap();
        } else if (target instanceof BitmapHolder) {
            b = ((BitmapHolder) target).getBitmap();
        }

        if (b != null) {
            start(b, interceptor);
        }

        return callbackResult;
    }

    public interface BitmapHolder {
        @Nullable
        Bitmap getBitmap();
    }

}
