package com.mobile.volunteerconnect.util

import android.util.Base64
import org.json.JSONObject

fun decodeJwtPayload(jwt: String): JSONObject? {
    return try {
        val parts = jwt.split(".")
        if (parts.size != 3) return null

        val payload = parts[1]
        val decodedBytes = Base64.decode(payload, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)
        val jsonString = String(decodedBytes, Charsets.UTF_8)

        JSONObject(jsonString)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
