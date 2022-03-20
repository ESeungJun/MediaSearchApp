package com.project.mediasearchapp.main.view.search.presentation.view

import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.mediasearchapp.R
import com.project.mediasearchapp.common.BaseBindFragment
import com.project.mediasearchapp.common.utils.EndlessRecyclerOnScrollListener
import com.project.mediasearchapp.databinding.SearchFragmentBinding
import com.project.mediasearchapp.main.view.search.presentation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseBindFragment<SearchFragmentBinding, SearchViewModel>(R.layout.search_fragment) {


    override fun createViewModel() = viewModels<SearchViewModel>().value

    override fun initFragment(dataBinding: SearchFragmentBinding, viewModel: SearchViewModel) {
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
            errorMsg.observe(this@SearchFragment.viewLifecycleOwner) {
                activity?.let { act ->
                    Toast.makeText(act, it, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}