package com.conungvic.gigame.ui.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.conungvic.gigame.GIGame
import com.conungvic.gigame.V_HEIGHT
import com.conungvic.gigame.V_WIDTH
import com.conungvic.gigame.ui.utils.FontManager

class TitleScreen(game: GIGame) : CommonScreen(game) {

    init {
        val font32 = LabelStyle(FontManager.astra32, Color.WHITE)
        val playAgainLabel = Label("Click to play", font32)

        val font45 = LabelStyle(FontManager.astra45, Color.WHITE)
        val mainTitleLabel = Label("Galaxy Invanders", font45)

        mainTitleLabel.setPosition(V_WIDTH / 2 - 100, V_HEIGHT / 2 + 50)
        playAgainLabel.setPosition(100f, 100f)

        stage.addActor(mainTitleLabel)
        stage.addActor(playAgainLabel)
    }

    override fun show() {
//        Gdx.app?.log("TitleScreen:show","Not yet implemented")
    }

    private fun handleInput(delta: Float) {
        if (
            Gdx.input.justTouched() ||
            Gdx.input.isKeyJustPressed(Input.Keys.SPACE) ||
            Gdx.input.isKeyJustPressed(Input.Keys.ENTER)
        ) {
            this.game.screen = GameScreen(this.game)
            dispose()
        }
    }

    override fun update(delta: Float) {
        super.update(delta)
        handleInput(delta)
    }

    override fun render(delta: Float) {
        super.render(delta)
        stage.act()
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun pause() {
//        Gdx.app?.log("TitleScreen:pause","Not yet implemented")
    }

    override fun resume() {
//        Gdx.app?.log("TitleScreen:resume","Not yet implemented")
    }

    override fun hide() {
//        Gdx.app?.log("TitleScreen:hide","Not yet implemented")
    }
}