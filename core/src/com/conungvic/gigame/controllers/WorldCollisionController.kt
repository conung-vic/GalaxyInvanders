package com.conungvic.gigame.controllers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.physics.box2d.*
import com.conungvic.gigame.*
import com.conungvic.gigame.models.Bullet
import com.conungvic.gigame.models.Enemy
import kotlin.experimental.or

private const val s = "Not yet implemented"

class WorldCollisionController(
    val game: GIGame
) : ContactListener {

    override fun beginContact(contact: Contact?) {
        val fixA: Fixture? = contact?.fixtureA
        val fixB: Fixture? = contact?.fixtureB

        val cDefA: Short = fixA?.filterData?.categoryBits ?: 0
        val cDefB: Short = fixB?.filterData?.categoryBits ?: 0

        when (cDefA or cDefB) {
            WALL_BIT or PLAYER_BULLET_BIT -> {
                if (cDefA == PLAYER_BULLET_BIT )
                    markBulletForDestroy(fixA)
                else
                    markBulletForDestroy(fixB)
            }
            WALL_BIT or PLAYER_BIT -> {
                Gdx.app.log("WorldCollisionController", "Player and wall")
            }
            PLAYER_BULLET_BIT or PLAYER_BIT -> {
                Gdx.app.log("WorldCollisionController", "player bullet and player")
            }
            PLAYER_BULLET_BIT or ENEMY_BIT -> {
                if (cDefA == PLAYER_BULLET_BIT) {
                    markBulletForDestroy(fixA)
                    game.enemyController.hit((fixB?.userData as Enemy), (fixA?.userData as Bullet))
                } else {
                    markBulletForDestroy(fixB)
                    game.enemyController.hit((fixA?.userData as Enemy), (fixB?.userData as Bullet))
                }
            }
            else -> {
                Gdx.app.log("WorldCollisionController", "Unknown collision")
            }
        }
    }

    private fun markBulletForDestroy(fix: Fixture?) {
        val bullet: Bullet? = fix?.userData as Bullet?
        bullet?.setWaitForDestroy(true)
    }

    override fun endContact(contact: Contact?) {
        Gdx.app.log("WorldContactListener:endContact", s)
    }

    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {
        Gdx.app.log("WorldContactListener:preSolve", s)
    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {
        Gdx.app.log("WorldContactListener:postSolve", s)
    }


}