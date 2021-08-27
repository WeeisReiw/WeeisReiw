package kotleni.b0mb3r.services;

import okhttp3.HttpUrl;

public class Citilink extends ParamsService {

    public Citilink() {
        setUrl("https://www.citilink.ru/registration/confirm/phone/");
        setMethod(POST);
        setPhoneCode("7");
    }

    @Override
    public void buildParams(HttpUrl.Builder builder) {
        builder.addPathSegment("+" + getFormattedPhone() + "/");
    }
}
