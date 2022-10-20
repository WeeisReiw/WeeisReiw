package kotleni.b0mb3r.services

import okhttp3.Call
import okhttp3.Callback
import java.io.IOException

interface Callback : Callback {
    fun onError(call: Call, e: Exception) {

    }

    override fun onFailure(call: Call, e: IOException) {
        onError(call, e)
    }
}