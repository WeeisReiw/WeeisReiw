package kotleni.b0mb3r.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import kotleni.b0mb3r.R
import kotleni.b0mb3r.ui.progress.ProgressDialog


class MainActivity : AppCompatActivity(), MainView {
    private val logo: ImageView by lazy { findViewById(R.id.logo) }
    private val phone: EditText by lazy { findViewById(R.id.phone) }
    private val cycles: EditText by lazy { findViewById(R.id.cycles) }
    private val start: MaterialButton by lazy { findViewById(R.id.start_btn) }
    private val gitlnk: TextView by lazy { findViewById(R.id.github_lnk) }

    private val repo: MainRepository by lazy { MainRepository(getSharedPreferences(packageName, MODE_PRIVATE)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MainPresenter(this, repo)
    }

    override fun loadPrefs() {
        phone.setText(repo.getPhone())
        cycles.setText(repo.getCycles().toString())
    }

    override fun setOnGithubOpen(handler: () -> Unit) {
        gitlnk.setOnClickListener { handler.invoke() }
    }

    override fun setOnStart(handler: () -> Unit) {
        start.setOnClickListener { handler.invoke() }
    }

    override fun openUrl(url: String) {
        val uri = Uri.parse(url)
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    override fun startAttack() {
        // save data
        try { // try
            repo.setPhone(phone.text.toString())
            repo.setCycles(cycles.text.toString().toInt())
        } catch (e: Exception) {
            Toast.makeText(this, "Проверьте правильность введеныхх данных!", Toast.LENGTH_SHORT)
                .show()
            return
        }

        // show dialog
        val dialog = ProgressDialog(this, repo)
        dialog.show()
    }
}