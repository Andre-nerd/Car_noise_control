package ru.zoomparty.noise_controller.ui.compose.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.zoomparty.noise_controller.SoundViewModel

@Composable
fun VolumeScreen(
    onNavigateToMainScreen: () -> Unit,
    viewModel: SoundViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val deviceVolumeState by viewModel.stateVolume.collectAsState()
        val deviceNoiseState by viewModel.stateNoise.collectAsState()

        Text(
            text = "App sound! maxVolume:${deviceVolumeState.maxVolume} | curValue:${deviceVolumeState.currentVolume}",
            style = TextStyle(fontSize = 32.sp)
        )

        Button(onClick = {
            viewModel.upVolume()
        }) {
            Text(
                text = "Up"
            )
        }
        Button(onClick = {
            viewModel.downVolume()
        }) {
            Text(
                text = "Down"
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = "Noise around: ${deviceNoiseState.noiseLevel}",
            style = TextStyle(fontSize = 32.sp)
        )
    }
}