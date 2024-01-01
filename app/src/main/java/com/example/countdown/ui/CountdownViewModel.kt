package com.example.countdown.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.countdown.data.Counter
import com.example.countdown.data.TimeRemain
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CountdownUiState(
    val isTicking: Boolean = true,
    val timeRemain: Long = 0,
    val isCounterInitialized: Boolean = false
)

fun CountdownUiState.formatTimeRemain(): TimeRemain {
    return Counter.formatTimeRemain(timeRemain)
}

class CountdownViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CountdownUiState())
    val uiState: StateFlow<CountdownUiState> = _uiState.asStateFlow()

    suspend fun beginCounting(
        days: Long = 0,
        hours: Long = 0,
        minutes: Long = 0,
        seconds: Long = 24
    ) {
        val c = Counter(days, hours, minutes, seconds)
        val countdownTime = c.getTimeInMs()
        if (!_uiState.value.isCounterInitialized) {
            _uiState.update { currentState ->
                currentState.copy(timeRemain = countdownTime, isCounterInitialized = true)
            }
        }
        try {
            while (_uiState.value.timeRemain > 0) {
                delay(1000)
                _uiState.update { currentState ->
                    currentState.copy(timeRemain = currentState.timeRemain - 1000)
                }
            }
        } catch (e: Exception) {
            Log.d("", e.message ?: "error in Counter:start")
        }
    }


    fun resetCounting() {
        _uiState.update {// currentState ->
            CountdownUiState(isTicking = false)
        }
    }

}