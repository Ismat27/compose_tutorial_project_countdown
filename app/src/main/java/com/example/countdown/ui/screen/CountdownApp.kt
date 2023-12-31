package com.example.countdown.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.countdown.ui.theme.CountdownTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


data class TimeRemain(
    val days: String,
    val hours: String,
    val minutes: String,
    val seconds: String
)

class Counter(
    days: Long = 0,
    hours: Long = 0,
    minutes: Long = 0,
    seconds: Long = 0
) {

    private var timeRemaining by mutableLongStateOf(
        days * 24 * 3600 * 1000 + hours * 3600 * 1000 + minutes * 60 * 1000 + seconds * 1000
    )

    suspend fun start() {
        try {
            while (timeRemaining > 0) {
                delay(1000)
                timeRemaining -= 1000
            }
        } catch (e: Exception) {
            Log.d("", e.message ?: "error in Counter:start")
        }

    }

    private fun formatTimeUnit(unit: Int): String {
        return if (unit < 10) "0$unit" else unit.toString()
    }

    fun timeRemain(milliseconds: Long = timeRemaining): TimeRemain {
        val seconds = (milliseconds / 1000).toInt()
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        val remainingHours = formatTimeUnit(hours % 24)
        val remainingMinutes = formatTimeUnit(minutes % 60)
        val remainingSeconds = formatTimeUnit(seconds % 60)

        return TimeRemain(
            formatTimeUnit(days),
            remainingHours,
            remainingMinutes,
            remainingSeconds
        )
    }

}

@Composable
fun CountdownApp(modifier: Modifier = Modifier) {

    val counter = remember {
        Counter(days = 1)
    }

    val timeRemain = counter.timeRemain()

    var isTicking by remember {
        mutableStateOf(true)
    }

    if (isTicking) {
        LaunchedEffect(counter) {
            coroutineScope {
                launch { counter.start() }
            }
            isTicking = false
        }
    }

    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TimeRemainComponent(title = "Days", value = timeRemain.days)
        Spacer(modifier = Modifier.width(12.dp))
        TimeRemainComponent(title = "Hours", value = timeRemain.hours)
        Spacer(modifier = Modifier.width(12.dp))
        TimeRemainComponent(title = "Minutes", value = timeRemain.minutes)
        Spacer(modifier = Modifier.width(12.dp))
        TimeRemainComponent(title = "Seconds", value = timeRemain.seconds)
    }
}

@Composable
fun TimeRemainComponent(title: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = title)
        Text(text = value)
    }
}

@Preview(showBackground = true)
@Composable
fun CountdownPreview() {
    CountdownTheme {
        CountdownApp()
    }
}