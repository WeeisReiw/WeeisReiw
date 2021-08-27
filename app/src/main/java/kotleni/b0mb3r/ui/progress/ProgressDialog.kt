package kotleni.b0mb3r.ui.progress

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotleni.b0mb3r.R
import kotleni.b0mb3r.ui.main.MainRepository

class ProgressDialog(ctx: Context, var repo: MainRepository): BottomSheetDialog(ctx), ProgressView {
    private val indicator: LinearProgressIndicator by lazy { findViewById(R.id.indicator)!! }

    override fun show() {
        this.setContentView(R.layout.dialog_progress)
        this.setCancelable(false)

        super.show()
        ProgressPresenter(this, repo)
    }

    override fun updateProgress(value: Int) {
        Handler(Looper.getMainLooper()).post {
            indicator.progress = value
        }
    }

    override fun doneExit() {
        Handler(Looper.getMainLooper()).post {
            this.dismiss()
        }
    }

    override fun numberParseError() {
        Toast.makeText(context, "Ошибка разбора номера", Toast.LENGTH_SHORT)
            .show()
        doneExit()
    }
}