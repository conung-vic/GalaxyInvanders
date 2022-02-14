package com.conungvic.gigame.models

enum class State {
    LIVE, DEAD, INVULNERABLE
}

object PlayerModel {
    var life: Int = 5
    var weaponLevel: Int = 1
    var weaponPower: Int = 1
    var weaponSpeed: Float = 1.0f
    var state: State = State.LIVE
}