package com.project.mediasearchapp.itemview.presentation.data

data class ImageItemViewData(
    val imageUrl: String,
    val dateTime: String,
    var isFavorite: Boolean
) : Comparable<ImageItemViewData> {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        if (this.imageUrl == (other as? ImageItemViewData)?.imageUrl) {
            return true
        }

        return false
    }

    override fun compareTo(other: ImageItemViewData): Int {
        return other.dateTime.compareTo(this.dateTime)
    }

    override fun hashCode(): Int {
        var result = imageUrl.hashCode()
        result = 31 * result + dateTime.hashCode()
        result = 31 * result + isFavorite.hashCode()
        return result
    }

}
