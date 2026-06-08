package com.lagradost.cloudstream3.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.lagradost.cloudstream3.R
import com.lagradost.cloudstream3.databinding.FragmentLibraryKinoBinding
import com.lagradost.cloudstream3.ui.BaseFragment
import com.lagradost.cloudstream3.ui.download.DownloadViewModel
import com.lagradost.cloudstream3.mvvm.observe
import com.lagradost.cloudstream3.mvvm.Resource

class KinoLibraryFragment : BaseFragment<FragmentLibraryKinoBinding>(
    BindingCreator.Bind(FragmentLibraryKinoBinding::bind)
) {
    private val libraryViewModel: LibraryViewModel by activityViewModels()
    private val downloadViewModel: DownloadViewModel by activityViewModels()

    private var currentTab = "Downloads"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabs()
        observeData()
    }

    private fun setupTabs() {
        val tabs = listOf("Downloads", "Continue Watching", "Watchlist", "Favorites", "History", "Completed")
        binding.libraryTabsContainer.removeAllViews()
        
        tabs.forEach { tabName ->
            val tabView = LayoutInflater.from(context).inflate(R.layout.kino_pill_tab, binding.libraryTabsContainer, false) as TextView
            tabView.text = tabName
            updateTabStyle(tabView, tabName == currentTab)
            
            tabView.setOnClickListener {
                currentTab = tabName
                setupTabs() // Refresh styles
                updateContent()
            }
            binding.libraryTabsContainer.addView(tabView)
        }
    }

    private fun updateTabStyle(view: TextView, isActive: Boolean) {
        if (isActive) {
            view.setBackgroundResource(R.drawable.bg_red_pill)
            view.setTextColor(resources.getColor(R.color.white, null))
        } else {
            view.setBackgroundResource(R.drawable.bg_pill_inactive)
            view.setTextColor(resources.getColor(R.color.kino_muted, null))
        }
    }

    private fun observeData() {
        // Observe CloudStream data based on current tab
        updateContent()
    }

    private fun updateContent() {
        when (currentTab) {
            "Downloads" -> {
                binding.libraryContentRecycler.layoutManager = LinearLayoutManager(context)
                // Use downloadViewModel data
            }
            "Watchlist", "Favorites", "Completed" -> {
                binding.libraryContentRecycler.layoutManager = GridLayoutManager(context, 2)
                // Use libraryViewModel data
            }
            else -> {
                binding.libraryContentRecycler.layoutManager = LinearLayoutManager(context)
            }
        }
    }
}
