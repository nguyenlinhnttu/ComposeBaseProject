package com.compose.base.common.extension

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.compose.base.MainActivity
import com.compose.base.common.utils.AppLogger

fun Activity.goToAppSetting() {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null),
    )
    startActivity(intent)
}

@RequiresApi(Build.VERSION_CODES.S)
fun Activity.gotoScheduleExactAlarmSetting() {
    val intent = Intent(
        Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
        Uri.fromParts("package", packageName, null),
    )
    startActivity(intent)
}

fun Context.getMainActivity(): MainActivity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is MainActivity) return context
        context = context.baseContext
    }
    return null
}

fun Context.openBrowser(link: String?) {
    if (link.isNullOrEmpty()) {
        return
    }
    "openBrowser $link".logi()
    try {
        val intent = Intent(Intent.ACTION_VIEW, link.toUri())
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        AppLogger.e("Activity not found to handle ACTION_VIEW for URI: $link", e)
    }
}
