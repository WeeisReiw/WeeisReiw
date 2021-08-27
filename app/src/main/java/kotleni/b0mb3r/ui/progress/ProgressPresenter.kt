package kotleni.b0mb3r.ui.progress

import kotleni.b0mb3r.Bomber
import kotleni.b0mb3r.Phone
import kotleni.b0mb3r.ui.main.MainRepository

class ProgressPresenter(var view: ProgressView, var repo: MainRepository) {
    init {
        val phone = Phone.parseFrom(repo.getPhone())
        if(phone == null) { // error parsing
            view.numberParseError()
        } else { // all ok
            Bomber(phone, repo.getCycles())
                .also {
                    it.setOnUpdateProgress {
                        view.updateProgress(it)
                    }
                    it.setOnFinish {
                        view.doneExit()
                    }
                }.start()
        }
    }
}