package com.compose.base.common.extension

import com.compose.base.BuildConfig
import com.compose.base.common.utils.AppLogger
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

fun Any.logi() {
    AppLogger.i("BSV--- $this")
}

fun Any.logd() {
    AppLogger.d("BSV--- $this")
}

fun Any.loge() {
    AppLogger.e("BSV--- $this")
}

fun Any.logw() {
    AppLogger.w("BSV--- $this")
}

fun String.logJson() {
    if (BuildConfig.DEBUG) {
        try {
            val trimmed = this.trim()
            val pretty = when {
                trimmed.startsWith("{") -> {
                    val jsonObject = JSONObject(trimmed)
                    jsonObject.toString(4)
                }
                trimmed.startsWith("[") -> {
                    val jsonArray = JSONArray(trimmed)
                    jsonArray.toString(4)
                }
                else -> {
                    AppLogger.d("Invalid JSON format")
                    return
                }
            }
            AppLogger.d(pretty, tag = "JSON_PRETTY")
        } catch (e: JSONException) {
            AppLogger.e("Malformed JSON", e)
        }
    }
}
