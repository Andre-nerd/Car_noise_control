package ru.zoomparty.noise_controller.data

import android.media.MediaRecorder
import android.os.Build
import ru.zoomparty.noise_controller.App
import ru.zoomparty.noise_controller.data.config.temp_file_record
import java.io.File

class NoiseController() {
    private var audioRecorder: MediaRecorder? = null

    fun start() {
        audioRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(App.appContext)
        } else {
            MediaRecorder()
        }
        audioRecorder?.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(getAudioPath())
            prepare()
            start()
        }
    }

    fun stop() {
        audioRecorder?.let {
            it.stop()
            it.release()
        }
        deleteTempFileRecord()
        audioRecorder = null
    }

    fun isAudioRecording() = audioRecorder != null
    fun getNoise(): Int = audioRecorder?.maxAmplitude ?: -1

    private fun getAudioPath(): String {
        return temp_file_record
    }

    companion object {
        const val NOISE_IN_MIC = "NOISE_IN_MIC"
        fun deleteTempFileRecord() {
            val tempFile: File = File(temp_file_record)
            if (tempFile.exists()) tempFile.delete()
        }
    }
}