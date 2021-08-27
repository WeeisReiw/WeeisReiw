package kotleni.b0mb3r.ui.main

interface MainView {
    fun openUrl(url: String)
    fun setOnGithubOpen(handler: () -> Unit)
    fun setOnStart(handler: () -> Unit)
    fun startAttack()
    fun loadPrefs()
}