package com.conungvic.gigame.models

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.conungvic.gigame.*
import com.conungvic.gigame.ui.utils.PLAYER_SHOOT

enum class State {
    LIVE, DEAD, INVULNERABLE
}

class Player(game: GIGame) {
    private val game: GIGame
    lateinit var body: Body

    private val bulletFixDef: FixtureDef

    var life: Int = 5
    var weaponLevel: Int = 2
    var weaponPower: Int = 2
    var weaponSpeed: Float = 2.0f
    var state: State = State.LIVE
    val bullets: MutableList<Bullet> = mutableListOf()

    init {
        this.game = game
        definePlayer()

        bulletFixDef = FixtureDef()
        bulletFixDef.filter.categoryBits = PLAYER_BULLET_BIT
        bulletFixDef.filter.maskBits = WALL_BIT
        val bulletShape = PolygonShape()
        val vertices = arrayOf(
            Vector2(-1f, 2f),
            Vector2(-1f, -2f),
            Vector2(1f, 2f),
            Vector2(1f, -2f)
        )
        bulletShape.set(vertices)
        bulletFixDef.shape = bulletShape
    }

    private fun definePlayer() {
        val bDef = BodyDef()
        bDef.position.set(V_WIDTH / 2, 60f)
        bDef.type = BodyDef.BodyType.DynamicBody

        body = game.world.createBody(bDef)
        val fDef = FixtureDef()
        fDef.filter.categoryBits = PLAYER_BIT
        fDef.filter.maskBits = WALL_BIT
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
        val bDef = BodyDef()
        bDef.type = BodyDef.BodyType.DynamicBody
        bDef.position.set(body.position.x, body.position.y + 50)
        val bulletBody = game.world.createBody(bDef)
        bulletBody.setLinearVelocity(0f, 220f)
        val bullet = Bullet(bulletBody)
        bulletBody.createFixture(bulletFixDef).userData = bullet
        bullets.add(bullet)
    }

    fun shoot() {
        if (bullets.size < this.weaponLevel) {
            val playerShootSound = this.game.assetManager.get(PLAYER_SHOOT, Sound::class.java)
            playerShootSound.play()
            createBullet()
        }
    }
}