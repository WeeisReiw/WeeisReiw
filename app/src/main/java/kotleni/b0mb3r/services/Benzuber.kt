package kotleni.b0mb3r.services

import okhttp3.HttpUrl
import okhttp3.Request
import okhttp3.Request.Builder

class Benzuber : ParamsService() {
    override fun buildRequest(builder: Builder): Request {
        builder.addHeader("User-Agent", "Dalvik/2.1.0 (Linux; U; Android 11; M2010J19SY Build/RKQ1.201004.002)")
        return super.buildRequest(builder)
    }

    override fun buildParams(builder: HttpUrl.Builder) {
        builder.addQueryParameter("phone", formattedPhone)
        builder.addQueryParameter("flag", "A")
        builder.addQueryParameter("lng", "ru")
        builder.addQueryParameter("token", "*")
    }

    init {
        setUrl("https://app.benzuber.ru/app/1.8/auth/login")
    }
}