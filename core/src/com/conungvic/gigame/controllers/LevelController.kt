package com.conungvic.gigame.controllers

import com.badlogic.gdx.audio.Sound
import com.conungvic.gigame.GIGame
import com.conungvic.gigame.V_HEIGHT
import com.conungvic.gigame.V_WIDTH
import com.conungvic.gigame.models.Enemy
import com.conungvic.gigame.models.EnemyState
import com.conungvic.gigame.models.GameModel
import com.conungvic.gigame.models.GameState
import com.conungvic.gigame.ui.utils.ALIEN_FLY

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

private const val speedY: Float = -2f
private const val speedX: Float = 25f

class LevelController(val game: GIGame) {

    fun createLevel() {
        GameModel.state = GameState.STARTING
        GameModel.stateTime = 0f
        GameModel.fleetState = EnemyState.STAND
        GameModel.fleetStateTime = 0f
        GameModel.flyngEnemies = 0
        buildFleet()
    }

    private fun buildFleet() {
        val enemyLevelUpMap = (GameModel.currentLevel-1) % 7
        for (i in 0 .. 3) {
            val levelByte = levelMap[enemyLevelUpMap][i]

            for (j in 0..7) {
                val shift = 1 shl j
                val enemyLevel = GameModel.currentLevel + (if (levelByte and shift == 0) 0 else 1)
                game.enemies.add(game.enemyController.createEnemy(enemyLevel, 100f + j * 60, 300f + i * 70))
            }
        }
    }

    fun update(delta: Float) {
        GameModel.fleetStateTime += delta
        if (GameModel.state == GameState.PLAYING) {
            if (GameModel.fleetStateTime > 5f && GameModel.fleetState == EnemyState.STAND) {
                GameModel.fleetState = EnemyState.FLYING_RIGHT
            }
            moveFleet()
        } else {
            game.enemies.forEach { it.body.setLinearVelocity(0f, 0f) }
        }
    }

    private fun getMostLeftX(): Float = game.enemies
        .filter { it.state != EnemyState.FLYING_TO_PLAYER }
        .map { it.body.position.x }
        .min() ?: 10f

    private fun getMostRightX(): Float = game.enemies
        .filter { it.state != EnemyState.FLYING_TO_PLAYER }
        .map { it.body.position.x }
        .max() ?: V_WIDTH

    private fun processMovementVector() {
        val mostLeftEnemyX = getMostLeftX()
        val mostRightEnemyX = getMostRightX()

        if (GameModel.fleetState == EnemyState.FLYING_RIGHT) {
            if (mostRightEnemyX < V_WIDTH - 50) {
                game.enemies.forEach { it.body.setLinearVelocity(speedX, speedY) }
            } else {
                GameModel.fleetState = EnemyState.FLYING_LEFT
            }
        }

        if (GameModel.fleetState == EnemyState.FLYING_LEFT) {
            if (mostLeftEnemyX > 50) {
                game.enemies.forEach { it.body.setLinearVelocity(-speedX, speedY) }
            } else {
                GameModel.fleetState = EnemyState.FLYING_RIGHT
            }
        }
    }

    private fun startEnemy() {
        if (GameModel.flyngEnemies != game.enemies.size) {
            val snd = game.assetManager.get(ALIEN_FLY, Sound::class.java)
            snd.play()
            game.enemies
                .filter { it.state != EnemyState.FLYING_TO_PLAYER }
                .shuffled()
                .first()
                .state = EnemyState.FLYING_TO_PLAYER
        }
    }

    private fun moveFleet() {
        processMovementVector()
        val timeUntilFlights = 5 + GameModel.currentLevel * 5

        if (GameModel.fleetStateTime >= timeUntilFlights) {
            val x = (GameModel.fleetStateTime - timeUntilFlights).toInt() / 5 // every 5 sec after timeUntilFlights
            if (x > GameModel.flyngEnemies ) {
                GameModel.flyngEnemies = x
                startEnemy()
            }
        }

        game.enemies
            .filter { it.state == EnemyState.FLYING_TO_PLAYER }
            .forEach { it.correctFlight() }

        game.enemies
            .filter { it.body.position.y < -10 }
            .forEach { teleport(it) }
    }

    private fun teleport(enemy: Enemy) {
        val newEnemy = Enemy(game, enemy.body.position.x, V_HEIGHT - 20, enemy.level, enemy.skin)
        newEnemy.state = enemy.state
        newEnemy.health = enemy.health
        game.enemies.add(newEnemy)
        enemy.destroy()
    }

    fun checkLevel() {
        if (game.enemies.isEmpty()) {
            game.gameController.clearOrStopObjects()
            GameModel.currentLevel += 1
            createLevel()
        }
    }


}