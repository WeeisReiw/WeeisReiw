package kotleni.b0mb3r.services

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class Baucenter : Service() {
    override fun run(callback: Callback) {
        client.newCall(
            Request.Builder()
                .url("https://ma.baucenter.ru/auth/sessionRegister")
                .header("x-api-key", "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCtj8uTFmdASuHU")
                .header("x-auth-token", "")
                .post(RequestBody.create("application/json".toMediaTypeOrNull(), ByteArray(0)))
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
                    client.newCall(
                        Request.Builder()
                            .url("https://ma.baucenter.ru/auth/authByPhone")
                            .header("x-api-key", "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCtj8uTFmdASuHU")
                            .header("x-auth-token", json.getJSONObject("data").getString("token"))
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