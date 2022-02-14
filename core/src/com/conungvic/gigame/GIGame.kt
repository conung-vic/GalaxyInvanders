package com.conungvic.gigame

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.conungvic.gigame.ui.screens.TitleScreen
import com.conungvic.gigame.ui.utils.GIAssetManager

//const val V_WIDTH = 900f
//const val V_HEIGHT = 510f

const val V_WIDTH = 1600f
const val V_HEIGHT = 900f

const val PPM = 100f

class GIGame : Game(){
    val assetManager: GIAssetManager = GIAssetManager()
    var atlas: TextureAtlas = TextureAtlas()
    var batch: SpriteBatch? = null

    override fun create() {
        val atlasData: TextureAtlas.TextureAtlasData = TextureAtlas.TextureAtlasData()
        atlasData.load(
            Gdx.files.internal("sprites/atlas/ships.atlas"),
            Gdx.files.internal("sprites/atlas/"),
            false
        )
        atlas.load(atlasData)
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