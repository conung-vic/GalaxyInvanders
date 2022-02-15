package com.conungvic.gigame.controllers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.conungvic.gigame.GIGame
import kotlin.math.absoluteValue

class PlayerController(
    private val game: GIGame
    ) {

    fun handleInput() {
        var xVel = game.player.body.linearVelocity.x
        val yVel = game.player.body.linearVelocity.y

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
        } else {
            if (xVel.absoluteValue <= 7f) xVel = 0f
            else if (xVel > 0f) xVel -= 5
            else xVel += 5
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.player.shoot()
        }

        game.player.body.linearVelocity = Vector2(xVel, yVel)
    }

    fun processObjects() {
        val it = game.player.bullets.iterator()
        while (it.hasNext()) {
            val bullet = it.next()
            if (bullet.isWaitForDestroy()) {
                it.remove()
                bullet.destroy()
            }
        }
    }
}