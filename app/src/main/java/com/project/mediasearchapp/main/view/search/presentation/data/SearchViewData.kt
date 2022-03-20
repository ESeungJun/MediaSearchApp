package com.project.mediasearchapp.main.view.search.presentation.data

data class SearchViewData(
    val imageUrl: String,
    val dateTime: String,
    val isFavorite: Boolean
) : Comparable<SearchViewData> {

    override fun compareTo(other: SearchViewData): Int {
        return other.dateTime.compareTo(this.dateTime)
    }

}
