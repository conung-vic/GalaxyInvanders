package com.conungvic.gigame.models

interface Destroyable {
    fun destroy()
    fun setWaitForDestroy(value: Boolean)
    fun isWaitForDestroy(): Boolean
}