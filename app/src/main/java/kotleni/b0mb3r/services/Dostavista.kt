package kotleni.b0mb3r.services

import okhttp3.Request.Builder
import okhttp3.FormBody
import okhttp3.Request

class Dostavista : FormService() {
    override fun buildRequest(builder: Builder): Request {
        builder.addHeader("user-agent", "ru-courier-app-main-android/2.61.5.2377")
        return super.buildRequest(builder)
    }

    override fun buildBody(builder: FormBody.Builder) {
        builder.add("v", "1.141")
        builder.add("phone", "+$formattedPhone")
        builder.add("unique_device_id", "null")
    }

    init {
        setUrl("https://robot.dostavista.ru/api/send-registration-sms")
        setMethod(POST)
        setPhoneCode("7")
    }
}