package com.weaponx.spotifyclone.util

/**
 * Single use event
 * Commonly used in Google samples
 */
open class Event<out T>(private val data: T) {
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (!hasBeenHandled) {
            hasBeenHandled = true
            data
        }
        else null
    }

    fun peekContent() = data
}