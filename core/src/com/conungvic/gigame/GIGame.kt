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
import com.conungvic.gigame.controllers.*
import com.conungvic.gigame.models.Enemy
import com.conungvic.gigame.models.Player
import com.conungvic.gigame.ui.screens.TitleScreen
import com.conungvic.gigame.ui.utils.GIAssetManager

const val V_WIDTH = 900f
const val V_HEIGHT = 600f

const val WALL_BIT: Short = 1
const val PLAYER_BIT: Short = 2
const val PLAYER_BULLET_BIT: Short = 4
const val ENEMY_BIT: Short = 16
const val ENEMY_BULLET_BIT: Short = 32
const val BONUS_BIT: Short = 64

class GIGame : Game(){
    lateinit var batch: SpriteBatch

    val assetManager: GIAssetManager = GIAssetManager()
    var atlas: TextureAtlas = TextureAtlas()
    var world: World = World(Vector2(0f, 0f), true)

    val player: Player = Player(this)
    val enemies: MutableList<Enemy> = mutableListOf()

    val playerController = PlayerController(this)
    val enemyController = EnemyController(this)
    val levelController = LevelController(this)
    val gameController = GameController(this)

    init {
        world.setContactListener(WorldCollisionController(this))
        initEnemyVertices()
        levelController.createLevel()
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
//        GameModel.state = GameState.END_GAME
//        setScreen(EndGameScreen(this))

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
        fDef.filter.maskBits = PLAYER_BIT

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
    }

    override fun dispose() {
        batch.dispose()
        super.dispose()
    }
}