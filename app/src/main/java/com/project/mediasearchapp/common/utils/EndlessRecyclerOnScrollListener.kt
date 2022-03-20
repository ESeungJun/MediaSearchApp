package com.project.mediasearchapp.common.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * Custom Scroll listener for RecyclerView.
 * Based on implementation https://gist.github.com/ssinss/e06f12ef66c51252563e
 */
abstract class EndlessRecyclerOnScrollListener(private val linearLayoutManager: LinearLayoutManager?) : RecyclerView.OnScrollListener() {
    private var previousTotal = 0 // The total number of items in the dataset after the last load
    private var loading = true // True if we are still waiting for the last set of data to load.
    private val visibleThreshold = 5 // The minimum amount of items to have below your current scroll position before loading more.

    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0

    private var scrollState = RecyclerView.SCROLL_STATE_IDLE
    private var nextPage = 1

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (scrollState == RecyclerView.SCROLL_STATE_IDLE) {
            return
        }

        visibleItemCount = recyclerView.childCount
        totalItemCount = linearLayoutManager?.itemCount ?: 0
        firstVisibleItem = linearLayoutManager?.findFirstVisibleItemPosition() ?: 0

        if (totalItemCount == 0) {
            return
        }
        
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
        }
        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            // End has been reached

            // Do something
            nextPage++
            onLoadMore(nextPage)
            loading = true
        }
    }


    fun resetState() {
        this.nextPage = 1
        previousTotal = 0
        loading = true
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        scrollState = newState
    }

    abstract fun onLoadMore(nextPage: Int)
}