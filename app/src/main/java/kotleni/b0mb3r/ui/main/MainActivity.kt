package kotleni.b0mb3r.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import kotleni.b0mb3r.R
import kotleni.b0mb3r.data.ProxiesResult
import kotleni.b0mb3r.data.Proxy
import kotleni.b0mb3r.databinding.ActivityMainBinding
import kotleni.b0mb3r.databinding.DialogProxiesBinding
import kotleni.b0mb3r.ui.progress.ProgressDialog
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity(), MainView {
    companion object {
        var repository: MainRepository? = null
    }

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val repo: MainRepository by lazy { MainRepository(getSharedPreferences(packageName, MODE_PRIVATE)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        val testDeviceIds = Arrays.asList("119D264F27A04CEFA80BE527A0CD0C40")
//        val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
//        MobileAds.setRequestConfiguration(configuration)
        MobileAds.initialize(this) { }
        val adRequest = AdRequest.Builder()
            .build()
        binding.adView.loadAd(adRequest)


        MainActivity.repository = repo
        MainPresenter(this, repo)
    }

    override fun loadPrefs() {
        binding.phone.setText(repo.getPhone())
        binding.cycles.setText(repo.getCycles().toString())
    }

    override fun setOnGithubOpen(handler: () -> Unit) {
        binding.githubLnk.setOnClickListener { handler.invoke() }
    }

    override fun setOnStart(handler: () -> Unit) {
        binding.startBtn.setOnClickListener { handler.invoke() }
    }

    override fun setOnProxyList(handler: () -> Unit) {
        binding.proxiesBtn.setOnClickListener { handler.invoke() }
    }

    override fun openUrl(url: String) {
        val uri = Uri.parse(url)
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    override fun openProxiesDialog() {
        val binding = DialogProxiesBinding.inflate(this.layoutInflater)
        var text = ""
        text.let {
            repo.getProxies().forEach {
                text += "${it.ip}:${it.port}\n"
            }
        }
        MaterialDialog(this, BottomSheet(LayoutMode.WRAP_CONTENT)).apply {
            title = getString(R.string.proxy_list)
            cancelable(false)
            customView(null, binding.root)
            positiveButton(-1, "Сохранить") {
                val arr = ArrayList<Proxy>()
                binding.edittext.text.toString().split("\n").forEach {
                    val spl = it.split(":")
                    if(spl.size > 1) {
                        arr.add(
                            Proxy(
                                spl[0],
                                spl[1]
                            )
                        )
                    }
                }

                repo.setProxies(arr)
            }
            negativeButton(-1, "Скачать с сервера") {
                Toast.makeText(applicationContext, "Подождите немного...", Toast.LENGTH_SHORT)
                    .show()
                thread {
                    val client = OkHttpClient()
                    val request = Request.Builder()
                        .url("https://proxylist.geonode.com/api/proxy-list?limit=15&page=1&sort_by=lastChecked&sort_type=desc&speed=medium&protocols=http%2Chttps")
                    val response = client.newCall(request.build()).execute()

                    val gson = Gson()
                    val res = gson.fromJson(response.body?.string(), ProxiesResult::class.java)
                    repo.setProxies(res.data)

                    Thread.sleep(230)
                    runOnUiThread { openProxiesDialog() }
                }
            }
            show {
                binding.edittext.setText(text)
            }
        }
    }

    override fun startAttack() {
        // save data
        try { // try
            repo.setPhone(binding.phone.text.toString())
            repo.setCycles(binding.cycles.text.toString().toInt())
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.input_invalid), Toast.LENGTH_SHORT)
                .show()
            return
        }

        // show dialog
        val dialog = ProgressDialog(this, repo)
        dialog.show()
    }
}