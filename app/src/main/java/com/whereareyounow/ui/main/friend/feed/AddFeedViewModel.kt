package com.whereareyounow.ui.main.friend.feed

import android.app.Application
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.data.addfeed.AddFeedScreenUIState
import com.whereareyounow.data.cached.AuthData
import com.whereareyounow.domain.entity.feed.FeedImageInfo
import com.whereareyounow.domain.entity.schedule.ScheduleListItem
import com.whereareyounow.domain.request.feed.CreateFeedRequest
import com.whereareyounow.domain.request.schedule.GetScheduleListRequest
import com.whereareyounow.domain.usecase.feed.CreateFeedUseCase
import com.whereareyounow.domain.usecase.schedule.GetScheduleListUseCase
import com.whereareyounow.domain.util.onError
import com.whereareyounow.domain.util.onException
import com.whereareyounow.domain.util.onSuccess
import com.whereareyounow.util.calendar.parseLocalDate
import com.whereareyounow.util.copy
import com.whereareyounow.util.getFileExtension
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class AddFeedViewModel @Inject constructor(
    private val getScheduleListUseCase: GetScheduleListUseCase,
    private val createFeedUseCase: CreateFeedUseCase,
    private val application: Application,
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(AddFeedScreenUIState())
    val uiState = _uiState.asStateFlow()

    fun updateSelectedSchedule(schedule: ScheduleListItem) {
        _uiState.update {
            it.copy(
                selectedSchedule = schedule
            )
        }
    }

    fun updateTitle(title: String) {
        _uiState.update {
            it.copy(
                title = title
            )
        }
    }

    fun updateContent(content: String) {
        _uiState.update {
            it.copy(
                content = content
            )
        }
    }

    fun addImages(uriList: List<String>) {
        _uiState.update {
            it.copy(
                imageUris = it.imageUris.plus(uriList)
            )
        }
    }

    fun createFeed() {
        val requestBody = CreateFeedRequest(
            scheduleSeq = _uiState.value.selectedSchedule!!.scheduleSeq,
            memberSeq = AuthData.memberSeq,
            title = _uiState.value.title,
            content = _uiState.value.content,
            feedImageOrders = (1 ..  _uiState.value.imageUris.size).toList()
        )
        val imageList = _uiState.value.imageUris.mapIndexed { idx, item ->
            val fileExtension = getFileExtension(application, item.toUri())
            val fileName = "temporary_file" + if (fileExtension != null) ".$fileExtension" else ""
            val imageFile = File(application.cacheDir, fileName)
            imageFile.createNewFile()
            try {
                val oStream = FileOutputStream(imageFile)
                val inputStream = application.contentResolver.openInputStream(item.toUri())
                inputStream?.let {
                    copy(inputStream, oStream)
                }
                oStream.flush()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            imageFile
        }
        createFeedUseCase(requestBody, imageList)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->

                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    private fun getScheduleList() {
        val requestData = GetScheduleListRequest(
            memberSeq = AuthData.memberSeq,
            page = 0,
            size = 100
        )
        getScheduleListUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    data?.let {
                        val scheduleMap = data.scheduleList.groupBy { parseLocalDate(it.startTime).toString() }
                        _uiState.update {
                            it.copy(
                                scheduleListMap = scheduleMap
                            )
                        }
                    }
                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    init {
        getScheduleList()
    }
}