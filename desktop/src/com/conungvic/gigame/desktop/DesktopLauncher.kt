package com.conungvic.gigame.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.conungvic.gigame.GIGame
import com.conungvic.gigame.V_HEIGHT
import com.conungvic.gigame.V_WIDTH
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

fun main(args: Array<String>) {
    val config = LwjglApplicationConfiguration()
    config.width = V_WIDTH.toInt()
    config.height = V_HEIGHT.toInt()
    config.title = "Galactic Invaders"
    config.vSyncEnabled = false
    config.foregroundFPS = 120
    config.backgroundFPS = 120
    config.resizable = true
    checkIsUsernameNotLatin()
//    config.fullscreen = true
    LwjglApplication(GIGame(), config)
}

fun checkIsUsernameNotLatin() {
    val name = System.getProperty("user.name")
    if (!isPureAscii(name)) {
        val buf = StandardCharsets.UTF_8.encode(name)
        val sBuf = StringBuffer()
        for (b in buf.array()) {
            if (1*b != 0) {
                val hex = Integer.toHexString(1 * b).substring(6, 8).uppercase()
                sBuf.append("\\x$hex")
            }
        }
        System.setProperty("user.name", sBuf.toString())
    }
}

fun isPureAscii(v: String): Boolean {
    return Charset.forName(StandardCharsets.ISO_8859_1.name()).newEncoder().canEncode(v)
    // or "ISO-8859-1" for ISO Latin 1
    // or StandardCharsets.US_ASCII with JDK1.7+
}