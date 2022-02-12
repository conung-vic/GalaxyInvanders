package com.conungvic.gigame.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.conungvic.gigame.GIGame

fun main(args: Array<String>) {
    val config = LwjglApplicationConfiguration()
    LwjglApplication(GIGame(), config)
}
