package kotleni.b0mb3r.ui

import android.content.SharedPreferences

class MainRepository(var prefs: SharedPreferences) {
    companion object {
        private const val KEY_PHONE = "phone"
        private const val KEY_CYCLES = "cycles"
    }

    fun getPhone() : String {
        return prefs.getString(KEY_PHONE, "")!!
    }

    fun setPhone(phone: String) {
        prefs.edit().apply {
            putString(KEY_PHONE, phone)
            apply()
        }
    }

    fun getCycles() : Int {
        return prefs.getInt(KEY_CYCLES, 1)
    }

    fun setCycles(cycles: Int) {
        prefs.edit().apply {
            putInt(KEY_CYCLES, cycles)
            apply()
        }
    }
}