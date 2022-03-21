package com.project.mediasearchapp.main.fragment.favorite.presentation.view

import androidx.fragment.app.viewModels
import com.project.mediasearchapp.BR
import com.project.mediasearchapp.R
import com.project.mediasearchapp.databinding.FavoriteFragmentBinding
import com.project.mediasearchapp.main.activity.home.presentation.viewmodel.HomeActViewModel
import com.project.mediasearchapp.main.fragment.AbsHomeBaseFragment
import com.project.mediasearchapp.main.fragment.favorite.presentation.data.FavoriteEventData
import com.project.mediasearchapp.main.fragment.favorite.presentation.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : AbsHomeBaseFragment<FavoriteFragmentBinding, FavoriteViewModel>(R.layout.favorite_fragment) {

    override fun createViewModel() = viewModels<FavoriteViewModel>().value

    override fun initFragment(dataBinding: FavoriteFragmentBinding, viewModel: FavoriteViewModel, actViewModel: HomeActViewModel) {
        actViewModel.run {
            favoriteList.observe(this@FavoriteFragment) { list ->
                dataBinding.setVariable(BR.list, list)
            }

            favoriteEventData.observe(this@FavoriteFragment) { event ->
                when (event) {
                    is FavoriteEventData.AddFavorite -> {
                        (dataBinding.rvFavorite.adapter as? FavoriteAdapter)?.notifyAddItem(event.data)
                        dataBinding.rvFavorite.scrollToPosition(0)
                    }

                    is FavoriteEventData.RemoveFavorite -> {
                        (dataBinding.rvFavorite.adapter as? FavoriteAdapter)?.notifyRemoveItem(event.data)
                    }
                }
            }
        }
    }
}