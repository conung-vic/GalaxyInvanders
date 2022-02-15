package com.conungvic.gigame.models

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.conungvic.gigame.GIGame
import com.conungvic.gigame.PLAYER_BULLET_BIT
import com.conungvic.gigame.WALL_BIT

class Bullet(
    val game: GIGame,
    player: Player
) {
    var waitForDestroy = false
    val body: Body

    init {
        val bulletFixDef = FixtureDef()
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

        val bDef = BodyDef()
        bDef.type = BodyDef.BodyType.DynamicBody
        bDef.position.set(player.body.position.x, player.body.position.y + 50)
        body = game.world.createBody(bDef)
        body.setLinearVelocity(0f, 220f)
        body.createFixture(bulletFixDef).userData = this
    }



    fun destroy() {
        game.world.destroyBody(body)    }
}