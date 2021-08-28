package kotleni.b0mb3r.services

import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class DvaBerega : Service() {
    override fun run(callback: Callback) {
        client.newCall(
            Request.Builder()
                .url("https://2bmob.2-berega.ru/api/v1/registration/guest")
                .post(FormBody.Builder().build())
                .build()
        ).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailure(call, e)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                try {
                    val json = JSONObject(response.body!!.string())
                    val req = JSONObject()
                    req.put("phone", formattedPhone)
                    req.put("regionId", "1")
                    req.put("showcase", "2")
                    client.newCall(
                        Request.Builder()
                            .url("https://2bmob.2-berega.ru/api/v1/registration")
                            .header(
                                "Authorization", "Bearer " + json
                                    .getJSONObject("data")
                                    .getString("bearer")
                            )
                            .post(RequestBody.create("application/json".toMediaTypeOrNull(), req.toString()))
                            .build()
                    ).enqueue(callback)
                } catch (e: JSONException) {
                    callback.onResponse(call, response)
                }
            }
        })
    }
}