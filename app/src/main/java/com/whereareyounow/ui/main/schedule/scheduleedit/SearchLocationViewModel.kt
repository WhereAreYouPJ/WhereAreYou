package com.whereareyounow.ui.main.schedule.scheduleedit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.data.searchlocation.SearchLocationScreenSideEffect
import com.whereareyounow.data.searchlocation.SearchLocationScreenUIState
import com.whereareyounow.domain.request.location.SearchLocationAddressRequest
import com.whereareyounow.domain.usecase.location.SearchLocationAddressUseCase
import com.whereareyounow.domain.util.onError
import com.whereareyounow.domain.util.onException
import com.whereareyounow.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchLocationViewModel @Inject constructor(
    private val application: Application,
    private val searchLocationAddressUseCase: SearchLocationAddressUseCase
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(SearchLocationScreenUIState())
    val uiState = _uiState.asStateFlow()
    val sideEffect = MutableSharedFlow<SearchLocationScreenSideEffect>()

    fun updateInputLocationText(text: String) {
        _uiState.update {
            it.copy(inputLocationName = text)
        }
    }

    fun searchLocation() {
        val requestData = SearchLocationAddressRequest(_uiState.value.inputLocationName)
        searchLocationAddressUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    data?.let {
                        _uiState.update {
                            it.copy(
                                locationInfosList = data.items
                            )
                        }
                    }
                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }
}