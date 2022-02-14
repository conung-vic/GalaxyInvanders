package com.conungvic.gigame.ui.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.MassData
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.conungvic.gigame.GIGame
import com.conungvic.gigame.V_WIDTH
import com.conungvic.gigame.models.PlayerModel
import com.conungvic.gigame.ui.scenes.Hud

class GameScreen(game: GIGame) : CommonScreen(game) {

    private val hud = Hud(game)
    private val playerImage: Sprite

    init {
        playerImage = Sprite(TextureRegion(game.atlas.findRegion("player")))
        definePlayer()
    }

    private fun definePlayer() {
        val bDef = BodyDef()
        bDef.position.set(V_WIDTH / 2, 60f)
        bDef.type = BodyDef.BodyType.DynamicBody

        PlayerModel.body = game.world.createBody(bDef)
        val fDef = FixtureDef()
        val playerShape = PolygonShape()
        val vertices = arrayOf(
            Vector2(0f, -36f),
            Vector2(-24.5f, -9f),
            Vector2(0f, 36f),
            Vector2(24.5f, -9f)
        )
        playerShape.set(vertices)
        fDef.shape = playerShape
        PlayerModel.body.createFixture(fDef)

        Gdx.app.log("Init", "Player body created")
    }

    private fun handleInput(delta: Float) {
        var xVel = PlayerModel.body.linearVelocity.x
        val yVel = PlayerModel.body.linearVelocity.y

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && xVel >= -350) {
            xVel -= 10
            Gdx.app.log("Player", "Speed: ${PlayerModel.body.linearVelocity.x}")
            Gdx.app.log("App   ", "FPS  : ${Gdx.graphics.framesPerSecond}")
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && xVel <= 350) {
            xVel += 10
            Gdx.app.log("Player", "Speed: ${PlayerModel.body.linearVelocity.x}")
            Gdx.app.log("App   ", "FPS  : ${Gdx.graphics.framesPerSecond}")
        }

        PlayerModel.body.linearVelocity = Vector2(xVel, yVel)
    }

    override fun update(delta: Float) {
        hud.update()
        super.update(delta)
        handleInput(delta)
    }

    override fun render(delta: Float) {
        super.render(delta)
        hud.render()

        game.batch?.begin()
        val x: Float = (PlayerModel.body.position?.x ?: (V_WIDTH / 2)) - playerImage.width / 2
        val y: Float = (PlayerModel.body.position?.y ?: 60f) - playerImage.height / 2
        game.batch?.draw(playerImage, x, y)
        game.batch?.end()

        b2dr.render(game.world, camera.combined)
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