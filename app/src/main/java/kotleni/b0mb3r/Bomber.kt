package kotleni.b0mb3r

import com.dm.bomber.bomber.Attack
import com.dm.bomber.bomber.Callback

class Bomber(var target: Phone, var cycles: Int): Callback {
    private var handlerProgress: ((value: Int) -> Unit)? = null
    private var handlerFinish: (() -> Unit)? = null

    private val manager: Attack by lazy { Attack(this, target.country, target.number, cycles) }

    fun start() {
        manager.start()
        // manager.performAttack(target.country, target.number, cycles)
    }

    fun setOnUpdateProgress(handler: (value: Int) -> Unit) {
        handlerProgress = handler
    }

    fun setOnFinish(handler: () -> Unit) {
        handlerFinish = handler
    }

    override fun onAttackEnd() {
        if(handlerFinish != null)
            handlerFinish!!.invoke()
    }

    override fun onAttackStart(serviceCount: Int, numberOfCycles: Int) { }

    override fun onProgressChange(progress: Int) {
        if(handlerProgress != null)
            handlerProgress!!.invoke(progress)
    }
}