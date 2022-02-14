package com.conungvic.gigame

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.conungvic.gigame.ui.screens.TitleScreen
import com.conungvic.gigame.ui.utils.GIAssetManager

const val V_WIDTH = 1600f
const val V_HEIGHT = 900f

class GIGame : Game(){
    val assetManager: GIAssetManager = GIAssetManager()
    var atlas: TextureAtlas = TextureAtlas()
    var batch: SpriteBatch? = null
    var world: World = World(Vector2(0f, 0f), true)

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