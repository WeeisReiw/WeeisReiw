package kotleni.b0mb3r.services

import okhttp3.Request.Builder
import okhttp3.Request

class BApteka : Gorparkovka() {
    override fun buildRequest(builder: Builder): Request {
        builder.addHeader("User-Agent", "Dalvik/2.1.0 (Linux; U; Android 11; M2010J19SY Build/RKQ1.201004.002)")
        builder.addHeader("b-apteka-session", "null")
        return super.buildRequest(builder)
    }

    init {
        setUrl("https://b-apteka.ru/api/lk/send_code")
        setMethod(POST)
        setPhoneCode("7")
    }
}