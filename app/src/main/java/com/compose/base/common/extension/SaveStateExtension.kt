package com.compose.base.common.extension

import androidx.lifecycle.SavedStateHandle

inline fun <reified T> SavedStateHandle.consume(key: String): T? {
    val value = get<T>(key)
    remove<T>(key)
    return value
}
