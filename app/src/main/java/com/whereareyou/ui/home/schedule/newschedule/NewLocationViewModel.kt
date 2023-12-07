package com.whereareyou.ui.home.schedule.newschedule

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyou.domain.entity.schedule.LocationInformation
import com.whereareyou.domain.usecase.location.GetLocationAddressUseCase
import com.whereareyou.domain.util.NetworkResult
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
            when (val getLocationAddressResult = getLocationAddressUseCase.getLocationAddress(inputLocationText.value)) {
                is NetworkResult.Success -> {
                    _locationInformationList.update {
                        if (getLocationAddressResult.data != null) {
                            getLocationAddressResult.data!!.items
                        } else {
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