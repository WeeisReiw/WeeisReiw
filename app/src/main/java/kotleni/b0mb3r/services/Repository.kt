package kotleni.b0mb3r.services

interface Repository {
    fun getServices(countryCode: String?): List<Service>
}