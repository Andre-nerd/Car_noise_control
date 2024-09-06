package ru.zoomparty.noise_controller

import android.content.Context
import android.media.AudioManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.zoomparty.noise_controller.data.NoiseController
import ru.zoomparty.noise_controller.data.NoiseController.Companion.NOISE_IN_MIC
import ru.zoomparty.noise_controller.domain.state.DeviceMicState
import ru.zoomparty.noise_controller.domain.state.DeviceVolumeState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SoundViewModel:ViewModel() {
    private val audioManager = App.appContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager?
    private val noiseController = NoiseController()


    private val _stateVolume = MutableStateFlow(DeviceVolumeState.INITIAL)
    val stateVolume = _stateVolume.asStateFlow()
    private val _stateNoise = MutableStateFlow(DeviceMicState.INITIAL)
    val stateNoise= _stateNoise.asStateFlow()



    init {
        viewModelScope.launch(Dispatchers.Default) {
            while(true){
                delay(500)
                getSoundState()

            }
        }
        noiseController.start()
        viewModelScope.launch(Dispatchers.Default) {
            while (true){
                delay(100)
                val noise = noiseController.getNoise()
                Log.d(NOISE_IN_MIC,"volume in mic $noise")
                _stateNoise.update { DeviceMicState(noiseLevel = noise) }
            }
        }
    }

    fun getSoundState(){
        val mVolume: Int = audioManager?.getStreamMaxVolume(AudioManager.STREAM_MUSIC) ?: -1
        val cValue: Int = audioManager?.getStreamVolume(AudioManager.STREAM_MUSIC) ?: -1
        _stateVolume.update { DeviceVolumeState(
            maxVolume = mVolume,
            currentVolume = cValue
        ) }
    }
    fun upVolume(){
        val newVolume = if(stateVolume.value.currentVolume < stateVolume.value.maxVolume) stateVolume.value.currentVolume + 1 else
            stateVolume.value.maxVolume
        audioManager?.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, 0)
    }
    fun downVolume(){
        val newVolume = if(stateVolume.value.currentVolume > 0) stateVolume.value.currentVolume - 1 else
            0
        audioManager?.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, 0)
    }

    override fun onCleared() {
        noiseController.stop()
        super.onCleared()
    }
}