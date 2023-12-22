package com.whereareyounow.ui.home.schedule.newschedule

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.domain.entity.schedule.LocationInformation
import com.whereareyounow.domain.usecase.location.GetLocationAddressUseCase
import com.whereareyounow.domain.util.LogUtil
import com.whereareyounow.domain.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewLocationViewModel @Inject constructor(
    application: Application,
    private val getLocationAddressUseCase: GetLocationAddressUseCase
) : AndroidViewModel(application) {

    private val _inputLocationText = MutableStateFlow("")
    val inputLocationText: StateFlow<String> = _inputLocationText
    private val _locationInformationList = MutableStateFlow(listOf<LocationInformation>())
    val locationInformationList: StateFlow<List<LocationInformation>> = _locationInformationList

    fun updateInputLocationText(text: String) {
        _inputLocationText.update {
            text
        }
    }

    fun searchLocation() {
        viewModelScope.launch(Dispatchers.Default) {
            val response = getLocationAddressUseCase(inputLocationText.value)
            LogUtil.printNetworkLog(response, "지역 검색")
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { data ->
                        _locationInformationList.update {
                            data.items
                        }
                    } ?: {
                        _locationInformationList.update {
                            listOf()
                        }
                    }
                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Exception -> {

                }
            }
        }
    }
}