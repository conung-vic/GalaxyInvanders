package com.conungvic.gigame.controllers

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.conungvic.gigame.GIGame
import com.conungvic.gigame.models.Bullet
import com.conungvic.gigame.models.BulletOwner
import com.conungvic.gigame.models.Enemy
import com.conungvic.gigame.models.GameModel
import com.conungvic.gigame.ui.utils.ALIEN_HIT
import com.conungvic.gigame.ui.utils.ALIEN_SHOOT
import com.conungvic.gigame.ui.utils.EXPLOSION_2

val enemyBodyVertices: MutableMap<Int, Array<Vector2>> = mutableMapOf()
fun initEnemyVertices() {
    val vertices0 = arrayOf(
        Vector2(-6f, 12.5f),
        Vector2(-10f, 1.5f),
        Vector2(0f, -12.5f),
        Vector2(10f, 1.5f),
        Vector2(6f, 12.5f)
    )
    val vertices1 = arrayOf(
        Vector2(8f, 14.5f),
        Vector2(11f, 0.5f),
        Vector2(0f, -14.5f),
        Vector2(-11f, 0.5f),
        Vector2(-8f, 14.5f)
    )
    val vertices2 = arrayOf(
        Vector2(3f, 13.5f),
        Vector2(15f, 3.5f),
        Vector2(0f, -13.5f),
        Vector2(-15f, 3.5f),
        Vector2(-3f, 13.5f)
    )
    val vertices3 = arrayOf(
        Vector2(9f, 19.5f),
        Vector2(15f, 11.5f),
        Vector2(15f, 6.5f),
        Vector2(1f, -15.5f),
        Vector2(-15f, 6.5f),
        Vector2(-15f, 11.5f),
        Vector2(-9f, 19.5f)
    )
    val vertices4 = arrayOf(
        Vector2(0.5f, 18f),
        Vector2(21.5f, 16f),
        Vector2(21.5f, 9f),
        Vector2(0.5f, -16f),
        Vector2(-21.5f, 9f),
        Vector2(-21.5f, 16f)
    )
    val vertices5 = arrayOf(
        Vector2(18f, 0f),
        Vector2(22.5f, 17f),
        Vector2(9.5f, -14f),
        Vector2(0f, -24f),
        Vector2(-9.5f, -14f),
        Vector2(-22.5f, 17f),
        Vector2(-18f, 0f)
    )
    val vertices6 = arrayOf(
        Vector2(7f, 27.5f),
        Vector2(24f, 20.5f),
        Vector2(24f, -1.5f),
        Vector2(6f, -26.5f),
        Vector2(-6f, -26.5f),
        Vector2(-24f, -1.5f),
        Vector2(-24f, 20.5f),
        Vector2(-7f, 27.5f)
    )
    enemyBodyVertices[0] = vertices0
    enemyBodyVertices[1] = vertices1
    enemyBodyVertices[2] = vertices2
    enemyBodyVertices[3] = vertices3
    enemyBodyVertices[4] = vertices4
    enemyBodyVertices[5] = vertices5
    enemyBodyVertices[6] = vertices6
}

class EnemyController(
    private val game: GIGame
) {
    val bullets: MutableList<Bullet> = mutableListOf()

    fun createEnemy(level: Int, x: Float, y: Float) = Enemy(game, x, y, level, MathUtils.random(0, 2))

    fun hit(enemy: Enemy, bullet: Bullet) {
        enemy.health -= bullet.power
        if (enemy.health <= 0) {
            val enemyHitSound = this.game.assetManager.get(EXPLOSION_2, Sound::class.java)
            enemyHitSound.play()
            GameModel.scores += enemy.maxHealth
            GameModel.killed++
            game.gameController.spawnBonus(enemy.body.position.x, enemy.body.position.y)
            enemy.setWaitForDestroy(true)
        } else {
            val enemyHitSound = this.game.assetManager.get(ALIEN_HIT, Sound::class.java)
            enemyHitSound.play()
        }
    }

    fun processObjects() {
        val it = game.enemies.iterator()
        while (it.hasNext()) {
            val enemy = it.next()
            if (enemy.isWaitForDestroy()) {
                it.remove()
                enemy.destroy()
            }
        }
    }

    private fun createBullet(enemy: Enemy) {
        val bullet = Bullet(game, enemy.body.position.x, enemy.body.position.y - 30, BulletOwner.ENEMY)
        bullets.add(bullet)
    }

    fun shoot(enemy: Enemy) {
        if (bullets.size < GameModel.currentLevel * 3 + 1) {
            val enemyShootSound = this.game.assetManager.get(ALIEN_SHOOT, Sound::class.java)
            enemyShootSound.play()
            createBullet(enemy)
        }
    }
}