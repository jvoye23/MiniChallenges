package com.jvcodingsolutions.minichallenges.april_2026.editing_status

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch


class EditingStatusViewModel : ViewModel() {

    private val _text = MutableStateFlow("")
    val text = _text.asStateFlow()

    private val _status = MutableStateFlow(EditingState.Hidden)
    val status = _status.asStateFlow()

    // Treat inputs as a stream of events as per requirements
    // extraBufferCapacity ensures fast typing doesn't drop events before suspension
    private val typingEvents = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    init {
        // Stream 1: Immediately show "Editing..." when the user types
        viewModelScope.launch {
            typingEvents.collect {
                _status.value = EditingState.Editing
            }
        }

        // Stream 2: Handle the "Saved" and "Hidden" sequence via debounce
        viewModelScope.launch {
            @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
            typingEvents
                .debounce(1000L) // Wait for 1 second of inactivity
                .collectLatest {
                    _status.value = EditingState.Saved

                    // Show "Saved" indicator for 2 seconds
                    delay(2000L)

                    // IMPORTANT: Only clear the status if we are STILL in the Saved state.
                    // If the user started typing again during this 2-second delay, Stream 1
                    // will have already set the status back to 'Editing'.
                    if (_status.value == EditingState.Saved) {
                        _status.value = EditingState.Hidden
                    }
                }
        }
    }

    fun onTextChanged(newText: String) {
        _text.value = newText
        // Emit an event to our stream to trigger the reactive flow logic
        typingEvents.tryEmit(Unit)
    }
}