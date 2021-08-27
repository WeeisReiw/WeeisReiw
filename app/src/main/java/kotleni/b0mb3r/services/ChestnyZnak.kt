package kotleni.b0mb3r.services

import kotleni.b0mb3r.services.JsonService
import org.json.JSONObject
import org.json.JSONException
import kotleni.b0mb3r.services.SimpleBaseService

class ChestnyZnak : JsonService() {
    override fun buildJson(): String {
        val json = JSONObject()
        try {
            json.put("phone", formattedPhone)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return json.toString()
    }

    init {
        setUrl("https://mobile.api.crpt.ru/mobile/login")
        setMethod(POST)
    }
}