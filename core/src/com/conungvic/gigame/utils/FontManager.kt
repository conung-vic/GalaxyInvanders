package com.conungvic.gigame.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion

object FontManager {
    var astra32: BitmapFont
    var astra45: BitmapFont

    init {
        val texture32 = Texture(Gdx.files.internal("fonts/astro-32.png"))
        astra32 = BitmapFont(Gdx.files.internal("fonts/astro-32.fnt"), TextureRegion(texture32), false)

        val texture45 = Texture(Gdx.files.internal("fonts/astro-45.png"))
        astra45 = BitmapFont(Gdx.files.internal("fonts/astro-45.fnt"), TextureRegion(texture45), false)
    }
}