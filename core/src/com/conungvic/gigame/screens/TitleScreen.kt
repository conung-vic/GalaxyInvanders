package com.conungvic.gigame.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.conungvic.gigame.GIGame
import com.conungvic.gigame.V_HEIGHT
import com.conungvic.gigame.V_WIDTH

class TitleScreen(game: GIGame) : Screen{
    private val viewport: Viewport
    private val stage: Stage
    private val game: Game

    init {
        this.game = game
        viewport = FitViewport(V_WIDTH, V_HEIGHT, OrthographicCamera())
        stage = Stage(viewport, game.batch)

        val texture = Texture(Gdx.files.internal("fonts/astro-32.png"))
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        val bFont = BitmapFont(Gdx.files.internal("fonts/astro-32.fnt"), TextureRegion(texture), false)
        val font = LabelStyle(bFont, Color.WHITE)
        val table = Table()
        table.center()
        table.setFillParent(true)

        val gameOverLabel = Label("Game Over", font)
        val playAgainLabel = Label("Click to play again", font)

        table.add(gameOverLabel).expandX()
        table.row()

        table.add(playAgainLabel).expandX().padTop(10f)

        stage.addActor(table)
    }

    override fun show() {
        Gdx.app.log("TitleScreen:show","Not yet implemented")
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        Gdx.app.log("TitleScreen:resize","Not yet implemented")
    }

    override fun pause() {
        Gdx.app.log("TitleScreen:pause","Not yet implemented")
    }

    override fun resume() {
        Gdx.app.log("TitleScreen:resume","Not yet implemented")
    }

    override fun hide() {
        Gdx.app.log("TitleScreen:hide","Not yet implemented")
    }

    override fun dispose() {
        Gdx.app.log("TitleScreen:dispose","Not yet implemented")
    }
}