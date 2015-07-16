package com.github.florent37.glidepalette.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.florent37.glidepalette.GlidePalette;

//import com.github.florent37.Glidepalette.GlidePalette;


public class MainActivity extends ActionBarActivity {

    ImageView imageView;

    TextView textVibrant;
    TextView textVibrantLight;
    TextView textVibrantDark;
    TextView textMuted;
    TextView textMutedLight;
    TextView textMutedDark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.image);

        textVibrant = (TextView) findViewById(R.id.textVibrant);
        textVibrantLight = (TextView) findViewById(R.id.textVibrantLight);
        textVibrantDark = (TextView) findViewById(R.id.textVibrantDark);
        textMuted = (TextView) findViewById(R.id.textMuted);
        textMutedLight = (TextView) findViewById(R.id.textMutedLight);
        textMutedDark = (TextView) findViewById(R.id.textMutedDark);

        String url = "https://www.lepetiterudit.com/wp-content/uploads/2015/04/starry-night.jpg";
        Glide.with(this).load(url)
                .centerCrop()
                .listener(GlidePalette.with(url)
                        .use(GlidePalette.Profile.VIBRANT)
                            .intoBackground(textVibrant, GlidePalette.Swatch.RGB)
                            .intoTextColor(textVibrant, GlidePalette.Swatch.BODY_TEXT_COLOR)
                        .use(GlidePalette.Profile.VIBRANT_DARK)
                            .intoBackground(textVibrantDark, GlidePalette.Swatch.RGB)
                            .intoTextColor(textVibrantDark, GlidePalette.Swatch.BODY_TEXT_COLOR)
                        .use(GlidePalette.Profile.VIBRANT_LIGHT)
                            .intoBackground(textVibrantLight, GlidePalette.Swatch.RGB)
                            .intoTextColor(textVibrantLight, GlidePalette.Swatch.BODY_TEXT_COLOR)

                        .use(GlidePalette.Profile.MUTED)
                        .intoBackground(textMuted, GlidePalette.Swatch.RGB)
                        .intoTextColor(textMuted, GlidePalette.Swatch.BODY_TEXT_COLOR)
                        .use(GlidePalette.Profile.MUTED_DARK)
                        .intoBackground(textMutedDark, GlidePalette.Swatch.RGB)
                        .intoTextColor(textMutedDark, GlidePalette.Swatch.BODY_TEXT_COLOR)
                        .use(GlidePalette.Profile.MUTED_LIGHT)
                        .intoBackground(textMutedLight, GlidePalette.Swatch.RGB)
                        .intoTextColor(textMutedLight, GlidePalette.Swatch.BODY_TEXT_COLOR)

                        .intoCallBack(new GlidePalette.CallBack() {
                            @Override
                            public void onPaletteLoaded(Palette palette) {
                                //specific task
                            }
                        }))
                .into(imageView);
    }

}
