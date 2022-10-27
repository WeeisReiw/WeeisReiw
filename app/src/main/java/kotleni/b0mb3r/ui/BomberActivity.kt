package kotleni.b0mb3r.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSpinner
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import com.dm.bomber.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import kotleni.b0mb3r.GITHUB_URL
import kotleni.b0mb3r.TELEGRAM_URL
import kotleni.b0mb3r.ui.adapters.CountryCodeAdapter
import kotleni.b0mb3r.ui.dialog.ProgressDialog


class BomberActivity : AppCompatActivity() {
    companion object {
        const val TASK_ID = "taskBomber"
    }

    private val githubLnk: TextView by trixpl { findViewById(R.id.github_lnk) }
    private val telegramLnk: TextView by trixpl { findViewById(R.id.tg_lnk) }
    private val startBtn: MaterialButton by trixpl { findViewById(R.id.start_btn) }
    private val cyclesText: EditText by trixpl { findViewById(R.id.cycles) }
    private val phoneText: EditText by trixpl { findViewById(R.id.phone) }
    private val phoneCode: AppCompatSpinner by trixpl { findViewById(R.id.phone_code) }

    private var model: MainViewModel? = null
    private var repository: Repository? = null

    private val clipText: String? = null

    private val countryCodes = arrayOf("7", "380", "375", "77", "")
    private val phoneLength = intArrayOf(10, 9, 9, 9, 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bomber)

        val workManager: WorkManager = WorkManager.getInstance(this)

        repository = MainRepository(this)
        model = ViewModelProvider(
            this,
            MainModelFactory(repository!!, workManager) as ViewModelProvider.Factory
        ).get(MainViewModel::class.java)

        val dialog = ProgressDialog(this)

        model?.getProgress()?.observe(this) { (first, second): Pair<Int, Int> ->
            // update progress
        }

        model?.getAttackStatus()?.observe(this) { attackStatus: Boolean ->
            if (attackStatus) {
                // attack start
                dialog.show()
            } else {
                // attack stop
                dialog.doneExit()
            }
        }

        githubLnk.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(GITHUB_URL)))
        }

        telegramLnk.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(TELEGRAM_URL)))
        }

        val countryCodeAdapter = CountryCodeAdapter(
            this,
            countryCodes
        )
        phoneCode.setAdapter(countryCodeAdapter)

        startBtn.setOnClickListener {
            val phoneNumber: String = phoneText.getText().toString()
            val repeats: String = cyclesText.getText().toString()

            val length = phoneLength[phoneCode.getSelectedItemPosition()]
            if (phoneNumber.length != length && length != 0) {
                Snackbar.make(githubLnk, "Долбаеб номер не правильный", Snackbar.LENGTH_LONG).show()
            } else {
                repository?.lastCountryCode = phoneCode.getSelectedItemPosition()
                repository?.lastPhone = phoneNumber

                model!!.startAttack(
                    countryCodes[phoneCode.getSelectedItemPosition()], phoneNumber,
                    if (repeats.isEmpty()) 1 else repeats.toInt()
                )
            }
        }
    }
}
