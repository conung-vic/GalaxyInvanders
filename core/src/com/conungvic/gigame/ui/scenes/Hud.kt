package com.conungvic.gigame.ui.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.conungvic.gigame.V_HEIGHT
import com.conungvic.gigame.V_WIDTH
import com.conungvic.gigame.models.GameModel

class Hud(batch: SpriteBatch): Disposable {
    val stage: Stage
    private val viewport: Viewport

    val scoreLabel: Label

    init {
        viewport = FitViewport(V_WIDTH, V_HEIGHT, OrthographicCamera())
        stage = Stage(viewport, batch)

        val table = Table()
        table.top()
        table.setFillParent(true)

        scoreLabel = Label(String.format("%06d", GameModel.scores),  Label.LabelStyle(BitmapFont(), Color.CYAN))
        table.add(scoreLabel).expandX()

        stage.addActor(table)
    }

    fun update() {
        scoreLabel.setText(String.format("%06d", GameModel.scores))
    }

    override fun dispose() {
        Gdx.app.log("HUD", "Dispose")
    }
}