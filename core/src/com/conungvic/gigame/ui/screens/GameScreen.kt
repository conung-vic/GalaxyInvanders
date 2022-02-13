package com.conungvic.gigame.ui.screens

import com.badlogic.gdx.Gdx
import com.conungvic.gigame.GIGame
import com.conungvic.gigame.ui.scenes.Hud

class GameScreen(game: GIGame) : CommonScreen(game) {

    private val hud = game.batch?.let { Hud(it) }

    override fun update(delta: Float) {
        hud?.update()
        super.update(delta)

    }

    override fun render(delta: Float) {
        super.render(delta)
        hud?.stage?.draw()
    }

    override fun show() {
        Gdx.app.log("GameScreen:show", "Not yet implemented")
    }

    override fun resize(width: Int, height: Int) {
        Gdx.app.log("GameScreen:resize", "Not yet implemented")
    }

    override fun pause() {
        Gdx.app.log("GameScreen:pause", "Not yet implemented")
    }

    override fun resume() {
        Gdx.app.log("GameScreen:resume", "Not yet implemented")
    }

    override fun hide() {
        Gdx.app.log("GameScreen:hide", "Not yet implemented")
    }
}