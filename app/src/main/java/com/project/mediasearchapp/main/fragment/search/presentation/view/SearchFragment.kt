package com.project.mediasearchapp.main.fragment.search.presentation.view

import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.project.mediasearchapp.R
import com.project.mediasearchapp.databinding.SearchFragmentBinding
import com.project.mediasearchapp.main.activity.home.presentation.viewmodel.HomeActViewModel
import com.project.mediasearchapp.main.fragment.AbsHomeBaseFragment
import com.project.mediasearchapp.main.fragment.favorite.presentation.data.FavoriteEventData
import com.project.mediasearchapp.main.fragment.search.presentation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : AbsHomeBaseFragment<SearchFragmentBinding, SearchViewModel>(R.layout.search_fragment) {

    override fun createViewModel() = viewModels<SearchViewModel>().value

    override fun initFragment(dataBinding: SearchFragmentBinding, viewModel: SearchViewModel, actViewModel: HomeActViewModel) {
        dataBinding.run {
            editInputKeyword.setOnEditorActionListener { textView, id, keyEvent ->
                if (id == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.getSearchResult(textView.text.toString(), false, 1)
                    true
                }
                false
            }
        }

        viewModel.run {
            errorMsg.observe(this@SearchFragment) {
                activity?.let { act ->
                    Toast.makeText(act, it, Toast.LENGTH_LONG).show()
                }
            }
        }

        actViewModel.run {
            favoriteEventData.observe(this@SearchFragment) { event ->
                when (event) {
                    is FavoriteEventData.AddFavorite -> {
                        (dataBinding.rvSearch.adapter as? SearchResultAdapter)?.notifyItemStatus(event.data)
                    }

                    is FavoriteEventData.RemoveFavorite -> {
                        (dataBinding.rvSearch.adapter as? SearchResultAdapter)?.notifyItemStatus(event.data)
                    }
                }
            }
        }
    }
}