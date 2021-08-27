package kotleni.b0mb3r.services;

import okhttp3.Request;

public abstract class SimpleBaseService extends Service {
    public static final String POST = "POST";
    public static final String PUT = "PUT";

    public String url;
    public String method;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Request buildRequest(Request.Builder builder) {
        return builder.build();
    }
}
