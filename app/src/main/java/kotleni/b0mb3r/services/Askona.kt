package kotleni.b0mb3r.services

import okhttp3.HttpUrl.Builder
import kotleni.b0mb3r.services.ParamsService

class Askona : ParamsService() {
    override fun buildParams(builder: Builder) {
        builder.addQueryParameter("csrf_token", "d5f096efeef60426fd83178815e6c771")
        builder.addQueryParameter("contact[phone]", formattedPhone)
    }

    init {
        setUrl("https://www.askona.ru/api/registration/sendcode")
        setPhoneCode("7")
    }
}