package com.project.mediasearchapp.main.activity.home.presentation.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.project.mediasearchapp.R
import com.project.mediasearchapp.databinding.HomeActivityBinding
import com.project.mediasearchapp.main.activity.home.presentation.data.HomeConstValue
import com.project.mediasearchapp.main.activity.home.presentation.viewmodel.HomeActViewModel
import com.project.mediasearchapp.main.fragment.favorite.presentation.view.FavoriteFragment
import com.project.mediasearchapp.main.fragment.search.presentation.view.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val binding by lazy { DataBindingUtil.setContentView<HomeActivityBinding>(this, R.layout.home_activity) }

    private val viewModel by viewModels<HomeActViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            lifecycleOwner = this@HomeActivity

            mainViewPager.apply {
                isUserInputEnabled = false
                adapter = MainViewPagerAdapter(this@HomeActivity)
            }

            TabLayoutMediator(mainTabLayout, mainViewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> getString(R.string.label_tab_search)
                    1 -> getString(R.string.label_tab_favorite)
                    else -> ""
                }
            }.attach()

            viewModel.getAllFavoriteList()
        }
    }

    inner class MainViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount() = HomeConstValue.MAIN_TAB_COUNT

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> {
                    SearchFragment()
                }

                else -> {
                    FavoriteFragment()
                }
            }
        }

    }
}