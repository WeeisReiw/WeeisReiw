package kotleni.b0mb3r.services

import okhttp3.Request.Builder
import org.json.JSONObject
import org.json.JSONException
import kotleni.b0mb3r.services.SimpleBaseService
import okhttp3.Request

class EKA : JsonService() {
    override fun buildRequest(builder: Builder): Request {
        builder.addHeader("User-Agent", "ru.growapps.eka/2.9 (None; Android 11)")
        return super.buildRequest(builder)
    }

    override fun buildJson(): String {
        val json = JSONObject()
        try {
            json.put("Type", "0")
            json.put("Login", "+$formattedPhone")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return json.toString()
    }

    init {
        setUrl("https://app.eka.ru/Api/Auth/Login")
        setMethod(POST)
        setPhoneCode("7")
    }
}