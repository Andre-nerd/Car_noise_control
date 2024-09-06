package ru.zoomparty.noise_controller

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.zoomparty.noise_controller.ui.compose.navigation.AppNavHost
import ru.zoomparty.noise_controller.ui.theme.Audio_controllerTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Audio_controllerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)){
                        FeatureThatRequiresPermissions()
                    }
                }
            }
        }
    }


    @SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    private fun FeatureThatRequiresPermissions() {
        val permissionsState = rememberMultiplePermissionsState(
                listOf(android.Manifest.permission.RECORD_AUDIO)
        )
        if (permissionsState.allPermissionsGranted) {
            AppNavHost()
        } else {
            ShowScreenRationalePermission(permissionsState)
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun ShowScreenRationalePermission(permissionsState: MultiplePermissionsState){
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center) {
            Column(modifier = Modifier.padding(16.dp)) {

                Spacer(modifier = Modifier.size(20.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    Text("Для корректной работы приложения необходимо предоставить пользовательские разрешения.")
                }
                Spacer(modifier = Modifier.size(40.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    Button(onClick = {
                        permissionsState.launchMultiplePermissionRequest()
                    }) {
                        Text("Предоставить разрешения")
                    }
                }
            }
        }
    }
}



