package com.conungvic.gigame.controllers

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.math.MathUtils
import com.conungvic.gigame.GIGame
import com.conungvic.gigame.V_HEIGHT
import com.conungvic.gigame.models.*
import com.conungvic.gigame.ui.screens.EndGameScreen
import com.conungvic.gigame.ui.utils.BONUS
import com.conungvic.gigame.ui.utils.PAUSE
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

class GameController(val game: GIGame) {
    val bonuses: MutableList<Bonus> = mutableListOf()
    private val bonusesToCreate: Queue<Pair<Float, Float>> = LinkedBlockingQueue()
    private var gotHundredBonus: Boolean = false

    fun update(delta: Float) {
        GameModel.stateTime += delta

        when (GameModel.state) {
            GameState.STARTING -> {
                if (GameModel.stateTime >= 2.5f) {
                    GameModel.stateTime = 0f
                    GameModel.state = GameState.PLAYING
                    game.gameController.bonuses.forEach { it.body.setLinearVelocity(0f, -170f) }
                }
            }
            GameState.PLAYER_DIED -> {
                if (GameModel.stateTime >= 2.5f) {
                    GameModel.stateTime = 0f
                    GameModel.state = if (game.player.life == 0) GameState.END_GAME else GameState.STARTING
                }
            }
            GameState.PLAYING -> {
                game.playerController.bullets
                    .filter { it.body.position.y >= V_HEIGHT - 40 }
                    .forEach { it.setWaitForDestroy(true) }
                game.enemyController.bullets
                    .filter { it.body.position.y <= 10f }
                    .forEach { it.setWaitForDestroy(true) }
                bonuses
                    .filter { it.body.position.y >= V_HEIGHT - 40 }
                    .forEach { it.setWaitForDestroy(true) }
            }
            GameState.END_GAME -> {
//                game.screen.dispose()
                game.screen = EndGameScreen(game)
            }
            else -> {
//                Gdx.app.log()
            }
        }

        if (GameModel.killed > 0 &&
            GameModel.killed % 100 == 0 &&
            !gotHundredBonus) {
            val snd = game.assetManager.get(BONUS, Sound::class.java)
            snd.play()
            game.player.life++
            gotHundredBonus = true
        }

        if (GameModel.killed > 0 &&
            GameModel.killed % 100 != 0) {
            gotHundredBonus = false
        }

        val it = bonuses.iterator()
        while (it.hasNext()) {
            val bonus = it.next()
            if (bonus.isWaitForDestroy()) {
                it.remove()
                bonus.destroy()
            }
        }

        while (!bonusesToCreate.isEmpty()) {
            val coord = bonusesToCreate.poll()
            realSpawnBonus(coord.first, coord.second)
        }
    }

    fun togglePause() {
        if (GameModel.state == GameState.PLAYING) {
            GameModel.state = GameState.PAUSED
            game.playerController.bullets.forEach { it.body.setLinearVelocity(0f, 0f) }
            game.enemyController.bullets.forEach { it.body.setLinearVelocity(0f, 0f) }
            bonuses.forEach { it.body.setLinearVelocity(0f, 0f) }
            game.enemies.forEach { it.body.setLinearVelocity(0f, 0f) }
            game.player.body.setLinearVelocity(0f, 0f)
            val snd = game.assetManager.get(PAUSE, Sound::class.java)
            snd.play()
        } else if (GameModel.state == GameState.PAUSED) {
            GameModel.state = GameState.PLAYING
            game.playerController.bullets.forEach { it.body.setLinearVelocity(0f, 220f) }
            game.enemyController.bullets.forEach { it.body.setLinearVelocity(0f, -220f) }
            bonuses.forEach { it.body.setLinearVelocity(0f, -170f) }
            val snd = game.assetManager.get(PAUSE, Sound::class.java)
            snd.play()
        }
    }

    fun spawnBonus(x: Float, y: Float) {
        bonusesToCreate.add(Pair(x, y))
    }

    private fun realSpawnBonus(x: Float, y: Float) {
        if (MathUtils.random(1000) <= 300) {
            when (MathUtils.random(100)) {
                in 0..25 -> bonuses.add(LifeBonus(game, x, y))
                in 26..50 -> bonuses.add(WeaponLevelBonus(game, x, y))
                in 51..75 -> bonuses.add(WeaponPowerBonus(game, x, y))
                in 76..100 -> bonuses.add(WeaponSpeedBonus(game, x, y))
            }
        }
    }

    fun clearOrStopObjects() {
        for (bullet in game.playerController.bullets) {
            bullet.destroy()
        }
        for (bullet in game.enemyController.bullets) {
            bullet.destroy()
        }
        game.gameController.bonuses.forEach { it.body.setLinearVelocity(0f, 0f) }
    }

}