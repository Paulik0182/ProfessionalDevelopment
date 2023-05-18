package com.paulik.professionaldevelopment.ui.stopwatch

import android.os.Bundle
import android.view.View
import com.paulik.professionaldevelopment.databinding.FragmentStopwatchBinding
import com.paulik.professionaldevelopment.ui.root.ViewBindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class StopwatchFragment : ViewBindingFragment<FragmentStopwatchBinding>(
    FragmentStopwatchBinding::inflate
) {

    private val viewModel: StopwatchViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        initViewModel()
    }

    private fun initView() {
        binding.firstStopwatch.buttonStart.setOnClickListener {
            viewModel.start(0)
        }
        binding.firstStopwatch.buttonPause.setOnClickListener {
            viewModel.pause(0)
        }
        binding.firstStopwatch.buttonStop.setOnClickListener {
            viewModel.stop(0)
        }

        binding.secondStopwatch.buttonStart.setOnClickListener {
            viewModel.start(1)
        }
        binding.secondStopwatch.buttonPause.setOnClickListener {
            viewModel.pause(1)
        }
        binding.secondStopwatch.buttonStop.setOnClickListener {
            viewModel.stop(1)
        }
    }

    private fun initViewModel() {
        viewModel.startTicker()
        viewModel.currentTime.observe(viewLifecycleOwner) {
            binding.firstStopwatch.textTime.text = it
        }

        viewModel.secondTime.observe(viewLifecycleOwner) {
            binding.secondStopwatch.textTime.text = it
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = StopwatchFragment()
    }
}