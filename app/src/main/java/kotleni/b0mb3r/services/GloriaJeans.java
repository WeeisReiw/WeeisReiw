package kotleni.b0mb3r.services;

import okhttp3.Request;

public class GloriaJeans extends Fivepost {

    public GloriaJeans() {
        setUrl("https://www.gloria-jeans.ru/phone-verification/send-code/registration");
        setMethod(POST);
        setPhoneCode("7");
    }

    @Override
    public Request buildRequest(Request.Builder builder) {
        builder.addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 11; M2010J19SY Build/RKQ1.201004.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/92.0.4515.131 Mobile Safari/537.36 GJ_MOBAPP");
        builder.addHeader("X-Requested-With", "XMLHttpRequest");

        return super.buildRequest(builder);
    }
}
