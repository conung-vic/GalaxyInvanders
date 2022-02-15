package com.conungvic.gigame.models

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.conungvic.gigame.*
import com.conungvic.gigame.ui.utils.PLAYER_SHOOT
import kotlin.experimental.or

enum class State {
    LIVE, DEAD, INVULNERABLE
}

class Player(game: GIGame): Destroyable {
    private val game: GIGame
    lateinit var body: Body

    private var waitForDestroy = false
    override fun setWaitForDestroy(value: Boolean) { waitForDestroy = value }
    override fun isWaitForDestroy(): Boolean = waitForDestroy

    var life: Int = 5
    var weaponLevel: Int = 20
    var weaponPower: Int = 2
    var weaponSpeed: Float = 2.0f
    var state: State = State.LIVE
    val bullets: MutableList<Bullet> = mutableListOf()

    init {
        this.game = game
        definePlayer()
    }

    private fun definePlayer() {
        val bDef = BodyDef()
        bDef.position.set(V_WIDTH / 2, 60f)
        bDef.type = BodyDef.BodyType.DynamicBody

        body = game.world.createBody(bDef)
        val fDef = FixtureDef()
        fDef.filter.categoryBits = PLAYER_BIT
        fDef.filter.maskBits = WALL_BIT or ENEMY_BIT
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

    private fun createBullet() {
        val bullet = Bullet(game, body.position.x, body.position.y+50)
        bullets.add(bullet)
    }

    fun shoot() {
        if (bullets.size < this.weaponLevel) {
            val playerShootSound = this.game.assetManager.get(PLAYER_SHOOT, Sound::class.java)
            playerShootSound.play()
            createBullet()
        }
    }

    override fun destroy() {
        game.world.destroyBody(body)
    }
}