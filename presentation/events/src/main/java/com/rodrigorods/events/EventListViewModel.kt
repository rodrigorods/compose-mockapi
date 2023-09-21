package com.rodrigorods.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigorods.events.model.Event
import com.rodrigorods.events.usecase.EventListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UIState {
    object FullPageLoading : UIState()
    data class DisplayingEventList(val eventList: List<Event>) : UIState()
    object NetworkError : UIState()
}

class EventListViewModel(
    private val useCase: EventListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState>(UIState.FullPageLoading)
    val uiState: StateFlow<UIState> = _uiState

    init {
        getEventList()
    }

    fun getEventList() {
        viewModelScope.launch {
            useCase.getEventList()
                .onSuccess { _uiState.emit(UIState.DisplayingEventList(it)) }
                .onFailure { _uiState.emit(UIState.NetworkError) }
        }
    }
}
