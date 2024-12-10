package com.example.yuktanam.ui.scan

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.widget.Toast
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
    private val onDownloadSuccess: (String) -> Unit,
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
                            val successMessage = context.getString(R.string.download_success)
                            onDownloadSuccess(successMessage)
                            Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
                        } catch (e: IOException) {
                            val errorMessage = "Failed to initialize TensorFlow Lite Interpreter: ${e.message}"
                            onError(errorMessage)
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    } ?: run {
                        val errorMessage = context.getString(R.string.firebaseml_model_download_failed)
                        onError(errorMessage)
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorMessage = context.getString(R.string.firebaseml_model_download_failed)
                    onError(errorMessage)
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                val errorMessage = "Model download failed: ${e.message}"
                onError(errorMessage)
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
    }


    private fun initializeInterpreter(modelFile: File) {
        interpreter?.close() // Tutup interpreter sebelumnya jika ada
        try {
            val options = InterpreterApi.Options()
            if (TfLiteGpu.isGpuDelegateAvailable(context).result) {
                options.addDelegateFactory(GpuDelegateFactory())
            }
            options.setRuntime(InterpreterApi.Options.TfLiteRuntime.FROM_SYSTEM_ONLY)

            interpreter = InterpreterApi.create(modelFile, options)
        } catch (e: Exception) {
            onError("Error initializing interpreter: ${e.message}")
            Toast.makeText(context, "Error initializing interpreter: ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e(TAG, e.message.toString())
        }
    }


    fun predict(image: Bitmap, callback: (String) -> Unit) {
        if (interpreter == null) {
            onError(context.getString(R.string.no_tflite_interpreter_loaded))
            return
        }
        try {
            val inputArray = preprocessImage(image)
            val outputArray = Array(1) { FloatArray(1) }
            interpreter?.run(inputArray, outputArray)
            val result = if (outputArray[0][0] > 0.5) "Healthy" else "Unhealthy"
            callback(result)
        } catch (e: Exception) {
            onError(context.getString(R.string.error_during_prediction))
        }
    }


    private fun preprocessImage(image: Bitmap): Array<Array<Array<FloatArray>>> {
        // Ubah ukuran gambar menjadi dimensi input model (misalnya, 224x224)
        val resizedImage = Bitmap.createScaledBitmap(image, 224, 224, true)

        // Normalisasi piksel gambar (0-255 menjadi 0-1)
        val inputArray = Array(1) {
            Array(224) { Array(224) { FloatArray(3) } }
        }
        for (x in 0 until 224) {
            for (y in 0 until 224) {
                val pixel = resizedImage.getPixel(x, y)
                inputArray[0][x][y][0] = (Color.red(pixel) / 255.0f)
                inputArray[0][x][y][1] = (Color.green(pixel) / 255.0f)
                inputArray[0][x][y][2] = (Color.blue(pixel) / 255.0f)
            }
        }
        return inputArray
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
