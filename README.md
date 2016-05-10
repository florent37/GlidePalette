GlidePalette
=======

![Alt sample](https://raw.githubusercontent.com/florent37/GlidePalette/master/screenshot/night_small_2.png)

#Download

In your module [![Download](https://api.bintray.com/packages/florent37/maven/GlidePalette/images/download.svg)](https://bintray.com/florent37/maven/GlidePalette/_latestVersion)
```groovy
compile 'com.github.florent37:glidepalette:2.0.0'

compile 'com.github.bumptech.glide:glide:4.0.0-SNAPSHOT'
```

#Sample

```java
Glide.with(this).load(url)
         .listener(GlidePalette.with(url)
               .use(GlidePalette.Profile.MUTED_DARK)
                   .intoBackground(textView)
                   .intoTextColor(textView)

               .use(GlidePalette.Profile.VIBRANT)
                    .intoBackground(titleView, GlidePalette.Swatch.RGB)
                    .intoTextColor(titleView, GlidePalette.Swatch.BODY_TEXT_COLOR)
                    .crossfade(true)
         );
         .into(imageView);
```

##Initialisation

First, init GlidePalette with an **Url**

```java
GlidePalette.with(url)
```

##Palettes

You can successively use following Palettes :

- Palette.VIBRANT
- Palette.VIBRANT_DARK
- Palette.VIBRANT_LIGHT
- Palette.MUTED
- Palette.MUTED_DARK
- Palette.MUTED_LIGHT

```java
.use(GlidePalette.Profile.MUTED_DARK)
```

**Each time you call "use" the next modification will follow this Profile**

```java
.use(GlidePalette.Profile.MUTED_DARK)
    //next operations will use Profile.MUTED_DARK
.use(GlidePalette.Profile.VIBRANT)
    //next operations will use Profile.VIBRANT
```

##Swatches

With the following Swatches

- RGB
- TITLE_TEXT_COLOR
- BODY_TEXT_COLOR

##Targets

Into Backgrounds

```java
.intoBackground(view)
.intoBackground(view,Swatch.RGB)
```

And TextView Color

```java
.intoTextColor(textView)
.intoTextColor(textView,Swatch.TITLE_TEXT_COLOR)
```

with optional Background Crossfade effect
```java
.crossfade(true)
    // will use default 300ms crossfade
.crossfade(true, 1000)
    // specify own crossfade speed in ms
```

#CallBack

Or simply return into CallBack

```java
.intoCallBack(
    new GlidePalette.CallBack() {
          @Override
          public void onPaletteLoaded(Palette palette) {
              //specific task
          }
    })
```


#TODO

#Community

Looking for contributors, feel free to fork !

#Dependencies

- Glide from Bumptech : [https://github.com/bumptech/glide][glide]

#Credits

Author: Florent Champigny www.florentchampigny.com/

<a href="https://plus.google.com/+florentchampigny">
  <img alt="Follow me on Google+"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/gplus.png" />
</a>
<a href="https://twitter.com/florent_champ">
  <img alt="Follow me on Twitter"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/twitter.png" />
</a>
<a href="https://www.linkedin.com/in/florentchampigny">
  <img alt="Follow me on LinkedIn"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/linkedin.png" />
</a>

#License

    Copyright 2015 florent37, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


[snap]: https://oss.sonatype.org/content/repositories/snapshots/
[glide]: https://github.com/bumptech/glide
