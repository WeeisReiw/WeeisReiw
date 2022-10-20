package kotleni.b0mb3r.ui

import android.content.Context
import android.content.SharedPreferences
import android.net.Credentials
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import kotleni.b0mb3r.worker.AuthableProxy
import java.net.InetSocketAddress
import java.net.Proxy

class MainRepository(context: Context) : Repository {
    private val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override var lastPhone: String?
        get() = preferences.getString(LAST_PHONE, "")
        set(phoneNumber) {
            preferences.edit().putString(LAST_PHONE, phoneNumber).apply()
        }
    override var lastCountryCode: Int
        get() = preferences.getInt(LAST_COUNTRY_CODE, 0)
        set(phoneCode) {
            preferences.edit().putInt(LAST_COUNTRY_CODE, phoneCode).apply()
        }

    companion object {
        private const val LAST_PHONE = "last_phone"
        private const val LAST_COUNTRY_CODE = "last_country_code"
    }
}
