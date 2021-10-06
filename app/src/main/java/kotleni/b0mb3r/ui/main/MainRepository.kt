package kotleni.b0mb3r.ui.main

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotleni.b0mb3r.data.Proxy

class MainRepository(var prefs: SharedPreferences) {
    fun getPhone() : String {
        return prefs.getString("phone", "")!!
    }

    fun setPhone(phone: String) {
        prefs.edit {
            putString("phone", phone)
        }
    }

    fun getCycles() : Int {
        return prefs.getInt("cycles", 1)
    }

    fun setCycles(cycles: Int) {
        prefs.edit {
            putInt("cycles", cycles)
        }
    }

    fun setProxies(arr: List<Proxy>) {
        val gson = Gson()
        val str = gson.toJson(arr)

        prefs.edit {
            putString("proxies", str)
        }
    }

    fun getProxies(): List<Proxy> {
        val str = prefs.getString("proxies", "[]")
        val gson = Gson()

        val listType = object : TypeToken<List<Proxy>>() { }.type
        return gson.fromJson<List<Proxy>>(str, listType)
    }
}