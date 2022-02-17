package com.conungvic.gigame.ui.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.conungvic.gigame.GIGame
import com.conungvic.gigame.V_WIDTH
import com.conungvic.gigame.models.Enemy
import com.conungvic.gigame.models.GameModel
import com.conungvic.gigame.models.GameState
import com.conungvic.gigame.ui.scenes.Hud

class GameScreen(game: GIGame) : CommonScreen(game) {

    private val playerImage: Sprite
    private val bulletImage: Sprite
    private val enemyBulletImage: Sprite
    private val hud = Hud(game)
    private val shapeRenderer: ShapeRenderer
    private val enemyImages: MutableList<List<Sprite>> = mutableListOf()

    init {
        playerImage = Sprite(TextureRegion(game.atlas.findRegion("player")))
        bulletImage = Sprite(TextureRegion(game.atlas.findRegion("bullet")))
        enemyBulletImage = Sprite(TextureRegion(game.atlas.findRegion("alien_bullet")))
        for (i in 1..7) {
            val imageName = "alien_%d".format(i)
            val sprites = mutableListOf<Sprite>()
            game.atlas.findRegions(imageName).forEach {
                sprites.add(Sprite(TextureRegion(it)))
            }
            enemyImages.add(sprites)
        }
        shapeRenderer = ShapeRenderer()
        shapeRenderer.projectionMatrix = game.batch.projectionMatrix
        shapeRenderer.setAutoShapeType(true)
    }

    override fun update(delta: Float) {
        game.gameController.update(delta)
        hud.update()
        super.update(delta)

        if (Gdx.input.isKeyJustPressed(Input.Keys.P) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.gameController.togglePause()
        }

        if (GameModel.state == GameState.PLAYING) {
            game.playerController.processObjects()
            game.enemyController.processObjects()
            game.playerController.handleInput()
            game.levelController.checkLevel()
        }
    }

    override fun render(delta: Float) {
        super.render(delta)

        game.batch.begin()
        drawImage(playerImage, game.player.body)
        for (bullet in game.playerController.bullets) {
            drawImage(bulletImage, bullet.body)
        }

        for (bullet in game.enemyController.bullets) {
            drawImage(enemyBulletImage, bullet.body)
        }

        for (enemy in game.enemies) {
            drawEnemy(enemy)
        }
        game.batch.end()

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        for (enemy in game.enemies) {
            drawEnemyHealthBar(enemy)
        }
        shapeRenderer.end()

//        b2dr.render(game.world, camera.combined)
        hud.render()
    }

    private fun getCoordVector(image: Sprite, body: Body): Vector2 {
        val x: Float = (body.position?.x ?: (V_WIDTH / 2)) - image.width / 2
        val y: Float = (body.position?.y ?: 60f) - image.height / 2
        return Vector2(x, y)
    }

    private fun drawEnemyHealthBar(enemy: Enemy) {
        if (enemy.health != enemy.maxHealth) {
            val image = enemyImages[enemy.level % 7][enemy.skin]
            val ratio = image.width * enemy.health / enemy.maxHealth
            val coordVector = getCoordVector(image, enemy.body)
            shapeRenderer.color = Color.RED
            shapeRenderer.rect(coordVector.x, coordVector.y + image.height +2, image.width, 5f)
            shapeRenderer.color = Color.GREEN
            shapeRenderer.rect(coordVector.x, coordVector.y + image.height +2, ratio, 5f)
        }
    }

    private fun drawEnemy(enemy: Enemy) {
        val imgIdx = ((GameModel.currentLevel - 1) % 7 + enemy.level - GameModel.currentLevel) % 7
        val image = enemyImages[imgIdx][enemy.skin]
        drawImage(image, enemy.body)
    }

    private fun drawImage(image: Sprite, body: Body) {
        val coords = getCoordVector(image, body)
        game.batch.draw(image, coords.x, coords.y)
    }

    override fun show() {
//        Gdx.app.log("GameScreen:show", s)
    }

    override fun resize(width: Int, height: Int) {
//        Gdx.app.log("GameScreen:resize", s)
    }

    override fun pause() {
//        Gdx.app.log("GameScreen:pause", s)
    }

    override fun resume() {
//        Gdx.app.log("GameScreen:resume", s)
    }

    override fun hide() {
//        Gdx.app.log("GameScreen:hide", s)
    }

    override fun dispose() {
        shapeRenderer.dispose()
        hud.dispose()
    }
}