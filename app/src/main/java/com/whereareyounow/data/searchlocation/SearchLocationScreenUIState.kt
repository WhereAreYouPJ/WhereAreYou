package com.whereareyounow.data.searchlocation

import com.whereareyounow.domain.entity.schedule.LocationInformation

data class SearchLocationScreenUIState(
    val inputLocationName: String = "",
    val locationInfosList: List<LocationInformation> = emptyList()
)