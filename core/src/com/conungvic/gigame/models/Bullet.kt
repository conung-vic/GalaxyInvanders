package com.conungvic.gigame.models

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.conungvic.gigame.*
import kotlin.experimental.or

enum class BulletOwner {
    PLAYER, ENEMY
}

class Bullet(
    private val game: GIGame,
    x: Float,
    y: Float,
    owner: BulletOwner
): Destroyable {
    private var waitForDestroy = false
    override fun setWaitForDestroy(value: Boolean) { waitForDestroy = value }
    override fun isWaitForDestroy(): Boolean = waitForDestroy
    val body: Body
    val power: Int = 100 + game.player.weaponPower * 5

    init {
        val bulletFixDef = FixtureDef()
        bulletFixDef.filter.categoryBits = if (owner == BulletOwner.PLAYER) PLAYER_BULLET_BIT else ENEMY_BULLET_BIT
        bulletFixDef.filter.maskBits = WALL_BIT or (if (owner == BulletOwner.PLAYER) ENEMY_BIT else PLAYER_BIT)
        val bulletShape = PolygonShape()
        val vertices = arrayOf(
            Vector2(-1f, 2f),
            Vector2(-1f, -2f),
            Vector2(1f, 2f),
            Vector2(1f, -2f)
        )
        bulletShape.set(vertices)
        bulletFixDef.shape = bulletShape

        val bDef = BodyDef()
        bDef.type = BodyDef.BodyType.DynamicBody
        bDef.position.set(x, y)
        body = game.world.createBody(bDef)
        val velocity = if (owner == BulletOwner.PLAYER) 220f * ((10f + game.player.weaponSpeed) / 10f) else -220f
        body.setLinearVelocity(0f, velocity)
        body.createFixture(bulletFixDef).userData = this
    }

    override fun destroy() {
        game.world.destroyBody(body)
    }
}