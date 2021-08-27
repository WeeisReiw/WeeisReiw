package kotleni.b0mb3r

data class Phone(
    var country: String,
    var number: String
) {
    companion object {
        fun parseFrom(line: String): Phone? {
            listOf("+380", "+7", "+375").forEach { // Украина, Россия, Беларусь
                if(line.startsWith(it)) { // ...
                    val start = line.indexOf(it)
                    if(start == 0) {
                        val number = line.removeRange(start, it.length)
                        return Phone(it, number)
                    }
                }
            }

            return null
        }
    }
}
