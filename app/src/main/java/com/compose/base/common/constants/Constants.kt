package com.compose.base.common.constants

/**
 * Constants.kt
 *
 * Defines all global constants used throughout the app.
 * ➤ Group constants using nested `object` blocks by domain (App, Intents, etc.)
 * ➤ When adding new constants, place them in the appropriate group.
 * ➤ If no group fits, feel free to create a new one.
 */
object Constants {
    // ===== App-wide config =====
    object App {
        const val DEFAULT_TIMEOUT = 30L
    }

    // ===== Intent Extras / Navigation Args =====
    object IntentKeys {
        const val POST_ID = "extra_post_id"
    }
}
