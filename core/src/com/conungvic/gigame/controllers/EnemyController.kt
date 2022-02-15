package com.conungvic.gigame.controllers

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.conungvic.gigame.GIGame
import com.conungvic.gigame.models.Enemy

val enemyBody: MutableMap<Int, Array<Vector2>> = mutableMapOf()
fun initEnemyVertices() {
    val vertices = arrayOf(
        Vector2(-6f, -12.5f),
        Vector2(-10f, -1.5f),
        Vector2(0f, 12.5f),
        Vector2(10f, -1.5f),
        Vector2(6f, -12.5f)
    )
    enemyBody[0] = vertices
    enemyBody[1] = vertices
    enemyBody[2] = vertices
    enemyBody[3] = vertices
    enemyBody[4] = vertices
    enemyBody[5] = vertices
    enemyBody[6] = vertices
}

class EnemyController(
    private val game:GIGame
) {

    fun createEnemy(level: Int, x:Float, y: Float) = Enemy(game, x, y, level % 7, MathUtils.random(0, 2))

}