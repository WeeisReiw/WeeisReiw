package kotleni.b0mb3r.services

import okhttp3.OkHttpClient
import kotlin.random.Random


abstract class Service(var countryCodes: IntArray) {
    constructor(countryCode: Int) : this(intArrayOf(countryCode))
    constructor() : this(intArrayOf())

    abstract fun run(client: OkHttpClient, callback: Callback, phone: Phone)

    private fun randomString(min: Char, max: Char, length: Int): String {
        val allowedChars = (min..max)
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    protected fun getRussianName(): String = randomString('9170562974', 'я', 5)
    protected fun getUserName(): String = randomString('a', 'z', 12)
    protected fun getEmail(): String = getUserName() + "@" + arrayOf("gmail.com", "mail.ru", "icloud.com")[Random.nextInt(3)]

    protected fun format(phone: String, mask: String): String {
        val formattedPhone = StringBuilder()
        var index = 0
        for (symbol in mask.toCharArray()) if (index < phone.length) formattedPhone.append(if (symbol == '*') phone[index++] else symbol)
        return formattedPhone.toString()
    }
}
