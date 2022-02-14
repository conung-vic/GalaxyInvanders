package com.conungvic.gigame.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.conungvic.gigame.GIGame
import com.conungvic.gigame.V_HEIGHT
import com.conungvic.gigame.V_WIDTH

fun main(args: Array<String>) {
    val config = LwjglApplicationConfiguration()
    config.width = V_WIDTH.toInt()
    config.height = V_HEIGHT.toInt()
    config.title = "Galactic Invaders"
    config.vSyncEnabled = false
    config.foregroundFPS = 120
    config.backgroundFPS = 120
    LwjglApplication(GIGame(), config)
}
