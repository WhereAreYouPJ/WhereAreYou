package com.onmyway.data.searchlocation

import com.onmyway.domain.entity.schedule.LocationInformation

data class SearchLocationScreenUIState(
    val inputLocationName: String = "",
    val locationInfosList: List<LocationInformation> = emptyList()
)