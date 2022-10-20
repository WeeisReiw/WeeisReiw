package kotleni.b0mb3r.services


class Phone(val countryCode: String, val phone: String) {
    override fun toString(): String {
        return countryCode + phone
    }
}