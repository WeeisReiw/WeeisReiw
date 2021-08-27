package kotleni.b0mb3r.services

import okhttp3.FormBody.Builder
import kotleni.b0mb3r.services.FormService
import kotleni.b0mb3r.services.SimpleBaseService

class AtPrime : FormService() {
    override fun buildBody(builder: Builder) {
        builder.add("phone", formattedPhone)
    }

    init {
        setUrl("https://api-prime.anytime.global/api/v2/auth/sendVerificationCode")
        setMethod(POST)
    }
}