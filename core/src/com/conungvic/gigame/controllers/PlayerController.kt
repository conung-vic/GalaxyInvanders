package com.conungvic.gigame.controllers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.conungvic.gigame.GIGame
import com.conungvic.gigame.models.Player
import kotlin.math.absoluteValue

class PlayerController(
    private val game: GIGame,
    private val player: Player
    ) {

    fun handleInput() {
        var xVel = player.body.linearVelocity.x
        val yVel = player.body.linearVelocity.y

        if (
            Gdx.input.isKeyPressed(Input.Keys.LEFT) &&
            xVel >= -500
        ) {
            xVel -= 10
        } else if (
            Gdx.input.isKeyPressed(Input.Keys.RIGHT) &&
            xVel <= 500
        ) {
            xVel += 10
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            player.shoot()
        } else {
            if (xVel.absoluteValue <= 7f) xVel = 0f
            else if (xVel > 0f) xVel -= 5
            else xVel += 5
        }

        player.body.linearVelocity = Vector2(xVel, yVel)
    }

    fun processObjects() {
        val it = player.bullets.iterator()
        while (it.hasNext()) {
            val bullet = it.next()
            if (bullet.waitForDestroy) {
                it.remove()
                game.world.destroyBody(bullet.body)
            }
        }
    }
}