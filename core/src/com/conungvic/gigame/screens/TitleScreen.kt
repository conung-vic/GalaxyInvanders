package com.conungvic.gigame.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.conungvic.gigame.GIGame
import com.conungvic.gigame.V_HEIGHT
import com.conungvic.gigame.V_WIDTH
import com.conungvic.gigame.utils.BACKGROUND_TEMPLATE

class TitleScreen(game: GIGame) : Screen{
    private val viewport: Viewport
    private val stage: Stage
    private val game: GIGame
    private var back: Texture? = null
    private val backNum = MathUtils.random(1, 12)

    init {
        this.game = game
        viewport = FitViewport(V_WIDTH, V_HEIGHT, OrthographicCamera())
        stage = Stage(viewport, game.batch)

        val texture = Texture(Gdx.files.internal("fonts/astro-32.png"))
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        val bFont = BitmapFont(Gdx.files.internal("fonts/astro-32.fnt"), TextureRegion(texture), false)
        val font = LabelStyle(bFont, Color.WHITE)

        val playAgainLabel = Label("Click to play", font)
        val mainTitleLabel = Label("Galaxy Invanders", font)

        mainTitleLabel.setPosition(0f, 0f)
        playAgainLabel.setPosition(100f, 100f)

        stage.addActor(mainTitleLabel)
        stage.addActor(playAgainLabel)
    }

    override fun show() {
        Gdx.app.log("TitleScreen:show","Not yet implemented")
    }

    private fun update(delta: Float) {
        this.game.assetManager.update()

        if (this.game.assetManager.isFinished)
            back = this.game.assetManager.get(BACKGROUND_TEMPLATE.format(backNum))
    }

    override fun render(delta: Float) {
        update(delta)
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        if (back != null) {
            game.batch?.begin()
            game.batch?.draw(back, 0f, 0f)
            game.batch?.end()
        }

        stage.act()
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
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
        stage.dispose()
    }
}