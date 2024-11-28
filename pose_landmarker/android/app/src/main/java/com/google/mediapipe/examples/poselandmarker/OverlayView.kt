/*
 * Copyright 2023 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.mediapipe.examples.poselandmarker

import SocketClientTask
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.AsyncTask
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarker
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException
import java.io.OutputStream

private val coroutineScope = CoroutineScope(Dispatchers.IO)

class OverlayView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var results: PoseLandmarkerResult? = null
    private var pointPaint = Paint()
    private var linePaint = Paint()

    private var scaleFactor: Float = 1f
    private var imageWidth: Int = 1
    private var imageHeight: Int = 1

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    //ajout
    private lateinit var sharedViewModel: SharedViewModel

    init {
        initPaints()
    }

    fun setViewModel(viewModel: SharedViewModel) {
        this.sharedViewModel = viewModel
        this.sharedViewModel.setTimerFinished(false)
        this.sharedViewModel.setRealtimeUpdateActive(false)
        //observeViewModel()
    }


    fun clear() {
        results = null
        pointPaint.reset()
        linePaint.reset()
        invalidate()
        initPaints()
    }

    private fun initPaints() {
        linePaint.color =
            ContextCompat.getColor(context!!, R.color.mp_color_primary)
        linePaint.strokeWidth = LANDMARK_STROKE_WIDTH
        linePaint.style = Paint.Style.STROKE

        pointPaint.color = Color.YELLOW
        pointPaint.strokeWidth = LANDMARK_STROKE_WIDTH
        pointPaint.style = Paint.Style.FILL
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        results?.let { poseLandmarkerResult ->
            for(landmark in poseLandmarkerResult.landmarks()) {
                for(normalizedLandmark in landmark) {
                    canvas.drawPoint(
                        normalizedLandmark.x() * imageWidth * scaleFactor,
                        normalizedLandmark.y() * imageHeight * scaleFactor,
                        pointPaint
                    )
                }

                PoseLandmarker.POSE_LANDMARKS.forEach {
                    canvas.drawLine(
                        poseLandmarkerResult.landmarks().get(0).get(it!!.start()).x() * imageWidth * scaleFactor,
                        poseLandmarkerResult.landmarks().get(0).get(it.start()).y() * imageHeight * scaleFactor,
                        poseLandmarkerResult.landmarks().get(0).get(it.end()).x() * imageWidth * scaleFactor,
                        poseLandmarkerResult.landmarks().get(0).get(it.end()).y() * imageHeight * scaleFactor,
                        linePaint)
                }
            }
        }
    }

    fun setResults(
        poseLandmarkerResults: PoseLandmarkerResult,
        imageHeight: Int,
        imageWidth: Int,
        runningMode: RunningMode = RunningMode.IMAGE
    ) {
        results = poseLandmarkerResults

        this.imageHeight = imageHeight
        this.imageWidth = imageWidth

        scaleFactor = when (runningMode) {
            RunningMode.IMAGE,
            RunningMode.VIDEO -> {
                min(width * 1f / imageWidth, height * 1f / imageHeight)
            }
            RunningMode.LIVE_STREAM -> {
                // PreviewView is in FILL_START mode. So we need to scale up the
                // landmarks to match with the size that the captured images will be
                // displayed.
                max(width * 1f / imageWidth, height * 1f / imageHeight)
            }
        }
        invalidate()
    }


    var currentTimestamp: Long? = null
    //var timer : Long ? = null  //3s timer
    var timer = 0L
    var startTimestamp: Long? = null

    fun changeRightHandLandmarkText() {
        Log.e("OV Nouvel Appel ", "Nouvel Appel")
        var xValueRightHand = 0F
        var yValueRightHand = 0F
        var zValueRightHand = 0F

        var xValueLeftHand = 0F
        var yValueLeftHand = 0F
        var zValueLeftHand = 0F

        var xValueRightShoulder = 0F
        var yValueRightShoulder = 0F

        var xValueLeftShoulder = 0F
        var yValueLeftShoulder = 0F

        var angleRightSide = 0F
        var angleLeftSide = 0F

        var angleShoulderlineRightShoulderElbow= 0F
        var angleShoulderlineLeftShoulderElbow = 0F

        var angleBetweenRightShoulderElbow= 0F
        var angleBetweenLeftShoulderElbow = 0F

        var angleBetweenRightElbowHand= 0F
        var angleBetweenLeftElbowHand = 0F

        var angleBetweenHipRightKnee= 0F
        var angleBetweenHipLeftKnee= 0F

        var angleBetweenRightKneeAnkle= 0F
        var angleBetweenLeftKneeAnkle = 0F

        val distanceBetweenAnkles = 0F

        var poseRightHandLandmarkTextView = rootView.findViewById<TextView>(R.id.pose_rightHand_landmark)
        if (results != null && results!!.landmarks() != null && results!!.landmarks().isNotEmpty()) {// When landmarks measures are performed
            currentTimestamp = results!!.timestampMs()
            this.sharedViewModel.setCurrentTimestamp(currentTimestamp!!)
            //Log.e("OV inside 2nd if currentTimestamp", "currentTimestamp = $currentTimestamp")
            //Log.e("OV inside 2nd if Timer", "timer = $timer")
            xValueRightHand = results!!.landmarks()[0][16].x()
            yValueRightHand = 1 - results!!.landmarks()[0][16].y()
            Log.e("OV Valeur y", "y = $yValueRightHand")
            zValueRightHand = 1 - results!!.landmarks()[0][16].z()
            //Y Graph
            sharedViewModel.setGraphLastYValue(yValueRightHand)

            xValueLeftHand = results!!.landmarks()[0][15].x()
            yValueLeftHand = 1 - results!!.landmarks()[0][15].y()
            zValueLeftHand = 1 - results!!.landmarks()[0][15].z()

            xValueRightShoulder = results!!.landmarks()[0][12].x()
            yValueRightShoulder = 1 - results!!.landmarks()[0][12].y()
            xValueLeftShoulder = results!!.landmarks()[0][11].x()
            yValueLeftShoulder = 1 - results!!.landmarks()[0][11].y()

            //linking the landmarks lit from OverlayView to the SharedViewModel one
            sharedViewModel.setSharedLandmarks(results!!.landmarks()[0])

            // Vector from Right shoulders
            val vectorShoulders = floatArrayOf(
                results!!.landmarks()[0][12].x() - results!!.landmarks()[0][11].x(),
                results!!.landmarks()[0][12].y() - results!!.landmarks()[0][11].y(),
                0F
            )

            //opposite vector
            val oppositeVectorShoulders = floatArrayOf(
                -vectorShoulders[0],
                -vectorShoulders[1],
                0F
            )

            val vectorRightShoulderToElbow = floatArrayOf(
                results!!.landmarks()[0][14].x()- results!!.landmarks()[0][12].x(),
                results!!.landmarks()[0][14].y()- results!!.landmarks()[0][12].y(),
                0F
            )

            val vectorLeftShoulderToElbow =floatArrayOf(
                results!!.landmarks()[0][13].x()- results!!.landmarks()[0][11].x(),
                results!!.landmarks()[0][13].y()- results!!.landmarks()[0][11].y(),
                0F
            )

            //vector from right hip to left hip
            val vectorHips = floatArrayOf(
                results!!.landmarks()[0][23].x()- results!!.landmarks()[0][24].x(),
                results!!.landmarks()[0][23].y()- results!!.landmarks()[0][24].y(),
                0F
            )


            //vector from left hip to right hip
            val oppositeVectorHips = floatArrayOf(
                -vectorHips[0],
                -vectorHips[1],
                0F
            )

            val vectorRightHipToKnee = floatArrayOf(
                results!!.landmarks()[0][26].x()- results!!.landmarks()[0][24].x(),
                results!!.landmarks()[0][26].y()- results!!.landmarks()[0][24].y(),
                0F
            )

            val vectorLeftHipToKnee = floatArrayOf(
                results!!.landmarks()[0][25].x()- results!!.landmarks()[0][23].x(),
                results!!.landmarks()[0][25].y()- results!!.landmarks()[0][23].y(),
                0F
            )

            val vectorRightKneeToAnkle = floatArrayOf(
                results!!.landmarks()[0][28].x()- results!!.landmarks()[0][26].x(),
                results!!.landmarks()[0][28].y()- results!!.landmarks()[0][26].y(),
                0F
            )

            val vectorLeftKneeToAnkle = floatArrayOf(
                results!!.landmarks()[0][27].x()- results!!.landmarks()[0][25].x(),
                results!!.landmarks()[0][27].y()- results!!.landmarks()[0][25].y(),
                0F
            )

            val vectorBothAnkles = floatArrayOf(
                results!!.landmarks()[0][27].x()- results!!.landmarks()[0][28].x(),
                results!!.landmarks()[0][27].y()- results!!.landmarks()[0][28].y(),
                0F
            )

            val vectorRightElbowHand = floatArrayOf(
                results!!.landmarks()[0][16].x()- results!!.landmarks()[0][14].x(),
                results!!.landmarks()[0][16].y()- results!!.landmarks()[0][14].y(),
                0F
            )

            val vectorLeftElbowHand = floatArrayOf(
                results!!.landmarks()[0][15].x()- results!!.landmarks()[0][13].x(),
                results!!.landmarks()[0][15].y()- results!!.landmarks()[0][13].y(),
                0F
            )

            val distanceBetweenAnkles = vectorNorm(vectorBothAnkles)



            angleBetweenRightShoulderElbow = getSignedAngle(vectorShoulders, vectorLeftShoulderToElbow)
            angleBetweenLeftShoulderElbow = getSignedAngle(oppositeVectorShoulders, vectorRightShoulderToElbow)

            angleBetweenRightElbowHand = getSignedAngle(vectorRightShoulderToElbow, vectorRightElbowHand)
            angleBetweenLeftElbowHand = getSignedAngle(vectorLeftShoulderToElbow, vectorLeftElbowHand)

            angleBetweenHipRightKnee = getSignedAngle(vectorHips, vectorLeftHipToKnee)
            angleBetweenHipLeftKnee = getSignedAngle(oppositeVectorHips, vectorRightHipToKnee)

            angleBetweenRightKneeAnkle = getSignedAngle(vectorRightHipToKnee, vectorRightKneeToAnkle)
            angleBetweenLeftKneeAnkle = getSignedAngle(vectorLeftHipToKnee, vectorLeftKneeToAnkle)

        // A Décomenter si on souhaite afficher les valeurs des angles à l'écran
//            poseRightHandLandmarkTextView.text = String.format(
//                        "angleBetweenRightShoulderElbow = %.2f \n angleBetweenLeftShoulderElbow = %.2f \n" +
//                        " angleBetweenRightElbowHand = %.2f \n angleBetweenLeftElbowHand = %.2f " +
//                        " angleBetweenHipRightKnee = %.2f \n" +
//                        " angleBetweenHipLeftKnee = %.2f \n angleBetweenRightKneeAnkle = %.2f \n angleBetweenLeftKneeAnkle = %.2f \n distanceBetweenAnkles = %.2f", angleBetweenRightShoulderElbow, angleBetweenLeftShoulderElbow, angleBetweenRightElbowHand,angleBetweenLeftElbowHand, angleBetweenHipRightKnee, angleBetweenHipLeftKnee,angleBetweenRightKneeAnkle,angleBetweenLeftKneeAnkle, distanceBetweenAnkles
//            )


            val messages =  listOf( String.format(
                "angleBetweenRightShoulderElbow = %.2f \n angleBetweenLeftShoulderElbow = %.2f \n" +
                        " angleBetweenRightElbowHand = %.2f \n angleBetweenLeftElbowHand = %.2f " +
                        " angleBetweenHipRightKnee = %.2f \n" +
                        " angleBetweenHipLeftKnee = %.2f \n angleBetweenRightKneeAnkle = %.2f \n angleBetweenLeftKneeAnkle = %.2f \n distanceBetweenAnkles = %.2f", angleBetweenRightShoulderElbow, angleBetweenLeftShoulderElbow, angleBetweenRightElbowHand,angleBetweenLeftElbowHand, angleBetweenHipRightKnee, angleBetweenHipLeftKnee,angleBetweenRightKneeAnkle,angleBetweenLeftKneeAnkle, distanceBetweenAnkles
            ) )


            coroutineScope.launch {
                val client = SocketClientTask("192.168.249.103", 5000) // Adresse IP et port
                Log.d("MESSAGE ENVOYE", "Message envoyé: $messages")
                client.sendMessage(messages)
            }


        }

//        else { // When landmarks measures are NOT performed
//
//            // A Décomenter si on souhaite afficher les valeurs des angles à l'écran
//            poseRightHandLandmarkTextView.text = String.format(
//                "angleBetweenRightShoulderElbow = %.2f \n angleBetweenLeftShoulderElbow = %.2f \n" +
//                        " angleBetweenRightElbowHand = %.2f \n angleBetweenLeftElbowHand = %.2f " +
//                        " angleBetweenHipRightKnee = %.2f \n" +
//                        " angleBetweenHipLeftKnee = %.2f \n angleBetweenRightKneeAnkle = %.2f \n angleBetweenLeftKneeAnkle = %.2f \n distanceBetweenAnkles = %.2f", angleBetweenRightShoulderElbow, angleBetweenLeftShoulderElbow, angleBetweenRightElbowHand,angleBetweenLeftElbowHand, angleBetweenHipRightKnee, angleBetweenHipLeftKnee,angleBetweenRightKneeAnkle,angleBetweenLeftKneeAnkle, distanceBetweenAnkles
//            )
//            Log.e("OV NoResults", "Results or landmarks are null or empty.")
//
//        }


    }



    fun getSignedAngle(v1: FloatArray , v2: FloatArray): Float {
        val dot = scalarProduct(v1, v2)
        val normV1 = vectorNorm(v1)
        val normV2 = vectorNorm(v2)
        val angle = acos(dot / (normV1 * normV2))

        // Produit vectoriel pour déterminer le signe
        val cross = crossProduct(v1, v2)
        val sign = cross[2]/ abs(cross[2])

        return Math.toDegrees(angle.toDouble()).toFloat() * sign
    }

    fun vectorNorm(v: FloatArray): Float {
        return sqrt(v[0].pow(2) + v[1].pow(2) + v[2].pow(2))
    }

    fun scalarProduct(v1: FloatArray, v2: FloatArray): Float {
        return v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2 [2]
    }

    fun crossProduct(v1: FloatArray, v2: FloatArray): FloatArray {
        return floatArrayOf(
            v1[1] * v2[2] - v1[2] * v2[1],
            v1[2] * v2[0] - v1[0] * v2[2],
            v1[0] * v2[1] - v1[1] * v2[0]
        )
    }

    companion object {
        private const val LANDMARK_STROKE_WIDTH = 12F
    }


}