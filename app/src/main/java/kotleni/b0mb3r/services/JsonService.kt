package kotleni.b0mb3r.services

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody


abstract class JsonService(
    protected var url: String,
    protected var method: String,
    vararg countryCodes: Int
) : Service(countryCodes) {
    protected var request: Request.Builder? = null

    constructor(url: String, vararg countryCodes: Int) : this(url, "POST", *countryCodes)

    override fun run(client: OkHttpClient, callback: Callback, phone: Phone) {
        request = Request.Builder()
        val body: RequestBody = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            buildJson(phone).toString()
        )
        request?.let { request ->
            request.url(url)
            request.method(method, body)
            client.newCall(request.build()).enqueue(callback!!)
        }
    }

    abstract fun buildJson(phone: Phone?): String?
}