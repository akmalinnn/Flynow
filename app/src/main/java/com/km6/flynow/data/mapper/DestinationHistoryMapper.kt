package com.km6.flynow.data.mapper

import com.km6.flynow.data.model.Airport
import com.km6.flynow.data.model.Destination
import com.km6.flynow.data.source.local.database.entity.DestinationHistoryEntity

fun Destination?.toDestinationHistoryEntity() =
    DestinationHistoryEntity(
        id =this?.id.orEmpty(),
        destinationName = this?.destinationName.orEmpty()
    )

fun DestinationHistoryEntity?.toDestination() =
    Destination(
        id =this?.id.orEmpty(),
        destinationName = this?.destinationName.orEmpty()
    )

fun List<DestinationHistoryEntity?>.toDestinationHistoryList() = this.map { it.toDestination() }