package com.example.countdown.ui.screen

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.countdown.ui.CountdownViewModel
import com.example.countdown.ui.formatTimeRemain
import com.example.countdown.ui.theme.CountdownTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@Composable
fun CountdownApp(
    modifier: Modifier = Modifier,
    cViewModel: CountdownViewModel = viewModel()
) {

    val uiState by cViewModel.uiState.collectAsState()

    val timeRemain = uiState.formatTimeRemain()


    if (uiState.isTicking) {
        LaunchedEffect(uiState) {
            coroutineScope {
                launch { cViewModel.beginCounting() }
            }
            cViewModel.resetCounting()
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