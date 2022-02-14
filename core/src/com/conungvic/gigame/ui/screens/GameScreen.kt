package com.conungvic.gigame.ui.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
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
    private val hud = Hud(game, player)
    private val playerController = PlayerController(game, player)
    private val shapeRenderer: ShapeRenderer

    init {
        playerImage = Sprite(TextureRegion(game.atlas.findRegion("player")))
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

        shapeRenderer.begin()
        for (bullet in player.bullets) {
            drawBullet(bullet)
        }
        shapeRenderer.end()

        game.batch.begin()
        val x: Float = (player.body.position?.x ?: (V_WIDTH / 2)) - playerImage.width / 2
        val y: Float = (player.body.position?.y ?: 60f) - playerImage.height / 2
        game.batch.draw(playerImage, x, y)
        game.batch.end()

//        b2dr.render(game.world, camera.combined)
        hud.render()
    }

    private fun drawBullet(bullet: Bullet) {
        shapeRenderer.rect(bullet.body.position.x, bullet.body.position.y, 4f, 10f)
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