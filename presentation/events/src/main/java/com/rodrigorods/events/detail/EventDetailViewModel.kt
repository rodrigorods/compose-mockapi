package com.rodrigorods.events.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigorods.events.model.Event
import com.rodrigorods.events.usecase.EventDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UIState {
    object FullPageLoading : UIState()
    data class DisplayingEventDetail(val event: Event) : UIState()
    object NetworkError : UIState()
}

class EventDetailViewModel(
    private val useCase: EventDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState>(UIState.FullPageLoading)
    val uiState: StateFlow<UIState> = _uiState

    fun getEventDetail(eventId: String) {
        viewModelScope.launch {
            useCase.getEventDetail(eventId)
                .onSuccess { _uiState.emit(UIState.DisplayingEventDetail(it)) }
                .onFailure { _uiState.emit(UIState.NetworkError) }
        }
    }
}
