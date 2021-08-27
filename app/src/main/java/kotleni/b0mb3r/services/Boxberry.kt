package kotleni.b0mb3r.services

import okhttp3.Request.Builder
import kotleni.b0mb3r.services.JsonService
import org.json.JSONObject
import org.json.JSONException
import kotleni.b0mb3r.services.SimpleBaseService
import okhttp3.Request

class Boxberry : JsonService() {
    override fun buildRequest(builder: Builder): Request {
        builder.addHeader("platform", "android")
        builder.addHeader("os-version", "11")
        builder.addHeader("app-version", "1.5.2")
        builder.addHeader("device", "null")
        builder.addHeader("x-access-token", "null")
        return super.buildRequest(builder)
    }

    override fun buildJson(): String {
        val json = JSONObject()
        try {
            json.put("phone", formattedPhone)
            json.put("reason", "registration")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return json.toString()
    }

    init {
        setUrl("https://mobile.boxberry.ru/api/v1/sms/code/send")
        setMethod(POST)
        setPhoneCode("7")
    }
}