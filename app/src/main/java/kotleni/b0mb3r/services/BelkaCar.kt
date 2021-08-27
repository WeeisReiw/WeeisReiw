package kotleni.b0mb3r.services

import okhttp3.FormBody.Builder

class BelkaCar : FormService() {
    override fun buildBody(builder: Builder) {
        builder.add("phone", formattedPhone)
        builder.add("device_id", "null")
    }

    init {
        setUrl("https://api.belkacar.ru/v2.12-covid19/auth/get-code")
        setMethod(POST)
        setPhoneCode("7")
    }
}