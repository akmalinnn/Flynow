package com.km6.flynow.data.mapper

import com.km6.flynow.data.model.DestinationHistory
import com.km6.flynow.data.source.local.database.entity.DestinationHistoryEntity

fun DestinationHistory?.toDestinationHistoryEntity() =
    DestinationHistoryEntity(
        id =this?.id,
        destinationName = this?.destinationName.orEmpty()
    )

fun DestinationHistoryEntity?.toDestinationHistory() =
    DestinationHistory(
        id =this?.id,
        destinationName = this?.destinationName.orEmpty()
    )

fun List<DestinationHistoryEntity?>.toDestinationHistoryList() = this.map { it.toDestinationHistory() }