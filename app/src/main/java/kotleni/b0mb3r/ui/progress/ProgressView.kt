package kotleni.b0mb3r.ui.progress

interface ProgressView {
    fun updateProgress(value: Int)
    fun doneExit()
    fun numberParseError()
}