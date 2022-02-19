package com.conungvic.gigame.ui.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.conungvic.gigame.GIGame
import com.conungvic.gigame.V_HEIGHT
import com.conungvic.gigame.V_WIDTH
import com.conungvic.gigame.models.BonusType
import com.conungvic.gigame.models.GameModel
import com.conungvic.gigame.ui.scenes.Hud
import com.conungvic.gigame.ui.utils.FontManager

class EndGameScreen(game: GIGame): CommonScreen(game) {

    private val hud = Hud(game)

    private val endGameLabel: Label
    private val scoreLabel: Label
    private val shootCountLabel: Label
    private val killCountLabel: Label
    private val accuracyLabel: Label
    private val wsBonusCountLabel: Label
    private val wpBonusCountLabel: Label
    private val wlBonusCountLabel: Label
    private val lfBonusCountLabel: Label
    private val quitLabel: Label

    private val wsSprite: Sprite
    private val wlSprite: Sprite
    private val wpSprite: Sprite
    private val lfSprite: Sprite

    init {
        val ls20 = Label.LabelStyle(FontManager.nasa20,  Color.YELLOW)
        val ls32 = Label.LabelStyle(FontManager.nasa32, Color.YELLOW)
        val ls45 = Label.LabelStyle(FontManager.nasa45, Color.YELLOW)

        endGameLabel = Label("GAME OVER", ls45)
        endGameLabel.width = V_WIDTH
        endGameLabel.setAlignment(Align.center)
        endGameLabel.setPosition(0f, V_HEIGHT / 2 + 100)

        scoreLabel = Label("Your score is %d".format(GameModel.scores), ls32)
        scoreLabel.width = V_WIDTH
        scoreLabel.setAlignment(Align.center)
        scoreLabel.setPosition(0f, V_HEIGHT / 2 + 60)

        val firstShift = 10
        val hDist = 30
        val wDist1 = 200f
        val wDist2 = 550f
        shootCountLabel = Label("You shoot %d times".format(GameModel.playerShootCount), ls20)
        shootCountLabel.setPosition(wDist1, V_HEIGHT / 2 - firstShift)

        killCountLabel = Label("You kill %d enemies".format(GameModel.killed), ls20)
        killCountLabel.setPosition(wDist1, V_HEIGHT / 2 - firstShift - hDist)

        val acc: Int = if (GameModel.playerShootCount != 0) (1.0f * GameModel.killed / GameModel.playerShootCount * 100).toInt() else 0
        accuracyLabel = Label("Your accuracy is %d%%".format(acc), ls20)
        accuracyLabel.setPosition(wDist1, V_HEIGHT / 2 - firstShift - hDist*2)

        wsBonusCountLabel = Label(" was used %d times".format(GameModel.bonuses[BonusType.WEAPON_SPEED]), ls20)
        wsBonusCountLabel.setPosition(wDist2, V_HEIGHT / 2 - firstShift)

        wpBonusCountLabel = Label(" was used %d times".format(GameModel.bonuses[BonusType.WEAPON_POWER]), ls20)
        wpBonusCountLabel.setPosition(wDist2, V_HEIGHT / 2 - firstShift - hDist)

        wlBonusCountLabel = Label(" was used %d times".format(GameModel.bonuses[BonusType.WEAPON_LEVEL]), ls20)
        wlBonusCountLabel.setPosition(wDist2, V_HEIGHT / 2 - firstShift - 2 * hDist)

        lfBonusCountLabel = Label(" was used %d times".format(GameModel.bonuses[BonusType.LIFE]), ls20)
        lfBonusCountLabel.setPosition(wDist2, V_HEIGHT / 2 - firstShift - 3 * hDist)

        quitLabel = Label("Click to quit", ls45)
        quitLabel.width = V_WIDTH
        quitLabel.setAlignment(Align.center)
        quitLabel.setPosition(0f, V_HEIGHT / 2 - firstShift - 7 * hDist)

        stage.addActor(endGameLabel)
        stage.addActor(scoreLabel)
        stage.addActor(shootCountLabel)
        stage.addActor(killCountLabel)
        stage.addActor(accuracyLabel)
        stage.addActor(wsBonusCountLabel)
        stage.addActor(wpBonusCountLabel)
        stage.addActor(wlBonusCountLabel)
        stage.addActor(lfBonusCountLabel)
        stage.addActor(quitLabel)

        lfSprite = Sprite(TextureRegion(game.atlas.findRegion("life_bonus")))
        wlSprite = Sprite(TextureRegion(game.atlas.findRegion("wl_bonus")))
        wpSprite = Sprite(TextureRegion(game.atlas.findRegion("wp_bonus")))
        wsSprite = Sprite(TextureRegion(game.atlas.findRegion("ws_bonus")))
    }

    private fun handleInput(delta: Float) {
        if (
            Gdx.input.justTouched() ||
            Gdx.input.isKeyJustPressed(Input.Keys.SPACE) ||
            Gdx.input.isKeyJustPressed(Input.Keys.ENTER)
        ) {
            dispose()
            Gdx.app.exit()
        }
    }

    override fun update(delta: Float) {
        super.update(delta)
        hud.update()
        handleInput(delta)
    }

    override fun render(delta: Float) {
        super.render(delta)
        hud.render()
        stage.act()
        stage.draw()

        val firstShift = 10
        val hDist = 30

        game.batch.begin()
        game.batch.draw(wsSprite, 520f, V_HEIGHT / 2 - firstShift)
        game.batch.draw(wpSprite, 520f, V_HEIGHT / 2 - firstShift - hDist)
        game.batch.draw(wlSprite, 520f, V_HEIGHT / 2 - firstShift - 2 * hDist)
        game.batch.draw(lfSprite, 520f, V_HEIGHT / 2 - firstShift - 3 * hDist)
        game.batch.end()
    }

    override fun show() {
//        Gdx.app.log("EndGameScreen", "")
    }

    override fun resize(width: Int, height: Int) {
//        Gdx.app.log("EndGameScreen", "")
    }

    override fun pause() {
//        Gdx.app.log("EndGameScreen", "")
    }

    override fun resume() {
//        Gdx.app.log("EndGameScreen", "")
    }

    override fun hide() {
//        Gdx.app.log("EndGameScreen", "")
    }

    override fun dispose() {
        super.dispose()
        hud.dispose()
    }
}