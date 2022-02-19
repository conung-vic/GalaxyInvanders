package com.conungvic.gigame.models

import com.badlogic.gdx.physics.box2d.World

enum class GameState {
    STARTING, PLAYING, PAUSED, PLAYER_DIED, END_GAME
}

object GameModel {
    var currentLevel: Int = 1
    var scores: Int = 0
    var killed: Int = 0
    var playerShootCount : Int = 0
    var state: GameState = GameState.STARTING
    var stateTime: Float = 0f
    var fleetState: EnemyState = EnemyState.STAND
    var fleetStateTime: Float = 0f
    var flyngEnemies: Int = 0
    var bonuses: MutableMap<BonusType, Int> = mutableMapOf(
        Pair(BonusType.LIFE, 0),
        Pair(BonusType.WEAPON_SPEED, 0),
        Pair(BonusType.WEAPON_POWER, 0),
        Pair(BonusType.WEAPON_LEVEL, 0)
    )
}