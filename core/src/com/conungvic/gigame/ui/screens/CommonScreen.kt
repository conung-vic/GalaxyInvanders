package com.conungvic.gigame.ui.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.conungvic.gigame.GIGame
import com.conungvic.gigame.V_HEIGHT
import com.conungvic.gigame.V_WIDTH
import com.conungvic.gigame.ui.utils.BACKGROUND_TEMPLATE

abstract class CommonScreen(game: GIGame) : Screen {
    private val viewport: Viewport
    protected val stage: Stage
    protected val game: GIGame
    private var back: Texture? = null
    private val backNum: Int = MathUtils.random(1, 12)
    val b2dr: Box2DDebugRenderer = Box2DDebugRenderer()
    val camera: OrthographicCamera = OrthographicCamera()

    init {
        this.game = game
        viewport = FitViewport(V_WIDTH, V_HEIGHT, camera)
        stage = Stage(viewport, game.batch)
    }

    protected open fun update(delta: Float) {
        if (!this.game.assetManager.isFinished)
            this.game.assetManager.update()

        val fps = Gdx.graphics.framesPerSecond
        val timeStep = if (fps > 60) 1f / fps else 1 / 60f
        this.game.world.step(timeStep, 1, 1)

        val backName = BACKGROUND_TEMPLATE.format(backNum)
        if (this.game.assetManager.isLoaded(backName))
            back = this.game.assetManager.get(backName)
    }

    override fun render(delta: Float) {
        update(delta)
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        if (back != null) {
            game.batch.begin()
            game.batch.draw(back, 0f, 0f, V_WIDTH, V_HEIGHT)
            game.batch.end()
        }
    }

    override fun dispose() {
        b2dr.dispose()
        stage.dispose()
    }
}