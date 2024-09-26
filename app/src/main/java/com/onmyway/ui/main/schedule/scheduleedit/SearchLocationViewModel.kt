package com.onmyway.ui.main.schedule.scheduleedit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.onmyway.data.searchlocation.SearchLocationScreenSideEffect
import com.onmyway.data.searchlocation.SearchLocationScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchLocationViewModel @Inject constructor(
    private val application: Application,
//    private val getLocationAddressUseCase: GetLocationAddressUseCase
) : AndroidViewModel(application) {

    private val _searchLocationScreenUIState = MutableStateFlow(SearchLocationScreenUIState())
    val searchLocationScreenUIState = _searchLocationScreenUIState.asStateFlow()
    val searchLocationScreenSideEffect = MutableSharedFlow<SearchLocationScreenSideEffect>()

    fun updateInputLocationText(text: String) {
        _searchLocationScreenUIState.update {
            it.copy(inputLocationName = text)
        }
    }

    fun searchLocation() {
//        viewModelScope.launch(Dispatchers.Default) {
//            val response = getLocationAddressUseCase(_searchLocationScreenUIState.value.inputLocationName)
//            LogUtil.printNetworkLog("inputLocationName = ${_searchLocationScreenUIState.value.inputLocationName}", response, "지역 검색")
//            when (response) {
//                is NetworkResult.Success -> {
//                    response.data?.let { data ->
//                        _searchLocationScreenUIState.update {
//                            it.copy(locationInfosList = data.items)
//                        }
//                    }
//                }
//                is NetworkResult.Error -> {
//
//                }
//                is NetworkResult.Exception -> {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//        }
    }
}