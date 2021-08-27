package kotleni.b0mb3r.services;

import okhttp3.HttpUrl;

public class Askona extends ParamsService {

    public Askona() {
        setUrl("https://www.askona.ru/api/registration/sendcode");
        setPhoneCode("7");
    }

    @Override
    public void buildParams(HttpUrl.Builder builder) {
        builder.addQueryParameter("csrf_token", "d5f096efeef60426fd83178815e6c771");
        builder.addQueryParameter("contact[phone]", getFormattedPhone());
    }
}
