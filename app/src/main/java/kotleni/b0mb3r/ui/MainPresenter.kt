package kotleni.b0mb3r.ui

import kotleni.b0mb3r.GITHUB_URL

class MainPresenter(var view: MainView, var repo: MainRepository) {
    init {
        view.loadPrefs()
        view.setOnStart {
            view.startAttack()
        }

        view.setOnGithubOpen {
            view.openUrl(GITHUB_URL)
        }
    }
}