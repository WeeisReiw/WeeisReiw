package kotleni.b0mb3r.services

import okhttp3.FormBody.Builder
import kotleni.b0mb3r.services.FormService
import kotleni.b0mb3r.services.SimpleBaseService

class Alltime : FormService() {
    override fun buildBody(builder: Builder) {
        builder.add("action", "send")
        builder.add("back", "/")
        builder.add("phone", format(formattedPhone, "+*+(***)+***-**-**"))
    }

    init {
        setUrl("https://www.alltime.ru/sservice/2020/form_register_phone.php")
        setMethod(POST)
        setPhoneCode("7")
    }
}