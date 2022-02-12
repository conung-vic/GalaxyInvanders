package com.conungvic.gigame

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.conungvic.gigame.screens.TitleScreen

const val V_WIDTH = 800f
const val V_HEIGHT = 600f
const val PPM = 100f

class GIGame : Game(){
    var batch: SpriteBatch? = null

    override fun create() {
        batch = SpriteBatch()
        setScreen(TitleScreen(this))
    }

    override fun dispose() {
        batch?.dispose()
        super.dispose()
    }
}