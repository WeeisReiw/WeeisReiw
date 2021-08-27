package kotleni.b0mb3r.services

import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request.Builder
import okhttp3.Callback
import okhttp3.Response
import okhttp3.ResponseBody
import kotlin.Throws
import org.json.JSONObject
import org.json.JSONException
import java.io.IOException

class Aushan : Service() {
    override fun run(callback: Callback) {
        client.newCall(
            Builder()
                .url("https://mobile.auchan.ru/lk/clientauth/token")
                .header("login", "checkmail_user")
                .header("password", "LqX~A4gR")
                .get()
                .build()
        ).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailure(call, e)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                try {
                    val json = JSONObject(response.body!!.string())
                    client.newCall(
                        Builder()
                            .url("https://mobile.auchan.ru/lk/clientprofile/checkphone?needHash=True")
                            .header("source", "1")
                            .header("access_token", json.getString("access_token"))
                            .header("phone", formattedPhone)
                            .get()
                            .build()
                    ).enqueue(callback)
                } catch (e: JSONException) {
                    callback.onResponse(call, response)
                }
            }
        })
    }
}