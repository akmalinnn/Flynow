package com.km6.flynow.data.repository

import com.km6.flynow.data.datasource.favorite_flight.FavoriteFlightDataSource
import com.km6.flynow.data.datasource.history.HistoryDataSource
import com.km6.flynow.data.mapper.toHistory
import com.km6.flynow.data.model.FavoriteFlight
import com.km6.flynow.data.model.history.History
import com.km6.flynow.data.mapper.toFavoriteFlight
import com.km6.flynow.data.mapper.toFavoriteFlightList
import com.km6.flynow.data.source.network.model.history.HistoryItemResponse
import com.km6.flynow.utils.ResultWrapper
import com.km6.flynow.utils.proceedFlow
import kotlinx.coroutines.flow.Flow


interface FavoriteFlightRepository {
    fun getFavoriteFlight(): Flow<ResultWrapper<List<FavoriteFlight>>>
}

class FavoriteFlightRepositoryImpl(private val favoriteFlightDataSource: FavoriteFlightDataSource) : FavoriteFlightRepository {
    override fun getFavoriteFlight(): Flow<ResultWrapper<List<FavoriteFlight>>> {
        return proceedFlow {
            val response = favoriteFlightDataSource.getFavoriteFlight()
            if (response.message == "Success") {
                favoriteFlightDataSource.getFavoriteFlight().data.toFavoriteFlightList()
            } else {
                throw Exception(response.message)
            }
        }
    }
}
