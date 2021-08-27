package kotleni.b0mb3r.services

import org.json.JSONObject
import org.json.JSONException

class Discord : JsonService() {
    override fun buildJson(): String {
        val json = JSONObject()
        try {
            json.put("phone", "+$formattedPhone")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return json.toString()
    }

    init {
        setUrl("https://discord.com/api/v9/auth/register/phone")
        setMethod(POST)
    }
}