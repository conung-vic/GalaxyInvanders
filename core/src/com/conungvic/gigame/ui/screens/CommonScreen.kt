package com.conungvic.gigame.ui.screens

import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.conungvic.gigame.GIGame
import com.conungvic.gigame.V_HEIGHT
import com.conungvic.gigame.V_WIDTH
import com.conungvic.gigame.models.GameModel
import com.conungvic.gigame.ui.utils.BACKGROUND_TEMPLATE

abstract class CommonScreen(game: GIGame) : Screen {
    protected val viewport: Viewport
    protected val stage: Stage
    protected val game: GIGame
    protected var back: Texture? = null
    private val backNum = MathUtils.random(1, 12)

    init {
        this.game = game
        viewport = FitViewport(V_WIDTH, V_HEIGHT, OrthographicCamera())
        stage = Stage(viewport, game.batch)
    }

    protected open fun update(delta: Float) {
        this.game.assetManager.update()

        GameModel.scores += 1

        val backName = BACKGROUND_TEMPLATE.format(backNum)
        if (this.game.assetManager.isLoaded(backName))
            back = this.game.assetManager.get(backName)
    }

    override fun render(delta: Float) {
        update(delta)

        if (back != null) {
            game.batch?.begin()
            game.batch?.draw(back, 0f, 0f, V_WIDTH, V_HEIGHT)
            game.batch?.end()
        }
    }

    override fun dispose() {
        stage.dispose()
    }
}