package com.conungvic.gigame.ui.scenes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.conungvic.gigame.V_HEIGHT
import com.conungvic.gigame.V_WIDTH
import com.conungvic.gigame.models.GameModel
import com.conungvic.gigame.models.Player
import com.conungvic.gigame.ui.utils.FontManager

class Hud(batch: SpriteBatch): Disposable {
    private val stage: Stage
    private val viewport: Viewport
    private val batch: SpriteBatch
    private val shapeRenderer: ShapeRenderer

    private val livesLabel: Label
    private val scoreLabel: Label
    private val weaponLabel: Label
    private val weaponLevelLabel: Label
    private val weaponPowerLabel: Label
    private val weaponSpeedLabel: Label
    private val levelLabel: Label
    private val levelNumLabel: Label

    private val bottomLeft = Vector2(0f, 0f)
    private val topLeft = Vector2(0f, V_HEIGHT)
    private val bottomRight = Vector2(V_WIDTH, 0f)
    private val topRight = Vector2(V_WIDTH, V_HEIGHT)

    private var labelStyle = Label.LabelStyle(FontManager.nasa32, Color.RED)

    init {
        this.batch = batch
        viewport = FitViewport(V_WIDTH, V_HEIGHT, OrthographicCamera())
        stage = Stage(viewport, batch)

        livesLabel = Label("Lives:", labelStyle)
        livesLabel.setPosition(10f, V_HEIGHT - livesLabel.height + 5)

        weaponLabel = Label("Weapon:", labelStyle)
        weaponLabel.setPosition(10f, V_HEIGHT - weaponLabel.height - 30f)

        labelStyle = Label.LabelStyle(FontManager.nasa32, Color.YELLOW)
        weaponLevelLabel = Label("Rocket lvl %d", labelStyle)
        weaponLevelLabel.setPosition(20f + weaponLabel.width, V_HEIGHT - weaponLabel.height - 30f)

        labelStyle = Label.LabelStyle(FontManager.nasa14, Color.YELLOW)
        weaponPowerLabel = Label("WP: %d", labelStyle)
        weaponPowerLabel.setPosition(
            40f + weaponLabel.width + weaponLevelLabel.width,
            V_HEIGHT - weaponPowerLabel.height - 35f)

        weaponSpeedLabel = Label("WS: %.1f", labelStyle)
        weaponSpeedLabel.setPosition(
            40f + weaponLabel.width + weaponLevelLabel.width,
            V_HEIGHT - weaponPowerLabel.height * 2 - 32f)

        labelStyle = Label.LabelStyle(FontManager.nasa45, Color(230f, 216f, 186f, 100f))
        scoreLabel = Label("999999", labelStyle)
        scoreLabel.setPosition(V_WIDTH / 2 - scoreLabel.width / 2, V_HEIGHT - 65)

        labelStyle = Label.LabelStyle(FontManager.nasa45, Color.RED)
        levelLabel = Label("Level: ", labelStyle)
        levelLabel.setPosition(V_WIDTH - levelLabel.width - 200, V_HEIGHT - 65)

        labelStyle = Label.LabelStyle(FontManager.nasa45, Color.YELLOW)
        levelNumLabel = Label("111", labelStyle)
        levelNumLabel.setPosition(V_WIDTH - levelLabel.width - levelNumLabel.width, V_HEIGHT - 65)

        stage.addActor(livesLabel)
        stage.addActor(weaponLabel)
        stage.addActor(weaponLevelLabel)
        stage.addActor(weaponPowerLabel)
        stage.addActor(weaponSpeedLabel)
        stage.addActor(scoreLabel)
        stage.addActor(levelLabel)
        stage.addActor(levelNumLabel)

        shapeRenderer = ShapeRenderer()
        shapeRenderer.projectionMatrix = batch.projectionMatrix    }

    fun render() {
        drawBorder(Color.BLACK, Color(255f, 165f, 0f, 0f))
        drawLifeCounter()
        this.stage.draw()
    }

    private fun drawBorder(bkColor: Color, fgColor: Color) {
        shapeRenderer.color = bkColor
        shapeRenderer.setAutoShapeType(true)

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.rectLine(bottomLeft, bottomRight, 20f)
        shapeRenderer.rectLine(bottomRight, topRight, 20f)
        shapeRenderer.rectLine(topRight, topLeft, 140f)
        shapeRenderer.rectLine(topLeft, bottomLeft, 20f)

        shapeRenderer.color = fgColor
        shapeRenderer.set(ShapeRenderer.ShapeType.Line)
//        shapeRenderer.rect(width/2, width/2, V_WIDTH - width, V_HEIGHT - width*3)

        shapeRenderer.end()
    }

    private fun drawLifeCounter() {

    }

    fun update() {
        scoreLabel.setText(String.format("%06d", GameModel.scores))
        weaponLevelLabel.setText(String.format("Rocket lvl: %d", Player.weaponLevel))
        weaponPowerLabel.setText(String.format("WP: %d", Player.weaponPower))
        weaponSpeedLabel.setText(String.format("WS: %.1f", Player.weaponSpeed))
        levelNumLabel.setText(String.format("%d", GameModel.currentLevel))
    }

    override fun dispose() {
        shapeRenderer.dispose()
        stage.dispose()
    }
}