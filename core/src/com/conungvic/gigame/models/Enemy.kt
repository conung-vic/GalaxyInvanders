package com.conungvic.gigame.models

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.conungvic.gigame.ENEMY_BIT
import com.conungvic.gigame.GIGame
import com.conungvic.gigame.PLAYER_BIT
import com.conungvic.gigame.PLAYER_BULLET_BIT
import com.conungvic.gigame.controllers.enemyBodyVertices
import kotlin.experimental.or

class Enemy(
    private val game: GIGame,
    x: Float,
    y: Float,
    val level: Int,
    val skin: Int
) : Destroyable {

    private var waitForDestroy = false
    override fun setWaitForDestroy(value: Boolean) {
        waitForDestroy = value
    }

    override fun isWaitForDestroy(): Boolean = waitForDestroy

    val body: Body
    var health: Int
    val maxHealth: Int

    init {
        val enemyFixtureDef = FixtureDef()

        enemyFixtureDef.filter.categoryBits = ENEMY_BIT
        enemyFixtureDef.filter.maskBits = PLAYER_BIT or PLAYER_BULLET_BIT

        val bDef = BodyDef()
        bDef.type = BodyDef.BodyType.StaticBody
        bDef.position.set(x, y)
        body = game.world.createBody(bDef)

        val enemyShape = PolygonShape()

        enemyShape.set(enemyBodyVertices[level % 7])
        enemyFixtureDef.shape = enemyShape
        body.createFixture(enemyFixtureDef).userData = this

        maxHealth = 100 + (GameModel.currentLevel - 1) * 10 + level * 5
        health = maxHealth
    }

    override fun destroy() {
        game.world.destroyBody(body)
    }
}