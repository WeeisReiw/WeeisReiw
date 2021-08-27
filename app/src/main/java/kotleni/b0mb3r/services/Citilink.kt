package kotleni.b0mb3r.services

import okhttp3.HttpUrl.Builder

class Citilink : ParamsService() {
    override fun buildParams(builder: Builder) {
        builder.addPathSegment("+$formattedPhone/")
    }

    init {
        setUrl("https://www.citilink.ru/registration/confirm/phone/")
        setMethod(POST)
        setPhoneCode("7")
    }
}