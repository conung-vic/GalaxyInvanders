package com.conungvic.gigame.ui.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture

private const val AUDIO = "audio"
private const val MUSIC = "music"
private const val SOUNDS = "sounds"

const val THEME_01 = "$AUDIO/$MUSIC/theme_01.mp3"
const val THEME_02 = "$AUDIO/$MUSIC/theme_02.mp3"

const val ALIEN_HIT    = "$AUDIO/$SOUNDS/alien_hit.wav"
const val ALIEN_SHOOT  = "$AUDIO/$SOUNDS/alien_shoot.wav"
const val ALIEN_FLY    = "$AUDIO/$SOUNDS/alien_fly.wav"
const val BONUS        = "$AUDIO/$SOUNDS/bonus.wav"
const val EXPLOSION_1  = "$AUDIO/$SOUNDS/explosion_01.wav"
const val EXPLOSION_2  = "$AUDIO/$SOUNDS/explosion_02.wav"
const val PAUSE        = "$AUDIO/$SOUNDS/pause.wav"
const val PLAYER_SHOOT = "$AUDIO/$SOUNDS/player_shoot.wav"

const val BACKGROUND_TEMPLATE = "backgrounds/back%02d.jpg"

class GIAssetManager : AssetManager() {

    private var assetsLoading = true

    fun loadThemes() {
        this.load(THEME_01, Music::class.java)
        this.load(THEME_02, Music::class.java)
    }

    fun loadSoundFx() {
        this.load(ALIEN_HIT, Sound::class.java)
        this.load(ALIEN_SHOOT, Sound::class.java)
        this.load(ALIEN_FLY, Sound::class.java)
        this.load(BONUS, Sound::class.java)
        this.load(EXPLOSION_1, Sound::class.java)
        this.load(EXPLOSION_2, Sound::class.java)
        this.load(PAUSE, Sound::class.java)
        this.load(PLAYER_SHOOT, Sound::class.java)
    }

    fun loadBackgrounds() {
        (1..12)
            .forEach {
                this.load(BACKGROUND_TEMPLATE.format(it), Texture::class.java)
            }
    }

    override fun update(): Boolean {
        val res = super.update()
        if(isFinished && assetsLoading) {
            assetsLoading = false
            Gdx.app.log("GIAssetManager", "update: assets loaded")
            val theme1 = this.get(THEME_01, Music::class.java)
            theme1.setOnCompletionListener {
                Gdx.app.log("GIAssetManager", "switch theme from 01 to 02")
                val theme = this.get(THEME_02, Music::class.java)
                theme.play()
            }

            val theme2 = this.get(THEME_02, Music::class.java)
            theme2.setOnCompletionListener {
                Gdx.app.log("GIAssetManager", "switch theme from 02 to 01")
                val theme = this.get(THEME_01, Music::class.java)
                theme.play()
            }

//            theme1.play()
        }
        return res
    }
}