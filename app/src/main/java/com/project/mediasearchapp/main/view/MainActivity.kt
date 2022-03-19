package com.project.mediasearchapp.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.project.mediasearchapp.R
import com.project.mediasearchapp.databinding.MainActivityBinding
import com.project.mediasearchapp.main.data.MainConstValue
import com.project.mediasearchapp.main.view.favorite.presentation.view.FavoriteFragment
import com.project.mediasearchapp.main.view.search.presentation.view.SearchFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            lifecycleOwner = this@MainActivity

            mainViewPager.apply {
                isUserInputEnabled = false
                adapter = MainViewPagerAdapter(this@MainActivity)
            }

            TabLayoutMediator(mainTabLayout, mainViewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> getString(R.string.label_tab_search)
                    1 -> getString(R.string.label_tab_favorite)
                    else -> ""
                }
            }.attach()
        }
    }

    inner class MainViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount() = MainConstValue.MAIN_TAB_COUNT

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