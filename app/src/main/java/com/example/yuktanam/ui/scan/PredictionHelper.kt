package com.example.yuktanam.ui.scan

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import com.example.yuktanam.R
import com.google.android.gms.tflite.client.TfLiteInitializationOptions
import com.google.android.gms.tflite.gpu.support.TfLiteGpu
import com.google.android.gms.tflite.java.TfLite
import com.google.firebase.ml.modeldownloader.CustomModel
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions
import com.google.firebase.ml.modeldownloader.DownloadType
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader
import org.tensorflow.lite.InterpreterApi
import org.tensorflow.lite.gpu.GpuDelegateFactory
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class PredictionHelper(
    private val modelName: String = "Plant-Detection.tflite",
    val context: Context,
    private val onResult: (String) -> Unit,
    private val onError: (String) -> Unit,
    private val onDownloadSuccess: () -> Unit,
) {

    private var interpreter: InterpreterApi? = null

    init {
        TfLiteGpu.isGpuDelegateAvailable(context).onSuccessTask { gpuAvailable ->
            val optionsBuilder = TfLiteInitializationOptions.builder()
            if (gpuAvailable) {
                optionsBuilder.setEnableGpuDelegateSupport(true)
            }
            TfLite.initialize(context, optionsBuilder.build())
        }.addOnSuccessListener {
            // Memulai pengunduhan model
            downloadModel()
        }.addOnFailureListener {
            onError(context.getString(R.string.tflite_is_not_initialized_yet))
        }
    }

    private fun downloadModel() {
        val conditions = CustomModelDownloadConditions.Builder()
            .requireWifi()
            .build()

        FirebaseModelDownloader.getInstance()
            .getModel(
                "Plant-Detection",
                DownloadType.LOCAL_MODEL,
                conditions
            )
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val model: CustomModel? = task.result
                    model?.file?.let { modelFile ->
                        try {
                            initializeInterpreter(modelFile)
                            onDownloadSuccess()
                        } catch (e: IOException) {
                            onError("Failed to initialize TensorFlow Lite Interpreter: ${e.message}")
                        }
                    } ?: run {
                        onError(context.getString(R.string.firebaseml_model_download_failed))
                    }
                } else {
                    onError(context.getString(R.string.firebaseml_model_download_failed))
                }
            }
            .addOnFailureListener { e ->
                onError("Model download failed: ${e.message}")
            }
    }

    private fun initializeInterpreter(modelFile: File) {
        interpreter?.close() // Tutup interpreter sebelumnya jika ada
        try {
            val options = InterpreterApi.Options()
                .setRuntime(InterpreterApi.Options.TfLiteRuntime.FROM_SYSTEM_ONLY)
                .addDelegateFactory(GpuDelegateFactory())

            interpreter = InterpreterApi.create(modelFile, options)
        } catch (e: Exception) {
            onError("Error initializing interpreter: ${e.message}")
            Log.e(TAG, e.message.toString())
        }
    }

    fun predict(inputString: String) {
        if (interpreter == null) {
            onError(context.getString(R.string.no_tflite_interpreter_loaded))
            return
        }
        val inputArray = FloatArray(1)
        inputArray[0] = inputString.toFloat()
        val outputArray = Array(1) { FloatArray(1) }
        try {
            interpreter?.run(inputArray, outputArray)
            onResult(outputArray[0][0].toString())
        } catch (e: Exception) {
            onError(context.getString(R.string.error_during_prediction))
            Log.e(TAG, e.message.toString())
        }
    }

    private fun loadModelFile(assetManager: AssetManager, modelPath: String): MappedByteBuffer {
        assetManager.openFd(modelPath).use { fileDescriptor ->
            FileInputStream(fileDescriptor.fileDescriptor).use { inputStream ->
                val fileChannel = inputStream.channel
                val startOffset = fileDescriptor.startOffset
                val declaredLength = fileDescriptor.declaredLength
                return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
            }
        }
    }

    companion object {
        private const val TAG = "PredictionHelper"
    }
}
