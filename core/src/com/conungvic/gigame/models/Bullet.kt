package com.conungvic.gigame.models

import com.badlogic.gdx.physics.box2d.Body

class Bullet(val body: Body) {
    var waitForDestroy = false
}