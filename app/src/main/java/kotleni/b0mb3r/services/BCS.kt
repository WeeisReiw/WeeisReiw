package kotleni.b0mb3r.services

import okhttp3.FormBody.Builder

class BCS : FormService() {
    override fun buildBody(builder: Builder) {
        builder.add("client_id", "broker_otp_guest2")
        builder.add("grant_type", "password")
        builder.add("username", formattedPhone)
    }

    init {
        setUrl("https://auth-ext.usvc.bcs.ru/auth/realms/Broker/protocol/openid-connect/token")
        setMethod(POST)
        setPhoneCode("7")
    }
}