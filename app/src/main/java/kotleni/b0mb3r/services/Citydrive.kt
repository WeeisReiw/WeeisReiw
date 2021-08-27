package kotleni.b0mb3r.services

import okhttp3.Request.Builder
import org.json.JSONObject
import org.json.JSONException
import okhttp3.Request

class Citydrive : JsonService() {
    override fun buildRequest(builder: Builder): Request {
        builder.addHeader("User-Agent", "carsharing/4.1.1 (Linux; Android 11; M2010J19SY Build/REL)")
        return super.buildRequest(builder)
    }

    override fun buildJson(): String {
        val json = JSONObject()
        try {
            json.put("os", "android")
            json.put("phone", phone)
            json.put("phone_code", phoneCode)
            json.put("token", "null")
            json.put("token_type", "fcm")
            json.put("vendor_id", "null")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return json.toString()
    }

    init {
        setUrl("https://cs-v2.youdrive.today/signup")
        setMethod(POST)
    }
}