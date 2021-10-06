package kotleni.b0mb3r

import kotleni.b0mb3r.ui.main.MainRepository

class Bomber(var target: Phone, var cycles: Int): AttackManager.Callback {
    private var handlerProgress: ((value: Int) -> Unit)? = null
    private var handlerFinish: (() -> Unit)? = null

    private val manager: AttackManager by lazy { AttackManager(this) }

    fun start() {
        manager.performAttack(target.country, target.number, cycles)
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

    override fun onAttackStart(serviceCount: Int, numberOfCycles: Int) {

    }

    override fun onProgressChange(progress: Int) {
        if(handlerProgress != null)
            handlerProgress!!.invoke(progress)
    }
}