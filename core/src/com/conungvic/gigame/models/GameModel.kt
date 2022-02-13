package com.conungvic.gigame.models

enum class GameState {
    STARTING, RUNNING, PAUSED, PLAYER_RESURRECTED, PLAYER_DIED
}

object GameModel {
    var currentLevel: Int = 1
    var scores: Int = 0
    var levelTime: Int = 0
    var killed: Int = 0
    var bonuses: Array<Int> = arrayOf(0, 0, 0, 0)
    var playerShootCount : Int = 0
    var state: GameState = GameState.STARTING

}