package kotleni.b0mb3r.services

import okhttp3.*
import okhttp3.Request.Builder
import kotlin.Throws
import org.json.JSONObject
import org.json.JSONException
import java.io.IOException

class Dolyame : Service() {
    override fun run(callback: Callback) {
        client.newCall(
            Builder()
                .url("https://id.dolyame.ru/auth/authorize")
                .post(
                    FormBody.Builder()
                        .add("client_id", "bnpl-mobile-app")
                        .add("redirect_uri", "mobile://")
                        .add("response_type", "code")
                        .add("response_mode", "json")
                        .add("display", "json")
                        .add("device_id", "1111111111111111")
                        .add("client_version", "2.5.0")
                        .add("vendor", "tinkoff_android")
                        .add(
                            "claims",
                            "{\"id_token\":{\"given_name\":null, \"phone_number\": null, \"picture\": null}}"
                        )
                        .build()
                )
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
                            .url("https://id.dolyame.ru/auth/step?cid=" + json.getString("cid"))
                            .header("Cookie", response.headers["Set-Cookie"]!!)
                            .post(
                                FormBody.Builder()
                                    .add("phone", "+$formattedPhone")
                                    .add("step", "phone")
                                    .build()
                            )
                            .build()
                    ).enqueue(callback)
                } catch (e: JSONException) {
                    callback.onResponse(call, response)
                }
            }
        })
    }
}