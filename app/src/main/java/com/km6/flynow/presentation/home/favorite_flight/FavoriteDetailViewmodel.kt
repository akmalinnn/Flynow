package com.km6.flynow.presentation.home.favorite_flight

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.km6.flynow.data.model.FavoriteFlight


class FavoriteDetailViewModel(
    private val extras: Bundle?,
) : ViewModel() {

    private val _favoriteItem = MutableLiveData<FavoriteFlight?>()
    val favoriteItem: LiveData<FavoriteFlight?> get() = _favoriteItem

    init {
        val favoriteItem = extras?.getParcelable<FavoriteFlight>(FavoriteDetailActivity.EXTRA_FAVORITE_FLIGHT_ITEM)
        _favoriteItem.value = favoriteItem
    }

}
