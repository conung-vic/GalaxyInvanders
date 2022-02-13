package com.conungvic.gigame

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.conungvic.gigame.ui.screens.TitleScreen
import com.conungvic.gigame.ui.utils.GIAssetManager

const val V_WIDTH = 900f
const val V_HEIGHT = 510f
const val PPM = 100f

class GIGame : Game(){
    val assetManager: GIAssetManager = GIAssetManager()

    var batch: SpriteBatch? = null

    override fun create() {
        batch = SpriteBatch()

        assetManager.loadBackgrounds()
        assetManager.loadThemes()
        assetManager.loadSoundFx()

        setScreen(TitleScreen(this))
    }

    override fun dispose() {
        batch?.dispose()
        super.dispose()
    }
}