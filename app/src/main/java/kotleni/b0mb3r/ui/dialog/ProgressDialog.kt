package kotleni.b0mb3r.ui.dialog

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import com.dm.bomber.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotleni.b0mb3r.ui.MainRepository

class ProgressDialog(ctx: Context): BottomSheetDialog(ctx) {
    private val indicator: LinearProgressIndicator by lazy { findViewById(R.id.indicator)!! }

    override fun show() {
        this.setContentView(R.layout.dialog_progress)
        this.setCancelable(false)

        super.show()
    }

    fun updateProgress(value: Int) {
        Handler(Looper.getMainLooper()).post {
            indicator.progress = value
        }
    }

    fun doneExit() {
        Handler(Looper.getMainLooper()).post {
            this.dismiss()
        }
    }
}