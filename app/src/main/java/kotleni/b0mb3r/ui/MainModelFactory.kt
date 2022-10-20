package kotleni.b0mb3r.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import kotleni.b0mb3r.ui.Repository

class MainModelFactory(preferences: Repository, workManager: WorkManager) : ViewModelProvider.NewInstanceFactory() {
    private val repository: Repository
    private val workManager: WorkManager

    init {
        repository = preferences
        this.workManager = workManager
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == MainViewModel::class.java) return MainViewModel(repository, workManager) as T
        throw IllegalArgumentException()
    }
}
