package com.conungvic.gigame.models

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.conungvic.gigame.*
import com.conungvic.gigame.ui.utils.EXPLOSION_1
import kotlin.experimental.or

class Player(game: GIGame): Destroyable {
    private val game: GIGame
    lateinit var body: Body

    private var waitForDestroy = false
    override fun setWaitForDestroy(value: Boolean) { waitForDestroy = value }
    override fun isWaitForDestroy(): Boolean = waitForDestroy

    var life: Int = 3
    var weaponLevel: Int = 1
    var weaponPower: Int = 1
    var weaponSpeed: Float = 1.0f

    init {
        this.game = game
        definePlayer()
    }

    private fun definePlayer() {
        val bDef = BodyDef()
        bDef.position.set(V_WIDTH / 2, 45f)
        bDef.type = BodyDef.BodyType.DynamicBody

        body = game.world.createBody(bDef)
        val fDef = FixtureDef()
        fDef.filter.categoryBits = PLAYER_BIT
        fDef.filter.maskBits = WALL_BIT or ENEMY_BIT or BONUS_BIT or ENEMY_BULLET_BIT
        val playerShape = PolygonShape()
        val vertices = arrayOf(
            Vector2(0f, -36f),
            Vector2(-24.5f, -9f),
            Vector2(0f, 36f),
            Vector2(24.5f, -9f)
        )
        playerShape.set(vertices)
        fDef.shape = playerShape
        body.createFixture(fDef).userData = this
    }

    fun hit() {
        for (bullet in game.playerController.bullets) {
            bullet.setWaitForDestroy(true)
        }
        for (bullet in game.enemyController.bullets) {
            bullet.setWaitForDestroy(true)
        }
        game.gameController.bonuses.forEach { it.body.setLinearVelocity(0f, 0f) }
        body.setLinearVelocity(0f, 0f)
        GameModel.state = GameState.PLAYER_DIED
        GameModel.stateTime = 0f
        life--
        val snd = game.assetManager.get(EXPLOSION_1, Sound::class.java)
        snd.play()
        weaponLevel = GameModel.currentLevel
        weaponPower = 1
        weaponSpeed = 1.0f
    }

    fun update() {
        if (GameModel.state == GameState.PLAYER_DIED) {
            body.setLinearVelocity(0f, 0f)
            game.enemyController.clearBullets()
            game.playerController.clearBullets()
        }
    }

    override fun destroy() {
        game.world.destroyBody(body)
    }
}