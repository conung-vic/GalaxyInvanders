package com.conungvic.gigame.models

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.conungvic.gigame.BONUS_BIT
import com.conungvic.gigame.GIGame
import com.conungvic.gigame.PLAYER_BIT
import com.conungvic.gigame.WALL_BIT
import com.conungvic.gigame.ui.utils.BONUS
import kotlin.experimental.or

enum class BonusType {
    NONE, LIFE, WEAPON_POWER, WEAPON_SPEED, WEAPON_LEVEL
}

abstract class Bonus(val game: GIGame, x: Float, y: Float) : Destroyable {
    val body: Body
    protected var bonusFixDef = FixtureDef()
    var type: BonusType = BonusType.NONE
    private var waitForDestroy = false
    override fun setWaitForDestroy(value: Boolean) { waitForDestroy = value }
    override fun isWaitForDestroy(): Boolean = waitForDestroy

    init {
        bonusFixDef.filter.categoryBits = BONUS_BIT
        bonusFixDef.filter.maskBits = PLAYER_BIT
        val bonusShape = CircleShape()
        bonusShape.radius = 10f
        bonusFixDef.shape = bonusShape

        val bDef = BodyDef()
        bDef.type = BodyDef.BodyType.DynamicBody
        bDef.position.set(x, y)
        body = game.world.createBody(bDef)
        val velocity = if (GameModel.state == GameState.PLAYING) -170f else 0f
        body.setLinearVelocity(0f, velocity)
    }

    override fun destroy() {
        game.world.destroyBody(body)
    }

    open fun apply() {
        val snd = game.assetManager.get(BONUS, Sound::class.java)
        snd.play()
        GameModel.bonuses[type] = GameModel.bonuses[type]!!+ 1
        GameModel.scores += 50
    }
}

class LifeBonus(game: GIGame, x: Float, y: Float): Bonus(game, x, y) {
    init {
        type = BonusType.LIFE
        body.createFixture(bonusFixDef).userData = this
    }

    override fun apply() {
        super.apply()
        game.player.life++
    }

}

class WeaponPowerBonus(game: GIGame, x: Float, y: Float): Bonus(game, x, y) {
    init {
        type = BonusType.WEAPON_POWER
        body.createFixture(bonusFixDef).userData = this
    }

    override fun apply() {
        super.apply()
        game.player.weaponPower++
    }
}

class WeaponSpeedBonus(game: GIGame, x: Float, y: Float): Bonus(game, x, y) {
    init {
        type = BonusType.WEAPON_SPEED
        body.createFixture(bonusFixDef).userData = this
    }

    override fun apply() {
        super.apply()
        game.player.weaponSpeed++
    }
}

class WeaponLevelBonus(game: GIGame, x: Float, y: Float): Bonus(game, x, y) {
    init {
        type = BonusType.WEAPON_LEVEL
        body.createFixture(bonusFixDef).userData = this
    }

    override fun apply() {
        super.apply()
        game.player.weaponLevel++
    }
}