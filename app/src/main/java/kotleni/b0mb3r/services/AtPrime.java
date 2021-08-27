package kotleni.b0mb3r.services;

import okhttp3.FormBody;

public class AtPrime extends FormService {

    public AtPrime() {
        setUrl("https://api-prime.anytime.global/api/v2/auth/sendVerificationCode");
        setMethod(POST);
    }

    @Override
    public void buildBody(FormBody.Builder builder) {
        builder.add("phone", getFormattedPhone());
    }
}
