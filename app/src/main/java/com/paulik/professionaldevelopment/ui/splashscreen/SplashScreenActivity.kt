package com.paulik.professionaldevelopment.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import com.paulik.core.ViewBindingActivity
import com.paulik.professionaldevelopment.databinding.ActivitySplashScreenBinding
import com.paulik.professionaldevelopment.ui.root.RootActivity

private const val DELAY_TIME_IN_MILLIS = 2_000L

class SplashScreenActivity : ViewBindingActivity<ActivitySplashScreenBinding>(
    ActivitySplashScreenBinding::inflate
) {

    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onCountDownTimerAndOpeningRootActivity()

//        onOpenRootActivity()
    }

    private fun onCountDownTimerAndOpeningRootActivity() {
        /**  создается объект CountDownTimer, который выполняется в течение указанной задержки
         * и затем переходит на RootActivity */
        countDownTimer = object : CountDownTimer(DELAY_TIME_IN_MILLIS, DELAY_TIME_IN_MILLIS) {

            override fun onTick(mollisUntilFinished: Long) {}

            /**  метод вызывается после того, как отсчет таймера завершится */
            override fun onFinish() {
                val intent = Intent(this@SplashScreenActivity, RootActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        countDownTimer!!.start()
    }

    /** Как вариант 2 */
    private fun onOpenRootActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashScreenActivity, RootActivity::class.java)
            startActivity(intent)
            finish()
        }, DELAY_TIME_IN_MILLIS)

    }

    override fun onDestroy() {
        super.onDestroy()
        /** Воизбежании утечек памяти необходимо закрыть countDownTimer */
        countDownTimer?.cancel()
    }
}