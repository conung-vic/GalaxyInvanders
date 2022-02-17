package com.conungvic.gigame.controllers

import com.conungvic.gigame.GIGame
import com.conungvic.gigame.models.GameModel
import com.conungvic.gigame.models.GameState

// each level contains of 2 kind of enemy:
// with the same level and level+1
private val levelMap: Array<Array<Int>> = arrayOf(
    arrayOf(  0,   0,   0,   0),
    arrayOf(  0,   0,   0, 129),
    arrayOf(  0,   0, 129, 195),
    arrayOf(  0,  60, 195, 255),
    arrayOf(195, 195, 231, 255),
    arrayOf(195, 231, 255, 255),
    arrayOf(231, 255, 255, 255)
)
/*
  0 = 0000 0000
 60 = 0011 1100
129 = 1000 0001
195 = 1100 0011
231 = 1110 0111
255 = 1111 1111
 */

class LevelController(val game: GIGame) {

    fun createLevel() {
        GameModel.state = GameState.STARTING
        GameModel.stateTime = 0f
        buildFleet()
    }

    private fun buildFleet() {
        val enemyLevelUpMap = (GameModel.currentLevel-1) % 7
        for (i in 0 .. 3) {
            val levelByte = levelMap[enemyLevelUpMap][i]

            for (j in 0..7) {
                val shift = 1 shl j
                val enemyLevel = GameModel.currentLevel + (if (levelByte and shift == 0) 0 else 1)
                game.enemies.add(game.enemyController.createEnemy(enemyLevel, 100f + j * 60, 270f + i * 70))
            }
        }
    }

    fun checkLevel() {
        if (game.enemies.isEmpty()) {
            clearOrStopObjects()
            GameModel.currentLevel += 1
            createLevel()
        }
    }

    private fun clearOrStopObjects() {
        for (bullet in game.playerController.bullets) {
            bullet.destroy()
        }
        for (bullet in game.enemyController.bullets) {
            bullet.destroy()
        }
        game.gameController.bonuses.forEach { it.body.setLinearVelocity(0f, 0f) }
    }
}