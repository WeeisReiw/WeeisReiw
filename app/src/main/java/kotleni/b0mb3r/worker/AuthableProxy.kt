package kotleni.b0mb3r.worker

import androidx.annotation.Nullable
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.net.InetSocketAddress
import java.net.Proxy


class AuthableProxy(type: Proxy.Type?, sa: InetSocketAddress?, private val credential: String?) :
    Proxy(type, sa), Authenticator {

    @Nullable
    override fun authenticate(@Nullable route: Route?, response: Response): Request? {
        return if (credential != null) response.request.newBuilder()
            .header("Proxy-Authorization", credential)
            .build() else null
    }
}