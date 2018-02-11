package com.github.florent37.glidepalette.sample;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import android.support.v7.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.florent37.glidepalette.BitmapPalette;
import com.github.florent37.glidepalette.GlidePalette;

public class GlidePaletteMainActivity extends AppCompatActivity {

	public static final String URL = "http://i.huffpost.com/gen/2299606/images/n-STARRY-NIGHT-628x314.jpg";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.glide_palette_activity_main);

		ImageView imageView = (ImageView) findViewById(R.id.image);
		TextView textVibrant = (TextView) findViewById(R.id.textVibrant);
		TextView textVibrantLight = (TextView) findViewById(R.id.textVibrantLight);
		TextView textVibrantDark = (TextView) findViewById(R.id.textVibrantDark);
		TextView textMuted = (TextView) findViewById(R.id.textMuted);
		TextView textMutedLight = (TextView) findViewById(R.id.textMutedLight);
		TextView textMutedDark = (TextView) findViewById(R.id.textMutedDark);

		Glide.with(this).load(URL)
				.listener(GlidePalette.with(URL)
						.use(GlidePalette.Profile.VIBRANT)
						.intoBackground(textVibrant, GlidePalette.Swatch.RGB)
						.intoTextColor(textVibrant, GlidePalette.Swatch.BODY_TEXT_COLOR)
						.crossfade(true)
						.use(GlidePalette.Profile.VIBRANT_DARK)
						.intoBackground(textVibrantDark, GlidePalette.Swatch.RGB)
						.intoTextColor(textVibrantDark, GlidePalette.Swatch.BODY_TEXT_COLOR)
						.crossfade(false)
						.use(GlidePalette.Profile.VIBRANT_LIGHT)
						.intoBackground(textVibrantLight, GlidePalette.Swatch.RGB)
						.intoTextColor(textVibrantLight, GlidePalette.Swatch.BODY_TEXT_COLOR)
						.crossfade(true, 1000)

						.use(GlidePalette.Profile.MUTED)
						.intoBackground(textMuted, GlidePalette.Swatch.RGB)
						.intoTextColor(textMuted, GlidePalette.Swatch.BODY_TEXT_COLOR)
						.use(GlidePalette.Profile.MUTED_DARK)
						.intoBackground(textMutedDark, GlidePalette.Swatch.RGB)
						.intoTextColor(textMutedDark, GlidePalette.Swatch.BODY_TEXT_COLOR)
						.crossfade(true, 2000)
						.use(GlidePalette.Profile.MUTED_LIGHT)
						.intoBackground(textMutedLight, GlidePalette.Swatch.RGB)
						.intoTextColor(textMutedLight, GlidePalette.Swatch.BODY_TEXT_COLOR)

						// optional
						.intoCallBack(new BitmapPalette.CallBack() {
							@Override
							public void onPaletteLoaded(@Nullable Palette palette) {
								//specific task
							}
						})

						// optional
						.setGlideListener(new RequestListener<Drawable>() {
							@Override public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
								return false;
							}

							@Override public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
								return false;
							}
						})

						// optional: do stuff with the builder
						.setPaletteBuilderInterceptor(new BitmapPalette.PaletteBuilderInterceptor() {
							@NonNull
							@Override
							public Palette.Builder intercept(Palette.Builder builder) {
								return builder.resizeBitmapArea(300 * 100);
							}
						})
				)
				.into(imageView);
	}
}