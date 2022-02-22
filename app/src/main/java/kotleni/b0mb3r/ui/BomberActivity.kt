package kotleni.b0mb3r.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.dm.bomber.R
import com.google.android.material.button.MaterialButton
import kotleni.b0mb3r.Bomber
import kotleni.b0mb3r.Phone
import kotleni.b0mb3r.ui.dialog.ProgressDialog

class BomberActivity : AppCompatActivity(), MainView {
    companion object {
        var globalRepository: MainRepository? = null
    }

    private val githubLnk: TextView by lazy { findViewById(R.id.github_lnk) }
    private val telegramLnk: TextView by lazy { findViewById(R.id.tg_lnk) }
    private val startBtn: MaterialButton by lazy { findViewById(R.id.start_btn) }
    private val cyclesText: EditText by lazy { findViewById(R.id.cycles) }
    private val phoneText: EditText by lazy { findViewById(R.id.phone) }
    private val logoImage: ImageView by lazy { findViewById(R.id.logo) }

    private val repo: MainRepository by lazy { MainRepository(getSharedPreferences(packageName, MODE_PRIVATE)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bomber)

        // fixme
        logoImage.setOnLongClickListener {
            val app = packageManager.getPackageInfo(packageName, 0)
            Toast.makeText(this, "${app.versionName} (${app.versionCode})", Toast.LENGTH_SHORT).show()
            true
        }

        // fixme
        telegramLnk.setOnClickListener {
            openUrl("https://t.me/b0mb3r_apk")
        }

        globalRepository = repo
        MainPresenter(this, repo)
    }

    override fun startAttack() {
        repo.setPhone(phoneText.text.toString())
        repo.setCycles(cyclesText.text.toString().toInt())

        val phone = Phone.parseFrom(phoneText.text.toString())

        if(phone == null) {
            Toast.makeText(this, "Ошибка разбора номера.", Toast.LENGTH_SHORT).show()
        } else {
            val dialog = ProgressDialog(this)
            val bomber = Bomber(phone, cyclesText.text.toString().toInt())

            bomber.setOnUpdateProgress { dialog.updateProgress(it) }
            bomber.setOnFinish { dialog.doneExit() }
            bomber.start()
            dialog.show()
        }
    }

    override fun openUrl(url: String) {
        val uri = Uri.parse(url)
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    override fun setOnGithubOpen(handler: () -> Unit) {
        githubLnk.setOnClickListener { handler.invoke() }
    }

    override fun setOnStart(handler: () -> Unit) {
        startBtn.setOnClickListener { handler.invoke() }
    }

    override fun loadPrefs() {
        phoneText.setText(repo.getPhone())
        cyclesText.setText(repo.getCycles().toString())
    }
}