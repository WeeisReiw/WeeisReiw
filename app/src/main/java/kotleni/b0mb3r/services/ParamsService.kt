package kotleni.b0mb3r.services

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.util.*

abstract class ParamsService(
    protected var url: String,
    protected var method: String?,
    vararg countryCodes: Int
) : Service(countryCodes) {
    protected var request: Request.Builder? = null
    protected var builder: HttpUrl.Builder? = null

    constructor(url: String, vararg countryCodes: Int) : this(url, null, *countryCodes) {}

    override fun run(client: OkHttpClient, callback: Callback, phone: Phone) {
        request = Request.Builder()
        builder = Objects.requireNonNull(url.toHttpUrlOrNull())!!.newBuilder()
        buildParams(phone)
        request?.let { request ->
            request.url(builder!!.build().toString())
            if (method != null) request.method(method.toString(), RequestBody.create(null, ""))
            client.newCall(request.build()).enqueue(callback!!)
        }
    }

    abstract fun buildParams(phone: Phone?)
}
