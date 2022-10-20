package kotleni.b0mb3r.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.*
import kotleni.b0mb3r.ui.Repository
import kotleni.b0mb3r.worker.AttackWorker
import java.util.*
import java.util.concurrent.TimeUnit


class MainViewModel(preferences: Repository, workManager: WorkManager) : ViewModel() {
    private val repository: Repository
    private val workManager: WorkManager
    private var currentAttackId: UUID? = null
    private val progress = MutableLiveData(Pair(0, 0))
    private val attackStatus = MutableLiveData(false)
    val scheduledAttacks: LiveData<List<WorkInfo>>

    init {
        repository = preferences
        this.workManager = workManager
        workManager.getWorkInfosLiveData(
            WorkQuery.Builder.fromStates(
                Arrays.asList(
                    WorkInfo.State.RUNNING,
                    WorkInfo.State.CANCELLED,
                    WorkInfo.State.SUCCEEDED,
                    WorkInfo.State.FAILED
                )
            ).build()
        ).observeForever { workInfos: List<WorkInfo> ->
            for (workInfo in workInfos) if (workInfo.id == currentAttackId) {
                if (workInfo.state.isFinished) attackStatus.

                value = false
                val data: Data = workInfo.progress
                progress.setValue(
                    Pair(
                        data.getInt(AttackWorker.KEY_PROGRESS, 0),
                        data.getInt(AttackWorker.KEY_MAX_PROGRESS, 0)
                    )
                )
            }
        }
        scheduledAttacks = workManager.getWorkInfosLiveData(
            WorkQuery.Builder.fromStates(
                listOf(
                    WorkInfo.State.RUNNING,
                    WorkInfo.State.ENQUEUED
                )
            ).build()
        )
    }

    fun getProgress(): LiveData<Pair<Int, Int>> {
        return progress
    }

    fun getAttackStatus(): LiveData<Boolean> {
        return attackStatus
    }

    fun scheduleAttack(countryCode: String, phoneNumber: String, repeats: Int, date: Long, current: Long) {
        val inputData: Data = Data.Builder()
            .putString(AttackWorker.KEY_COUNTRY_CODE, countryCode)
            .putString(AttackWorker.KEY_PHONE, phoneNumber)
            .putInt(AttackWorker.KEY_REPEATS, Math.min(repeats, 10))
            .build()
        val workRequest: OneTimeWorkRequest = OneTimeWorkRequest.Builder(AttackWorker::class.java)
            .addTag("+$countryCode$phoneNumber;$date")
            .setInitialDelay(date - current, TimeUnit.MILLISECONDS)
            .setInputData(inputData)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()
        if (current == 0L) {
            currentAttackId = workRequest.id
            attackStatus.setValue(true)
        }
        workManager.enqueue(workRequest)
    }

    fun startAttack(countryCode: String, phoneNumber: String, numberOfCyclesNum: Int) {
        scheduleAttack(countryCode, phoneNumber, numberOfCyclesNum, 0, 0)
    }

    fun stopAttack() {
        if(currentAttackId == null) return
        workManager.cancelWorkById(currentAttackId!!)
    }
}
