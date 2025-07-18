package com.compose.base.common.extension

import android.util.Patterns

/** Check if string is a valid email */
fun String?.isValidEmail(): Boolean {
    return !this.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

/** Check if string contains only digits */
fun String.isDigitsOnly(): Boolean {
    return this.matches(Regex("^\\d+$"))
}

/** Truncate and append "..." if string is too long */
fun String.ellipsize(maxLength: Int): String {
    return if (this.length <= maxLength) this else this.take(maxLength - 3) + "..."
}

/** Remove all whitespace (tabs, spaces, newlines) */
fun String.removeAllWhitespace(): String {
    return this.replace("\\s".toRegex(), "")
}

/** Checks if the character is a half-width character.*/
fun Char.isHalfWidth(): Boolean {
    return this in '\u0000'..'\u00FF' || this in '\uFF61'..'\uFFDC' || this in '\uFFE8'..'\uFFEE'
}
