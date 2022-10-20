package kotleni.b0mb3r.ui

import kotleni.b0mb3r.worker.AuthableProxy


interface Repository {
    var lastPhone: String?
    var lastCountryCode: Int
}