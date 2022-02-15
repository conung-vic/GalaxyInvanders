package com.conungvic.gigame.ui.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.physics.box2d.Body
import com.conungvic.gigame.GIGame
import com.conungvic.gigame.V_WIDTH
import com.conungvic.gigame.controllers.PlayerController
import com.conungvic.gigame.models.Enemy
import com.conungvic.gigame.models.Player
import com.conungvic.gigame.ui.scenes.Hud

private const val s = "Not yet implemented"

class GameScreen(game: GIGame) : CommonScreen(game) {

    private val playerImage: Sprite
    private val bulletImage: Sprite
    private val hud = Hud(game)
    private val shapeRenderer: ShapeRenderer
    private val enemyImages: MutableList<List<Sprite>> = mutableListOf()

    init {
        playerImage = Sprite(TextureRegion(game.atlas.findRegion("player")))
        bulletImage = Sprite(TextureRegion(game.atlas.findRegion("bullet")))
        for (i in 1..7) {
            val imageName = "alien_%d".format(i)
            val sprites = mutableListOf<Sprite>()
            game.atlas.findRegions(imageName).forEach { it ->
                sprites.add(Sprite(TextureRegion(it)))
            }
            enemyImages.add(sprites)
        }
        shapeRenderer = ShapeRenderer()
        shapeRenderer.projectionMatrix = game.batch.projectionMatrix
        shapeRenderer.setAutoShapeType(true)
    }

    override fun update(delta: Float) {
        hud.update()
        super.update(delta)
        game.playerController.processObjects()
        game.enemyController.processObjects()
        game.playerController.handleInput()
    }

    override fun render(delta: Float) {
        super.render(delta)

        game.batch.begin()
        drawImage(playerImage, game.player.body)
        for (bullet in game.player.bullets) {
            drawImage(bulletImage, bullet.body)
        }

        for (enemy in game.enemies) {
            drawEnemy(enemy)
        }
        game.batch.end()

        b2dr.render(game.world, camera.combined)
        hud.render()
    }

    private fun drawEnemy(enemy: Enemy) {
        val image = enemyImages[enemy.level % 7][enemy.skin]
        drawImage(image, enemy.body)
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