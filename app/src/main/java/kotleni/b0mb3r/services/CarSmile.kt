package kotleni.b0mb3r.services

import okhttp3.Request.Builder
import org.json.JSONObject
import org.json.JSONException
import okhttp3.Request

class CarSmile : JsonService() {
    override fun buildRequest(builder: Builder): Request {
        builder.addHeader("User-Agent", "okhttp/3.12.1")
        builder.addHeader("authorization", "Bearer null")
        return super.buildRequest(builder)
    }

    override fun buildJson(): String {
        val json = JSONObject()
        try {
            json.put("operationName", "enterPhone")
            json.put("variables", JSONObject().put("phone", formattedPhone))
            json.put("query", "mutation enterPhone(\$phone: String!) {\n  enterPhone(phone: \$phone)\n}\n")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return json.toString()
    }

    init {
        setUrl("https://api.carsmile.com/")
        setMethod(POST)
    }
}