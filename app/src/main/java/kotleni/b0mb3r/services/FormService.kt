package kotleni.b0mb3r.services

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request


abstract class FormService(
    protected var url: String,
    protected var method: String,
    vararg countryCodes: Int
) : Service(countryCodes) {
    protected var request: Request.Builder? = null
    protected var builder: FormBody.Builder? = null

    constructor(url: String, vararg countryCodes: Int) : this(url, "POST", *countryCodes)

    override fun run(client: OkHttpClient, callback: Callback, phone: Phone) {
        request = Request.Builder()
        builder = FormBody.Builder()
        buildBody(phone)
        request?.let { request ->
            request.url(url)
            request.method(method, builder?.build())
            client.newCall(request.build()).enqueue(callback!!)
        }
    }

    abstract fun buildBody(phone: Phone?)
}