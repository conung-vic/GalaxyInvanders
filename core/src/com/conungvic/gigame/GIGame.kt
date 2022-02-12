package com.conungvic.gigame

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.conungvic.gigame.screens.TitleScreen
import com.conungvic.gigame.utils.GIAssetManager

const val V_WIDTH = 800f
const val V_HEIGHT = 600f
const val PPM = 100f





class GIGame : Game(){
    val assetManager: GIAssetManager = GIAssetManager()

    var batch: SpriteBatch? = null

    override fun create() {
        batch = SpriteBatch()

        assetManager.loadThemes()
        assetManager.loadSoundFx()

        setScreen(TitleScreen(this))
    }

    override fun dispose() {
        batch?.dispose()
        super.dispose()
    }
}