package com.conungvic.gigame.models

enum class GameState {
    STARTING, PLAYING, PAUSED, PLAYER_DIED
}

object GameModel {
    var currentLevel: Int = 25
    var scores: Int = 0
    var killed: Int = 0
    var bonuses: Array<Int> = arrayOf(0, 0, 0, 0)
    var playerShootCount : Int = 0
    var state: GameState = GameState.STARTING
    var stateTime: Float = 0f

}