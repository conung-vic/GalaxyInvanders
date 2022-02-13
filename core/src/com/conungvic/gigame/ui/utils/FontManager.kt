package com.conungvic.gigame.ui.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion

object FontManager {
    var astra32: BitmapFont
    var astra45: BitmapFont
    var nasa10: BitmapFont
    var nasa14: BitmapFont
    var nasa20: BitmapFont
    var nasa32: BitmapFont
    var nasa45: BitmapFont

    init {
        var texture = Texture(Gdx.files.internal("fonts/astro-32.png"))
        astra32 = BitmapFont(Gdx.files.internal("fonts/astro-32.fnt"), TextureRegion(texture), false)

        texture = Texture(Gdx.files.internal("fonts/astro-45.png"))
        astra45 = BitmapFont(Gdx.files.internal("fonts/astro-45.fnt"), TextureRegion(texture), false)

        texture = Texture(Gdx.files.internal("fonts/nasa-20.png"))
        nasa20 = BitmapFont(Gdx.files.internal("fonts/nasa-20.fnt"), TextureRegion(texture), false)

        texture = Texture(Gdx.files.internal("fonts/nasa-32.png"))
        nasa32 = BitmapFont(Gdx.files.internal("fonts/nasa-32.fnt"), TextureRegion(texture), false)

        texture = Texture(Gdx.files.internal("fonts/nasa-14.png"))
        nasa14 = BitmapFont(Gdx.files.internal("fonts/nasa-14.fnt"), TextureRegion(texture), false)

        texture = Texture(Gdx.files.internal("fonts/nasa-10.png"))
        nasa10 = BitmapFont(Gdx.files.internal("fonts/nasa-10.fnt"), TextureRegion(texture), false)

        texture = Texture(Gdx.files.internal("fonts/nasa-45.png"))
        nasa45 = BitmapFont(Gdx.files.internal("fonts/nasa-45.fnt"), TextureRegion(texture), false)
    }
}