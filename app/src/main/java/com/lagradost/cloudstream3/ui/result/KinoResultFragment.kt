package com.lagradost.cloudstream3.ui.result

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.lagradost.cloudstream3.R
import com.lagradost.cloudstream3.databinding.FragmentResultKinoBinding
import com.lagradost.cloudstream3.ui.BaseFragment
import com.lagradost.cloudstream3.mvvm.observe
import com.lagradost.cloudstream3.mvvm.Resource

class KinoResultFragment : BaseFragment<FragmentResultKinoBinding>(
    BindingCreator.Bind(FragmentResultKinoBinding::bind)
) {
    private val viewModel: ResultViewModel2 by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupUI()
        observeData()
    }

    private fun setupUI() {
        binding.apply {
            // Back button logic
            backButton.setOnClickListener {
                activity?.onBackPressed()
            }
            // Watch now button logic
            // Watchlist, Download, Share logic
        }
    }

    private fun observeData() {
        observe(viewModel.page) { resource ->
            when (resource) {
                is Resource.Success -> {
                    // Update details with CloudStream data
                }
                is Resource.Loading -> {
                    // Show loading state
                }
                is Resource.Failure -> {
                    // Show error state
                }
            }
        }
    }
}
