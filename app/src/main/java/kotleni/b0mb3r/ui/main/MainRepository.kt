package kotleni.b0mb3r.ui.main

import android.content.SharedPreferences

class MainRepository(var prefs: SharedPreferences) {
    fun getPhone() : String {
        return prefs.getString("phone", "")!!
    }

    fun setPhone(phone: String) {
        prefs.edit().also {
            it.putString("phone", phone)
        }.apply()
    }

    fun getCycles() : Int {
        return prefs.getInt("cycles", 1)
    }

    fun setCycles(phone: Int) {
        prefs.edit().also {
            it.putInt("cycles", phone)
        }.apply()
    }
}