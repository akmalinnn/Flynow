package com.km6.flynow.data.datasource.seat

import com.km6.flynow.data.model.Response
import com.km6.flynow.data.source.network.model.seat.SeatData
import com.km6.flynow.data.source.network.service.FlynowApiService


class SeatApiDataSource(
    private val services: FlynowApiService,
) : SeatDataSource {
    override suspend fun getSeat(id: Int): Response<List<SeatData>?> {
        return services.getSeat(id)
    }
}
