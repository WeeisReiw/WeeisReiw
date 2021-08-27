package kotleni.b0mb3r.ui.main

import kotleni.b0mb3r.Static

class MainPresenter(var view: MainView, var repo: MainRepository) {
    init {
        view.loadPrefs()
        view.setOnStart {
            view.startAttack()
        }

        view.setOnGithubOpen {
            view.openUrl(Static.GITHUB_URL)
        }
    }
}