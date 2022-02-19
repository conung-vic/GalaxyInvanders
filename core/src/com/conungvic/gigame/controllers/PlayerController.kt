package com.conungvic.gigame.controllers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.math.Vector2
import com.conungvic.gigame.GIGame
import com.conungvic.gigame.models.Bullet
import com.conungvic.gigame.models.BulletOwner
import com.conungvic.gigame.models.GameModel
import com.conungvic.gigame.models.GameState
import com.conungvic.gigame.ui.utils.EXPLOSION_1
import com.conungvic.gigame.ui.utils.PLAYER_SHOOT
import kotlin.math.absoluteValue

class PlayerController(
    private val game: GIGame
    ) {

    val bullets: MutableList<Bullet> = mutableListOf()

    fun handleInput() {
        if (GameModel.state == GameState.PLAYING) {
            var xVel = game.player.body.linearVelocity.x
            val yVel = 0f

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
                shoot()
            }

            game.player.body.linearVelocity = Vector2(xVel, yVel)
        }
    }

    private fun clearBullets() {
        val it = bullets.iterator()
        while (it.hasNext()) {
            val bullet = it.next()
            if (bullet.isWaitForDestroy()) {
                it.remove()
                bullet.destroy()
            }
        }
    }

    fun update() {
        clearBullets()

        if (GameModel.state == GameState.PLAYER_DIED) {
            game.player.body.setLinearVelocity(0f, 0f)
        }
    }

    private fun createBullet() {
        val bullet = Bullet(game, game.player.body.position.x, game.player.body.position.y+50, BulletOwner.PLAYER)
        bullets.add(bullet)
    }

    private fun shoot() {
        if (bullets.size < game.player.weaponLevel) {
            GameModel.playerShootCount++
            val playerShootSound = this.game.assetManager.get(PLAYER_SHOOT, Sound::class.java)
            playerShootSound.play()
            createBullet()
        }
    }

    fun hitPlayer() {
        for (bullet in game.playerController.bullets) {
            bullet.setWaitForDestroy(true)
        }
        for (bullet in game.enemyController.bullets) {
            bullet.setWaitForDestroy(true)
        }
        game.gameController.bonuses.forEach { it.body.setLinearVelocity(0f, 0f) }
        game.player.body.setLinearVelocity(0f, 0f)
        GameModel.state = GameState.PLAYER_DIED
        GameModel.stateTime = 0f
        game.player.life--
        val snd = game.assetManager.get(EXPLOSION_1, Sound::class.java)
        snd.play()
        game.player.weaponLevel = GameModel.currentLevel
        game.player.weaponPower = 1
        game.player.weaponSpeed = 1.0f
    }
}