package com.conungvic.gigame.controllers

import com.badlogic.gdx.physics.box2d.*
import com.conungvic.gigame.*
import com.conungvic.gigame.models.Bonus
import com.conungvic.gigame.models.Bullet
import com.conungvic.gigame.models.Enemy
import kotlin.experimental.or

class WorldCollisionController(
    val game: GIGame
) : ContactListener {

    override fun beginContact(contact: Contact?) {
        val fixA: Fixture? = contact?.fixtureA
        val fixB: Fixture? = contact?.fixtureB

        val cDefA: Short = fixA?.filterData?.categoryBits ?: 0
        val cDefB: Short = fixB?.filterData?.categoryBits ?: 0

        when (cDefA or cDefB) {
            PLAYER_BULLET_BIT or ENEMY_BIT -> {
                if (cDefA == PLAYER_BULLET_BIT) {
                    game.enemyController.hit((fixB?.userData as Enemy), (fixA?.userData as Bullet))
                    markBulletForDestroy(fixA)
                } else {
                    game.enemyController.hit((fixA?.userData as Enemy), (fixB?.userData as Bullet))
                    markBulletForDestroy(fixB)
                }
            }
            PLAYER_BIT or BONUS_BIT -> {
                if (cDefA == PLAYER_BIT) {
                    (fixB?.userData as Bonus).apply()
                    (fixB.userData as Bonus).setWaitForDestroy(true)
                } else {
                    (fixA?.userData as Bonus).apply()
                    (fixA.userData as Bonus).setWaitForDestroy(true)
                }
            }
            ENEMY_BULLET_BIT or PLAYER_BIT -> {
                game.playerController.hitPlayer()
            }
            ENEMY_BIT or PLAYER_BIT -> {
                if (cDefA == ENEMY_BIT) {
                    game.enemyController.explode((fixA?.userData as Enemy))
                } else {
                    game.enemyController.explode((fixB?.userData as Enemy))
                }
                game.playerController.hitPlayer()
            }
            else -> {
//                Gdx.app.log("WorldCollisionController", "Unknown collision")
            }
        }
    }

    private fun markBulletForDestroy(fix: Fixture?) {
        val bullet: Bullet? = fix?.userData as Bullet?
        bullet?.setWaitForDestroy(true)
    }

    override fun endContact(contact: Contact?) {
//        Gdx.app.log("WorldCollisionController", "endContact:start")
    }

    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {
//        Gdx.app.log("WorldCollisionController", "preSolve:start")
    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {
//        Gdx.app.log("WorldCollisionController", "postSolve:start")
    }
}