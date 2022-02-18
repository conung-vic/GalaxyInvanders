package com.conungvic.gigame.ui.scenes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.conungvic.gigame.GIGame
import com.conungvic.gigame.V_HEIGHT
import com.conungvic.gigame.V_WIDTH
import com.conungvic.gigame.models.GameModel
import com.conungvic.gigame.models.GameState
import com.conungvic.gigame.ui.utils.FontManager

class Hud(game: GIGame): Disposable {
    private val debug = false

    private val game: GIGame
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
    private val lifeCounterLabel: Label
    private val readyGoLabel: Label

    private val bottomLeft = Vector2(0f, 0f)
    private val topLeft = Vector2(0f, V_HEIGHT)
    private val bottomRight = Vector2(V_WIDTH, 0f)
    private val topRight = Vector2(V_WIDTH, V_HEIGHT)

    private var labelStyle = Label.LabelStyle(FontManager.nasa32, Color.RED)
    private val playerImage: Sprite

    init {
        this.game = game
        this.batch = game.batch
        viewport = FitViewport(V_WIDTH, V_HEIGHT, OrthographicCamera())
        stage = Stage(viewport, batch)

        livesLabel = Label("Lives:", labelStyle)
        livesLabel.setPosition(10f, V_HEIGHT - livesLabel.height + 5)

        weaponLabel = Label("Weapon:", labelStyle)
        weaponLabel.setPosition(10f, V_HEIGHT - weaponLabel.height - 30f)

        labelStyle = Label.LabelStyle(FontManager.nasa32, Color.YELLOW)
        weaponLevelLabel = Label("Rocket lvl %d", labelStyle)
        weaponLevelLabel.setPosition(20f + weaponLabel.width, V_HEIGHT - weaponLabel.height - 30f)

        lifeCounterLabel = Label("x %d", labelStyle)
        lifeCounterLabel.setPosition(150f, V_HEIGHT - livesLabel.height + 5)

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

        readyGoLabel = Label("R E A D Y", labelStyle)
        readyGoLabel.setPosition(V_WIDTH / 2 - readyGoLabel.width / 2, V_HEIGHT / 2 - readyGoLabel.height / 2)
        readyGoLabel.isVisible = false

        labelStyle = Label.LabelStyle(FontManager.nasa45, Color.RED)
        levelLabel = Label("Level: ", labelStyle)
        levelLabel.setPosition(V_WIDTH - levelLabel.width - 100, V_HEIGHT - 65)

        labelStyle = Label.LabelStyle(FontManager.nasa45, Color.YELLOW)
        levelNumLabel = Label("111", labelStyle)
        levelNumLabel.setPosition(V_WIDTH - levelNumLabel.width - 20, V_HEIGHT - 65)

        stage.addActor(livesLabel)
        stage.addActor(lifeCounterLabel)
        stage.addActor(weaponLabel)
        stage.addActor(weaponLevelLabel)
        stage.addActor(weaponPowerLabel)
        stage.addActor(weaponSpeedLabel)
        stage.addActor(scoreLabel)
        stage.addActor(levelLabel)
        stage.addActor(levelNumLabel)
        stage.addActor(readyGoLabel)

        shapeRenderer = ShapeRenderer()
        shapeRenderer.projectionMatrix = batch.projectionMatrix
        shapeRenderer.setAutoShapeType(true)

        playerImage = Sprite(TextureRegion(game.atlas.findRegion("player")))
        playerImage.setSize(playerImage.width * 0.4f, playerImage.height * 0.4f)
    }

    fun render() {
        var backColor = Color.BLACK
        if (GameModel.state == GameState.PLAYER_DIED) {
            backColor = when(GameModel.stateTime) {
                in 0.0f.. 0.5f -> Color.RED
                in 0.5f..1.0f -> Color.BLACK
                in 1.0f..1.5f -> Color.RED
                in 1.5f..2.0f -> Color.BLACK
                in 2.0f..2.5f -> Color.RED
                else -> Color.BLACK
            }

        }
        drawBorder(backColor, Color(255f, 165f, 0f, 0f))
        if (debug) drawDebugScreen()
        drawLifeCounter()
        this.stage.draw()
        if (GameModel.state == GameState.STARTING) {
            if (GameModel.stateTime <= 1) {
                readyGoLabel.isVisible = true
                readyGoLabel.setText("R E A D Y")
            } else if (GameModel.stateTime <= 1.5) {
                readyGoLabel.isVisible = false
            }  else if (GameModel.stateTime <= 2.5) {
                readyGoLabel.isVisible = true
                readyGoLabel.setText("G O ! ! !")
            }
        } else if (GameModel.state == GameState.PAUSED) {
            readyGoLabel.isVisible = true
            readyGoLabel.setText("P A U S E")
        } else if (GameModel.state == GameState.END_GAME) {
            readyGoLabel.isVisible = true
            readyGoLabel.setText("T H E    E N D")
        } else {
            readyGoLabel.isVisible = false
        }

    }

    private fun drawBorder(bkColor: Color, fgColor: Color) {
        shapeRenderer.color = bkColor

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.rectLine(bottomLeft, bottomRight, 20f)
        shapeRenderer.rectLine(bottomRight, topRight, 20f)
        shapeRenderer.rectLine(topRight, topLeft, 140f)
        shapeRenderer.rectLine(topLeft, bottomLeft, 20f)

        shapeRenderer.color = fgColor
        shapeRenderer.set(ShapeRenderer.ShapeType.Line)
        shapeRenderer.rect(10f, 10f, V_WIDTH - 20, V_HEIGHT - 80)
        shapeRenderer.end()
    }

    private fun drawLifeCounter() {
        val y = V_HEIGHT - playerImage.height - 5
        this.batch.begin()
        if (game.player.life <= 5) {
            for (i in 1.. game.player.life) {
                playerImage.setPosition(100f + 25 * i, y)
                playerImage.draw(this.batch)
            }
        } else {
            playerImage.setPosition(125f, y)
            playerImage.draw(this.batch)
        }
        this.batch.end()
    }

    private fun drawDebugScreen() {
        shapeRenderer.begin()
        val cyan = Color( 135f, 206f,  235f, 255f)
        val yellow = Color( 255f, 255f,  0f, 255f)
        for (x in 1..V_WIDTH.toInt() step 20) {
            shapeRenderer.color = if ((x-1) % 100 == 0) yellow else cyan
            shapeRenderer.line(x*1.0f, 1f, x*1.0f, V_HEIGHT)
        }
        for (y in 1 .. V_HEIGHT.toInt() step 20) {
            shapeRenderer.color = if ((y-1) % 100 == 0) yellow else cyan
            shapeRenderer.line(1f, y*1.0f, V_WIDTH, y*1.0f)
        }
        shapeRenderer.end()
    }

    fun update() {
        scoreLabel.setText(String.format("%06d", GameModel.scores))
        weaponLevelLabel.setText(String.format("Rocket lvl: %d", game.player.weaponLevel))
        weaponPowerLabel.setText(String.format("WP: %d", game.player.weaponPower))
        weaponSpeedLabel.setText(String.format("WS: %.1f", game.player.weaponSpeed))

        lifeCounterLabel.isVisible = game.player.life > 5
        lifeCounterLabel.setText(String.format("x %d", game.player.life))

        levelNumLabel.setText(String.format("%d", GameModel.currentLevel))
    }

    override fun dispose() {
        shapeRenderer.dispose()
        stage.dispose()
    }
}