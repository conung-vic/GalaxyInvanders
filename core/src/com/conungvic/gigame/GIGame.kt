package com.conungvic.gigame

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import com.conungvic.gigame.controllers.WorldCollisionController
import com.conungvic.gigame.ui.screens.TitleScreen
import com.conungvic.gigame.ui.utils.GIAssetManager
import kotlin.experimental.or

const val V_WIDTH = 1600f
const val V_HEIGHT = 900f

const val WALL_BIT: Short = 1
const val PLAYER_BIT: Short = 2
const val PLAYER_BULLET_BIT: Short = 4

class GIGame : Game(){
    lateinit var batch: SpriteBatch

    val assetManager: GIAssetManager = GIAssetManager()
    var atlas: TextureAtlas = TextureAtlas()
    var world: World = World(Vector2(0f, 0f), true)

    init {
        world.setContactListener(WorldCollisionController())
    }

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

        defineWalls()
    }

    private fun defineWalls() {
        val bDef = BodyDef()
        bDef.type = BodyDef.BodyType.StaticBody

        bDef.position.set(10f, 60f)
        val leftWall = world.createBody(bDef)

        bDef.position.set(V_WIDTH - 10f, 60f)
        val rightWall = world.createBody(bDef)

        val fDef = FixtureDef()
        fDef.filter.categoryBits = WALL_BIT
        fDef.filter.maskBits = PLAYER_BULLET_BIT or PLAYER_BIT

        val shape = PolygonShape()
        val vertices = arrayOf(
            Vector2(-5f, 30f),
            Vector2(-5f, -30f),
            Vector2(5f, 30f),
            Vector2(5f, -30f)
        )
        shape.set(vertices)
        fDef.shape = shape
        leftWall.createFixture(fDef)
        rightWall.createFixture(fDef)

        bDef.position.set(V_WIDTH / 2, V_HEIGHT - 60)
        val topVertices = arrayOf(
            Vector2(-V_WIDTH / 2 + 10f, 10f),
            Vector2(-V_WIDTH / 2 + 10f, -10f),
            Vector2(V_WIDTH / 2 - 10f, -10f),
            Vector2(V_WIDTH / 2 - 10f, 10f)
        )
        shape.set(topVertices)
        fDef.shape = shape
        val topWall = world.createBody(bDef)
        topWall.createFixture(fDef)

        bDef.position.set(V_WIDTH / 2, 0f)
        val bottomWall = world.createBody(bDef)
        bottomWall.createFixture(fDef)
    }

    override fun dispose() {
        batch.dispose()
        super.dispose()
    }
}