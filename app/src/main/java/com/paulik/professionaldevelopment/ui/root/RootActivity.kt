package com.paulik.professionaldevelopment.ui.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.paulik.professionaldevelopment.R
import com.paulik.professionaldevelopment.ui.stopwatch.StopwatchFragment

class RootActivity : AppCompatActivity() {

    private val stopwatchFragment: StopwatchFragment by lazy { StopwatchFragment.newInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container_frame_layout, stopwatchFragment)
                .commit()
        }
    }
}