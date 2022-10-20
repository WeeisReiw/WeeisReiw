package kotleni.b0mb3r.ui.dialog

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.dm.bomber.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.progressindicator.LinearProgressIndicator

class ProgressDialog(ctx: Context): BottomSheetDialog(ctx) {
    private val indicator: CircularProgressIndicator by lazy { findViewById(R.id.indicator)!! }

    override fun show() {
        this.setContentView(R.layout.dialog_progress)
        this.setCancelable(false)

        super.show()
    }

    fun doneExit() {
        Handler(Looper.getMainLooper()).post {
            this.dismiss()
        }
    }
}