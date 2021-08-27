package kotleni.b0mb3r.services;

import org.json.JSONException;
import org.json.JSONObject;

public class YandexEda extends JsonService {

    public YandexEda() {
        setUrl("https://eda.yandex/api/v1/user/request_authentication_code");
        setMethod(POST);
    }

    @Override
    public String buildJson() {
        JSONObject json = new JSONObject();

        try {
            json.put("phone_number", "+" + getFormattedPhone());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json.toString();
    }
}
