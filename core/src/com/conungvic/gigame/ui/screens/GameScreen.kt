package com.conungvic.gigame.ui.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.physics.box2d.Body
import com.conungvic.gigame.GIGame
import com.conungvic.gigame.V_WIDTH
import com.conungvic.gigame.controllers.PlayerController
import com.conungvic.gigame.models.Bullet
import com.conungvic.gigame.models.Player
import com.conungvic.gigame.ui.scenes.Hud

private const val s = "Not yet implemented"

class GameScreen(game: GIGame) : CommonScreen(game) {

    private val player = Player(game)
    private val playerImage: Sprite
    private val bulletImage: Sprite
    private val hud = Hud(game, player)
    private val playerController = PlayerController(game, player)
    private val shapeRenderer: ShapeRenderer

    init {
        playerImage = Sprite(TextureRegion(game.atlas.findRegion("player")))
        bulletImage = Sprite(TextureRegion(game.atlas.findRegion("bullet")))
        shapeRenderer = ShapeRenderer()
        shapeRenderer.projectionMatrix = game.batch.projectionMatrix
        shapeRenderer.setAutoShapeType(true)    }

    override fun update(delta: Float) {
        hud.update()
        super.update(delta)
        playerController.processObjects()
        playerController.handleInput()
    }

    override fun render(delta: Float) {
        super.render(delta)

        game.batch.begin()
        drawImage(playerImage, player.body)
        for (bullet in player.bullets) {
            drawBullet(bullet)
        }
        game.batch.end()

//        b2dr.render(game.world, camera.combined)
        hud.render()
    }

    private fun drawBullet(bullet: Bullet) {
        drawImage(bulletImage, bullet.body)
    }

    private fun drawImage(image: Sprite, body: Body) {
        val x: Float = (body.position?.x ?: (V_WIDTH / 2)) - image.width / 2
        val y: Float = (body.position?.y ?: 60f) - image.height / 2
        game.batch.draw(image, x, y)
    }

    override fun show() {
        Gdx.app.log("GameScreen:show", s)
    }

    override fun resize(width: Int, height: Int) {
        Gdx.app.log("GameScreen:resize", s)
    }

    override fun pause() {
        Gdx.app.log("GameScreen:pause", s)
    }

    override fun resume() {
        Gdx.app.log("GameScreen:resume", s)
    }

    override fun hide() {
        Gdx.app.log("GameScreen:hide", s)
    }
}