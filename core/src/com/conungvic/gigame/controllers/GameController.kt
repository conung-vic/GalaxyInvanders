package com.conungvic.gigame.controllers

import com.badlogic.gdx.audio.Sound
import com.conungvic.gigame.GIGame
import com.conungvic.gigame.models.GameModel
import com.conungvic.gigame.models.GameState
import com.conungvic.gigame.ui.utils.PAUSE

class GameController(val game: GIGame) {

    fun update(delta: Float) {
        GameModel.stateTime += delta
        if (GameModel.stateTime >= 2.5f && GameModel.state == GameState.STARTING) {
            GameModel.stateTime = 0f
            GameModel.state = GameState.PLAYING
        }
    }

    fun togglePause() {
        if (GameModel.state == GameState.PLAYING) {
            GameModel.state = GameState.PAUSED
            game.playerController.bullets.forEach { it.body.setLinearVelocity(0f, 0f) }
            game.enemyController.bullets.forEach { it.body.setLinearVelocity(0f, 0f) }
        } else {
            GameModel.state = GameState.PLAYING
            game.playerController.bullets.forEach { it.body.setLinearVelocity(0f, 220f) }
            game.enemyController.bullets.forEach { it.body.setLinearVelocity(0f, -220f) }
        }
        val snd = game.assetManager.get(PAUSE, Sound::class.java)
        snd.play()
    }

}