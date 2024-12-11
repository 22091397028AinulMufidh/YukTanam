package com.example.yuktanam.ui.plants

import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class CameraActivityTest {

    private lateinit var scenario: ActivityScenario<CameraActivity>
    private val mockImageCapture: ImageCapture = mock()
    private val mockCameraProvider: ProcessCameraProvider = mock()

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(CameraActivity::class.java)
    }

    @After
    fun teardown() {
        scenario.close()
    }

//    @Test
//    fun testSwitchCamera() {
//        scenario.onActivity { activity ->
//            val binding = ActivityCameraBinding.inflate(activity.layoutInflater)
//            activity.imageCapture = mockImageCapture
//
//            // Simulate button click
//            binding.switchCamera.performClick()
//
//            // Verify the camera selector changed
//            assert(
//                activity.cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA ||
//                        activity.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA
//            )
//        }
//    }
//
//
//    @Test
//    fun testStartCamera() {
//        scenario.onActivity { activity ->
//            activity.startCamera()
//
//            // Verify that the camera provider is bound
//            verify(mockCameraProvider).unbindAll()
//        }
//    }
}