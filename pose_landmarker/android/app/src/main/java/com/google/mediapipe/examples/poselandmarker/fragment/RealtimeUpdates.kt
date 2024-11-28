package com.google.mediapipe.examples.poselandmarker.fragment
import androidx.fragment.app.Fragment
import com.google.mediapipe.examples.poselandmarker.R
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.mediapipe.examples.poselandmarker.DataBuffer
import com.google.mediapipe.examples.poselandmarker.SharedViewModel
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.LegendRenderer
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*



class RealtimeUpdates : Fragment() {




    private val mHandler = Handler()
    private lateinit var mTimer: Runnable

    // Hand Series
    private lateinit var mSeries_Right_Hand_y: LineGraphSeries<DataPoint>
    private lateinit var mSeries_Right_Hand_x: LineGraphSeries<DataPoint>
    private lateinit var mSeries_Right_Hand_z: LineGraphSeries<DataPoint>

    private lateinit var mSeries_Left_Hand_x: LineGraphSeries<DataPoint>
    private lateinit var mSeries_Left_Hand_y: LineGraphSeries<DataPoint>
    private lateinit var mSeries_Left_Hand_z: LineGraphSeries<DataPoint>


    // Elbow Series
    private lateinit var mSeries_Right_Elbow_x: LineGraphSeries<DataPoint>
    private lateinit var mSeries_Right_Elbow_y: LineGraphSeries<DataPoint>
    private lateinit var mSeries_Right_Elbow_z: LineGraphSeries<DataPoint>

    private lateinit var mSeries_Left_Elbow_x: LineGraphSeries<DataPoint>
    private lateinit var mSeries_Left_Elbow_y: LineGraphSeries<DataPoint>
    private lateinit var mSeries_Left_Elbow_z: LineGraphSeries<DataPoint>


    // Shoulder Series
    private lateinit var mSeries_Right_Shoulder_x: LineGraphSeries<DataPoint>
    private lateinit var mSeries_Right_Shoulder_y: LineGraphSeries<DataPoint>
    private lateinit var mSeries_Right_Shoulder_z: LineGraphSeries<DataPoint>

    private lateinit var mSeries_Left_Shoulder_x: LineGraphSeries<DataPoint>
    private lateinit var mSeries_Left_Shoulder_y: LineGraphSeries<DataPoint>
    private lateinit var mSeries_Left_Shoulder_z: LineGraphSeries<DataPoint>

    //INITIAL
    /*
    private var graphLastTimeValue = 0.0
    private var graphLastYValue = 0.0
    */
    private var graphLastTimeValue :Double? = null
    private var graphLastYValue :Double? = null



    //INITIAL
/*
    // Hand
    var xValueRightHand = 0.0
    var yValueRightHand = 0.0
    var zValueRightHand = 0.0

    var xValueLeftHand = 0.0
    var yValueLeftHand = 0.0
    var zValueLeftHand = 0.0


    //Shoulder
    var xValueRightShoulder = 0.0
    var yValueRightShoulder = 0.0
    var zValueRightShoulder = 0.0

    var xValueLeftShoulder = 0.0
    var yValueLeftShoulder = 0.0
    var zValueLeftShoulder = 0.0

    //Elbow
    var xValueRightElbow = 0.0
    var yValueRightElbow  = 0.0
    var zValueRightElbow  = 0.0

    var xValueLeftElbow = 0.0
    var yValueLeftElbow = 0.0
    var zValueLeftElbow = 0.0
*/
    //--------------test-----------------
    // Hand
    var xValueRightHand:Double?= null
    var yValueRightHand:Double? = null
    var zValueRightHand :Double? = null

    var xValueLeftHand:Double?= null
    var yValueLeftHand :Double?= null
    var zValueLeftHand :Double?= null


    //Shoulder
    var xValueRightShoulder :Double?= null
    var yValueRightShoulder :Double?= null
    var zValueRightShoulder :Double?= null

    var xValueLeftShoulder :Double?= null
    var yValueLeftShoulder :Double?= null
    var zValueLeftShoulder :Double?= null

    //Elbow
    var xValueRightElbow :Double?= null
    var yValueRightElbow :Double?= null
    var zValueRightElbow  :Double?= null

    var xValueLeftElbow :Double?= null
    var yValueLeftElbow :Double?= null
    var zValueLeftElbow :Double?= null

    //-----------test-----------------------------

    //private val sharedViewModel: SharedViewModel by viewModels({ requireActivity() })

    /*private val sharedViewModel: SharedViewModel by lazy {
        viewModels<SharedViewModel> { requireActivity() }.value
    }*/

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var startTime : Long? = null

    private val graphViewModel: GraphViewModel by activityViewModels()

    var textToWrite = ""

    //---------------------------Buffer--------------------------------------------------
    val bufferSize = 32 * 1024
    val buffer1 = ByteArrayOutputStream()
    val buffer2 = ByteArrayOutputStream()
    val buffer3 = ByteArrayOutputStream()
    val buffer4 = ByteArrayOutputStream()
    val buffer5 = ByteArrayOutputStream()
    val buffer6 = ByteArrayOutputStream()
    //---------------------------Buffer--------------------------------------------------


    //---------------------------colors--------------------------------------------------
    private val colors = listOf(
        Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.BLACK,
        Color.GRAY, Color.DKGRAY, Color.LTGRAY, Color.WHITE,
        Color.rgb(255, 165, 0), // Orange
        Color.rgb(75, 0, 130), // Indigo
        Color.rgb(255, 20, 147), // DeepPink
        Color.rgb(139, 0, 139), // DarkMagenta
        Color.rgb(0, 100, 0), // DarkGreen
        Color.rgb(72, 61, 139), // DarkSlateBlue
        Color.rgb(0, 255, 127) // SpringGreen
    )



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_realtime_updates, container, false)



        val graph = rootView.findViewById<GraphView>(R.id.graph)

        //init of number of active curves
        graphViewModel.initNumberOfVisibleCurve()

        mSeries_Right_Hand_y = LineGraphSeries()
        mSeries_Right_Hand_x = LineGraphSeries()
        mSeries_Right_Hand_z = LineGraphSeries()
        mSeries_Left_Hand_x = LineGraphSeries()
        mSeries_Left_Hand_y = LineGraphSeries()
        mSeries_Left_Hand_z = LineGraphSeries()

        mSeries_Right_Elbow_x = LineGraphSeries()
        mSeries_Right_Elbow_y = LineGraphSeries()
        mSeries_Right_Elbow_z = LineGraphSeries()
        mSeries_Left_Elbow_x = LineGraphSeries()
        mSeries_Left_Elbow_y = LineGraphSeries()
        mSeries_Left_Elbow_z = LineGraphSeries()


        mSeries_Right_Shoulder_x = LineGraphSeries()
        mSeries_Right_Shoulder_y = LineGraphSeries()
        mSeries_Right_Shoulder_z = LineGraphSeries()
        mSeries_Left_Shoulder_x = LineGraphSeries()
        mSeries_Left_Shoulder_y  = LineGraphSeries()
        mSeries_Left_Shoulder_z  = LineGraphSeries()



        //graph.addSeries(mSeries_Right_Hand_y)
        //graph.addSeries(mSeries_Right_Hand_x)

        mSeries_Right_Hand_x.color = colors[0]
        mSeries_Right_Hand_y.color = colors[1]
        mSeries_Right_Hand_z.color = colors[2]
        mSeries_Left_Hand_x.color = colors[3]
        mSeries_Left_Hand_y.color = colors[4]
        mSeries_Left_Hand_z.color = colors[5]

        mSeries_Right_Elbow_x.color = colors[6]
        mSeries_Right_Elbow_y.color = colors[7]
        mSeries_Right_Elbow_z.color = colors[8]
        mSeries_Left_Elbow_x.color = colors[9]
        mSeries_Left_Elbow_y.color = colors[10]
        mSeries_Left_Hand_z.color = colors[11]

        mSeries_Right_Shoulder_x.color = colors[12]
        mSeries_Right_Shoulder_y.color = colors[13]
        mSeries_Right_Shoulder_z.color = colors[14]
        mSeries_Left_Shoulder_x.color = colors[15]
        mSeries_Left_Shoulder_y.color = colors[16]
        mSeries_Left_Shoulder_z.color = colors[17]



        // Adding legends
        /*
        mSeries_Right_Hand_y.title = "right hand y"
        mSeries_Right_Hand_x.title = "right hand x"
        mSeries_Right_Hand_z.title = "right hand z"
        mSeries_Left_Hand_x.title = "left hand x"
        mSeries_Left_Hand_y.title = "left hand y"
        mSeries_Left_Hand_z.title = "left hand z"

        mSeries_Right_Elbow_y.title = "right elbow y"
        mSeries_Right_Elbow_x.title = "right elbow x"
        mSeries_Right_Elbow_z.title = "right elbow z"
        mSeries_Left_Elbow_x.title = "left elbow x"
        mSeries_Left_Elbow_y.title = "left elbow y"
        mSeries_Left_Elbow_z.title = "left elbow z"


        mSeries_Right_Shoulder_y.title = "right shoulder y"
        mSeries_Right_Shoulder_x.title = "right shoulder x"
        mSeries_Right_Shoulder_z.title = "right shoulder z"
        mSeries_Left_Shoulder_x.title = "left shoulder x"
        mSeries_Left_Shoulder_y.title = "left shoulder y"
        mSeries_Left_Shoulder_z.title = "left shoulder z"
        */

        // Activate graph's legend
        graph.legendRenderer.isVisible = true
        graph.legendRenderer.align = LegendRenderer.LegendAlign.TOP

        graph.viewport.isXAxisBoundsManual = true
        graph.viewport.setMinX(0.0)
        graph.viewport.setMaxX(2000.0)

        sharedViewModel.setRealtimeUpdateActive(true)


        Log.e("RU created", "RU has been created")

        //Button Parameters configuration
        val buttonParameters: Button = rootView.findViewById(R.id.button_parameters)
        //val buttonParameters: Button = requireView().findViewById(R.id.button_parameters)
        buttonParameters.setOnClickListener {
            val fragmentParameters = GraphParameters()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.add(R.id.fragment_container, fragmentParameters)
            transaction.addToBackStack("GraphParameters")
            transaction.commit()
        }


        //----------------------------------Hand-----------------------------------------------------
        graphViewModel.isCurveRightHandXVisible.observe(viewLifecycleOwner, { isChecked ->
            curveDisplaying(graph,isChecked,mSeries_Right_Hand_x, "right hand x")
        })

        graphViewModel.isCurveRightHandYVisible.observe(viewLifecycleOwner, { isChecked->
            curveDisplaying(graph,isChecked,mSeries_Right_Hand_y, "right hand y")
        })

        graphViewModel.isCurveRightHandZVisible.observe(viewLifecycleOwner, { isChecked ->
            curveDisplaying(graph,isChecked, mSeries_Right_Hand_z, "right hand z")
        })

        graphViewModel.isCurveLeftHandXVisible.observe(viewLifecycleOwner, { isChecked ->
            curveDisplaying(graph,isChecked,mSeries_Left_Hand_x,"left hand x")
        })

        graphViewModel.isCurveLeftHandYVisible.observe(viewLifecycleOwner, { isChecked->
            curveDisplaying(graph,isChecked,mSeries_Left_Hand_y, "left hand y")
        })

        graphViewModel.isCurveLeftHandZVisible.observe(viewLifecycleOwner, { isChecked ->
            curveDisplaying(graph,isChecked, mSeries_Left_Hand_z, "left hand z")
        })


        //--------------------------------Elbow--------------------------------------------------------
        graphViewModel.isCurveRightElbowXVisible.observe(viewLifecycleOwner, { isChecked ->
            curveDisplaying(graph,isChecked,mSeries_Right_Elbow_x, "right elbow x")
        })

        graphViewModel.isCurveRightElbowYVisible.observe(viewLifecycleOwner, { isChecked->
            curveDisplaying(graph,isChecked,mSeries_Right_Elbow_y, "right elbow y")
        })

        graphViewModel.isCurveRightElbowZVisible.observe(viewLifecycleOwner, { isChecked ->
            curveDisplaying(graph,isChecked, mSeries_Right_Elbow_z, "right elbow z")
        })

        graphViewModel.isCurveLeftElbowXVisible.observe(viewLifecycleOwner, { isChecked ->
            curveDisplaying(graph,isChecked,mSeries_Left_Elbow_x, "left shoulder x")
        })

        graphViewModel.isCurveLeftElbowYVisible.observe(viewLifecycleOwner, { isChecked->
            curveDisplaying(graph,isChecked,mSeries_Left_Elbow_y, "left shoulder y")
        })

        graphViewModel.isCurveLeftElbowZVisible.observe(viewLifecycleOwner, { isChecked ->
            curveDisplaying(graph,isChecked, mSeries_Left_Elbow_z, "left shoulder z")
        })


        //--------------------------------Shoulder--------------------------------------------------------
        graphViewModel.isCurveRightShoulderXVisible.observe(viewLifecycleOwner, { isChecked ->
            curveDisplaying(graph,isChecked,mSeries_Right_Shoulder_x, "right shoulder x")
        })

        graphViewModel.isCurveRightShoulderYVisible.observe(viewLifecycleOwner, { isChecked->
            curveDisplaying(graph,isChecked,mSeries_Right_Shoulder_y, "right shoulder y")
        })

        graphViewModel.isCurveRightShoulderZVisible.observe(viewLifecycleOwner, { isChecked ->
            curveDisplaying(graph,isChecked, mSeries_Right_Shoulder_z, "right shoulder z")
        })

        graphViewModel.isCurveLeftShoulderXVisible.observe(viewLifecycleOwner, { isChecked ->
            curveDisplaying(graph,isChecked,mSeries_Left_Shoulder_x, "left shoulder x")
        })

        graphViewModel.isCurveLeftShoulderYVisible.observe(viewLifecycleOwner, { isChecked->
            curveDisplaying(graph,isChecked,mSeries_Left_Shoulder_y, "left shoulder y")
        })

        graphViewModel.isCurveLeftShoulderZVisible.observe(viewLifecycleOwner, { isChecked ->
            curveDisplaying(graph,isChecked, mSeries_Left_Shoulder_z, "left shoulder z")
        })


        return rootView
    }








    override fun onResume() {
        super.onResume()
        /*
        mTimer = Runnable {
            graphLastYValue = setgraph2LastYValue()
            val currentTime = sharedViewModel.currentTimestamp-

            Log.e("graphLastYValue","graphLastYValue = $graphLastYValue")
            Log.e("currentTimeStamp","currentTimeStamp = $currentTime")
            if (currentTime != null && graphLastYValue != 0.0) {
                graphLastTimeValue = currentTime.toDouble()
                mSeries_Right_Hand_y.appendData(DataPoint(graphLastTimeValue, graphLastYValue), true, 40)
            }
            mHandler.postDelayed(mTimer, 200)
        }
        mHandler.postDelayed(mTimer, 1000)
         */

        if (startTime == null) {
            startTime = sharedViewModel.getRealTimeUpdateStartTime()
            Log.e("RU startTime in RealtimeUpdates", "startTime = $startTime")

        }


        /*
        graphViewModel.numberOfVisibleCurve.observe(viewLifecycleOwner,{ numberOfVisibleCurve ->
            if (graphViewModel.previousNumberOfVisibleCurve == 0 && numberOfVisibleCurve == 1) {
                //startTime = sharedViewModel.getRealTimeUpdateStartTime()
                //println("La valeur est passée de 0 à une valeur non nulle : $numberOfVisibleCurve")
                Log.e("startTime in RealtimeUpdates observe", "startTime observe = $startTime")

            }
            graphViewModel.previousNumberOfVisibleCurve = numberOfVisibleCurve
        } )*/



        sharedViewModel.currentTimestamp.observe(viewLifecycleOwner, { currentTimestamp ->
            if (currentTimestamp != null && startTime != null) {

                //Right hand
                xValueRightHand = sharedViewModel.getSharedLandmarks()[16].x().toDouble()
                yValueRightHand = 1 - sharedViewModel.getSharedLandmarks()[16].y().toDouble()
                zValueRightHand = 1 - sharedViewModel.getSharedLandmarks()[16].z().toDouble()

                //Left hand
                xValueLeftHand = sharedViewModel.getSharedLandmarks()[15].x().toDouble()
                yValueLeftHand = 1 - sharedViewModel.getSharedLandmarks()[15].y().toDouble()
                zValueLeftHand = 1 - sharedViewModel.getSharedLandmarks()[15].z().toDouble()

                //Right shoulder
                xValueRightShoulder = sharedViewModel.getSharedLandmarks()[12].x().toDouble()
                yValueRightShoulder = 1 - sharedViewModel.getSharedLandmarks()[12].y().toDouble()
                zValueRightShoulder = 1 - sharedViewModel.getSharedLandmarks()[12].z().toDouble()

                //Left shoulder
                xValueLeftShoulder = sharedViewModel.getSharedLandmarks()[11].x().toDouble()
                yValueLeftShoulder = 1 - sharedViewModel.getSharedLandmarks()[11].y().toDouble()
                zValueLeftShoulder = 1 - sharedViewModel.getSharedLandmarks()[11].z().toDouble()

                //Right Elbow
                xValueRightElbow = sharedViewModel.getSharedLandmarks()[14].x().toDouble()
                yValueRightElbow = 1 - sharedViewModel.getSharedLandmarks()[14].y().toDouble()
                zValueRightElbow = 1 - sharedViewModel.getSharedLandmarks()[14].z().toDouble()

                //Left Elbow
                xValueLeftElbow = sharedViewModel.getSharedLandmarks()[13].x().toDouble()
                yValueLeftElbow = 1 - sharedViewModel.getSharedLandmarks()[13].y().toDouble()
                zValueLeftElbow = 1 - sharedViewModel.getSharedLandmarks()[13].z().toDouble()

                val rightHand = mapOf("x" to xValueRightHand, "y" to yValueRightHand, "z" to zValueRightHand)
                val leftHand = mapOf("x" to xValueLeftHand, "y" to yValueLeftHand, "z" to zValueLeftHand)

                val rightElbow = mapOf("x" to xValueRightElbow, "y" to yValueRightElbow, "z" to zValueRightElbow)
                val leftElbow = mapOf("x" to xValueLeftElbow, "y" to yValueLeftElbow, "z" to zValueLeftElbow)

                val rightShoulder = mapOf("x" to xValueRightShoulder, "y" to yValueRightShoulder, "z" to zValueRightShoulder)
                val leftShoulder = mapOf("x" to xValueLeftShoulder, "y" to yValueLeftShoulder, "z" to zValueLeftShoulder)

                val landmarkMap = mapOf("RightHand" to rightHand,"LeftHand" to leftHand, "LeftElbow" to leftElbow, "RightElbow" to rightElbow, "RightShoulder" to rightShoulder, "LeftShoulder" to leftShoulder)

                val currentTime = currentTimestamp - startTime!!
                Log.e("RU CurrentTime", "CurrentTime = $currentTime")

                graphLastYValue = sharedViewModel.getGraphLastYValue().toDouble()
                Log.e("RU graphLastYValue", "graphLastYValue = $graphLastYValue")

                Log.e("RU currentTimeStamp", "currentTimeStamp = $currentTime")
                /*if (graphLastYValue != 0.0) {
                    graphLastTimeValue = currentTime.toDouble()
                    mSeries_Right_Hand_y.appendData(DataPoint(graphLastTimeValue, graphLastYValue), true, 40)
                    mSeries_Right_Hand_x.appendData(DataPoint(graphLastTimeValue, graphLastYValue1), true, 40)
                }*/

                graphLastTimeValue = currentTime.toDouble()
            //-------------------------------------Hand-------------------------------------------------------------------------------
                mSeries_Right_Hand_x.appendData(DataPoint(graphLastTimeValue!!, xValueRightHand!!), true, 40)
                mSeries_Right_Hand_y.appendData(DataPoint(graphLastTimeValue!!, yValueRightHand!!), true, 40)
                mSeries_Right_Hand_z.appendData(DataPoint(graphLastTimeValue!!, zValueRightHand!!), true, 40)

                mSeries_Left_Hand_x.appendData(DataPoint(graphLastTimeValue!!, xValueLeftHand!!), true, 40)
                mSeries_Left_Hand_y.appendData(DataPoint(graphLastTimeValue!!, yValueLeftHand!!), true, 40)
                mSeries_Left_Hand_z.appendData(DataPoint(graphLastTimeValue!!, zValueLeftHand!!), true, 40)

            //----------------------------------------Elbow-------------------------------------------------------------------------------
                mSeries_Right_Elbow_x.appendData(DataPoint(graphLastTimeValue!!, xValueRightElbow!!), true, 40)
                mSeries_Right_Elbow_y.appendData(DataPoint(graphLastTimeValue!!, yValueRightElbow!!), true, 40)
                mSeries_Right_Elbow_z.appendData(DataPoint(graphLastTimeValue!!, zValueRightElbow!!), true, 40)

                mSeries_Left_Elbow_x.appendData(DataPoint(graphLastTimeValue!!, xValueLeftElbow!!), true, 40)
                mSeries_Left_Elbow_y.appendData(DataPoint(graphLastTimeValue!!, yValueLeftElbow!!), true, 40)
                mSeries_Left_Elbow_z.appendData(DataPoint(graphLastTimeValue!!, zValueLeftElbow!!), true, 40)

            //---------------------------------------Shoulder------------------------------------------------------------------------------
                mSeries_Right_Shoulder_x.appendData(DataPoint(graphLastTimeValue!!, xValueRightShoulder!!), true, 40)
                mSeries_Right_Shoulder_y.appendData(DataPoint(graphLastTimeValue!!, yValueRightShoulder!!), true, 40)
                mSeries_Right_Shoulder_z.appendData(DataPoint(graphLastTimeValue!!, zValueRightShoulder!!), true, 40)

                mSeries_Left_Shoulder_x.appendData(DataPoint(graphLastTimeValue!!, xValueLeftShoulder!!), true, 40)
                mSeries_Left_Shoulder_y.appendData(DataPoint(graphLastTimeValue!!, yValueLeftShoulder!!), true, 40)
                mSeries_Left_Shoulder_z.appendData(DataPoint(graphLastTimeValue!!, zValueLeftShoulder!!), true, 40)



                //writing in files
                if(graphViewModel.fileUris.value!!.size !=0 /*&& graphViewModel.fileUris.value!!.keys.any{it in setOf("RightHand","LeftHand", "LeftElbow", "RightElbow", "RightShoulder", "LeftShoulder")}*/){ // check if the map supposed to contain Uris contains Uris of created files by checking the keys
                    /*
                    val filteredFileUris = getMatchingKeys(graphViewModel.fileUris.value!!, listOf("RightHand","LeftHand", "LeftElbow", "RightElbow", "RightShoulder", "LeftShoulder") )
                    for(fileName in filteredFileUris){
                        //Creation of a buffer for each file
                        graphViewModel.listDataBuffers.value!!.add( DataBuffer(bufferSize = 32 * 1024, contentResolver = requireActivity().contentResolver, fileName = fileName, uri = graphViewModel.getFileUri(fileName)!!  ) )
                    }
                    */
                    val listOfFilteredMapKeys = getMatchingSubstrings(graphViewModel.fileUris.value!!, landmarkMap.keys.toList() /*listOf("RightHand","LeftHand", "LeftElbow", "RightElbow", "RightShoulder", "LeftShoulder")*/ )
                    for(landmarkName in listOfFilteredMapKeys ){
                        textToWrite = "\n $graphLastTimeValue"
                        val mapOfCurvesVisibility = graphViewModel.curvesVisibilityMap[landmarkName]
                        if( mapOfCurvesVisibility != null){
                            val keysOfCurvesVisibility = mapOfCurvesVisibility!!.keys
                            for(dimension in keysOfCurvesVisibility){
                                if(mapOfCurvesVisibility[dimension]!!.value == true){
                                    textToWrite+= "\t ${landmarkMap[landmarkName]?.get(dimension)}"
                                    Log.e("???", "???")
                                }
                            }
                            if(textToWrite != "\n $graphLastTimeValue"){
                                val buffer = graphViewModel.mapDataBuffers.value!![getMatchingKey(graphViewModel.mapDataBuffers.value!!, landmarkName)]
                                if (buffer != null) {
                                    Log.e("RU Buffer", "write in buffer ")
                                    buffer.write(textToWrite)
                                }
                        }
                        else  {
                                Toast.makeText(context, "Writing issue : null ", Toast.LENGTH_SHORT).show()
                            }

                        }
                    }


                    /*
                    write(buffer1, "\n $graphLastTimeValue \t $xValueRightHand \t $yValueRightHand \t $zValueRightHand","RightHand.txt" )
                    write(buffer2, "\n $graphLastTimeValue \t $xValueLeftHand \t $yValueLeftHand \t $zValueLeftHand","LeftHand.txt" )
                    write(buffer3,"\n $graphLastTimeValue \t $xValueLeftElbow \t $yValueLeftElbow \t $zValueLeftElbow","LeftElbow.txt" )
                    write(buffer4,"\n $graphLastTimeValue \t $xValueRightElbow \t $yValueRightElbow \t $zValueRightElbow","RightElbow.txt" )
                    write(buffer5,"\n $graphLastTimeValue \t $xValueRightShoulder \t $yValueRightShoulder \t $zValueRightShoulder","RightShoulder.txt" )
                    write(buffer6,"\n $graphLastTimeValue \t $xValueLeftShoulder \t $yValueLeftShoulder \t $zValueLeftShoulder","LeftShoulder.txt" )
                    */
                }

                /*
                for ((fileName, uri) in graphViewModel.fileUris.value!!){
                    val currentfileName = fileName
                    if (currentfileName == "RightHand.txt") {
                        writeToFile(currentfileName, "\n $graphLastTimeValue ")
                        if(graphViewModel.isCurveRightHandXVisible.value==true){
                            writeToFile(currentfileName, "\t $xValueRightHand ")
                            //writeToFile(currentfileName, "\n $graphLastTimeValue \t $xValueRightHand \t $yValueRightHand \t $zValueRightHand")
                            Log.e("Write xR", "Writing x")
                        }

                        if(graphViewModel.isCurveRightHandYVisible.value==true){
                            writeToFile(currentfileName, "\t $yValueRightHand ")
                            Log.e("Write yR", "Writing y")
                        }

                        if(graphViewModel.isCurveRightHandZVisible.value==true){
                            writeToFile(currentfileName, "\t $zValueRightHand ")
                            Log.e("Write zR", "Writing z")
                        }
                    }

                    if (currentfileName == "LeftHand.txt") {
                        writeToFile(currentfileName, "\n $graphLastTimeValue \t $xValueLeftHand \t $yValueLeftHand \t $zValueLeftHand")
                        Log.e("Write all left", "Writing left")
                    }

                    //writeToFile(currentfileName, "\n $graphLastTimeValue \t $xValueRightHand \t $yValueRightHand \t $zValueRightHand")
                }

                //writeToFile("\n $graphLastTimeValue \t $xValueRightHand \t $yValueRightHand \t $zValueRightHand")
                */


            }
        })

        mTimer = Runnable {
            //Right hand
            xValueRightHand = sharedViewModel.getSharedLandmarks()[16].x().toDouble()
            yValueRightHand = 1 - sharedViewModel.getSharedLandmarks()[16].y().toDouble()
            zValueRightHand = 1 - sharedViewModel.getSharedLandmarks()[16].z().toDouble()

            //Left hand
            xValueLeftHand = sharedViewModel.getSharedLandmarks()[15].x().toDouble()
            yValueLeftHand = 1 - sharedViewModel.getSharedLandmarks()[15].y().toDouble()
            zValueLeftHand = 1 - sharedViewModel.getSharedLandmarks()[15].z().toDouble()

            //Right shoulder
            xValueRightShoulder = sharedViewModel.getSharedLandmarks()[12].x().toDouble()
            yValueRightShoulder = 1 - sharedViewModel.getSharedLandmarks()[12].y().toDouble()
            zValueRightShoulder = 1 - sharedViewModel.getSharedLandmarks()[12].z().toDouble()

            //Left shoulder
            xValueLeftShoulder = sharedViewModel.getSharedLandmarks()[11].x().toDouble()
            yValueLeftShoulder = 1 - sharedViewModel.getSharedLandmarks()[11].y().toDouble()
            zValueLeftShoulder = 1 - sharedViewModel.getSharedLandmarks()[11].z().toDouble()

            //Right Elbow
            xValueRightElbow = sharedViewModel.getSharedLandmarks()[14].x().toDouble()
            yValueRightElbow = 1 - sharedViewModel.getSharedLandmarks()[14].y().toDouble()
            zValueRightElbow = 1 - sharedViewModel.getSharedLandmarks()[14].z().toDouble()

            //Left Elbow
            xValueLeftElbow = sharedViewModel.getSharedLandmarks()[13].x().toDouble()
            yValueLeftElbow = 1 - sharedViewModel.getSharedLandmarks()[13].y().toDouble()
            zValueLeftElbow = 1 - sharedViewModel.getSharedLandmarks()[13].z().toDouble()

            mHandler.postDelayed(mTimer, 200)
        }
        mHandler.postDelayed(mTimer, 10)


    }



    override fun onPause() {
        mHandler.removeCallbacks(mTimer)
        super.onPause()
        sharedViewModel.setRealTimeUpdateStartTime(null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sharedViewModel.setRealtimeUpdateActive(false)
        Log.e("RU RealtimeUpdates backPressed", "RealtimeUpdates suppressed")

    }


    fun curveDisplaying(graphVar: GraphView?, isCheckedVar : Boolean?, seriesVar : LineGraphSeries<DataPoint>, titleVar : String ) {
        // Update the visibility of the curve
        if (isCheckedVar == true) {
            // Show the curve
            if (graphVar != null) {
                graphVar.addSeries(seriesVar)
                seriesVar.title = titleVar
                //graphVar.legendRenderer?.resetStyles()
            }
        }

        else{
            // Hide the curve
            if (graphVar != null) {
                graphVar.removeSeries(seriesVar)
                seriesVar.title = ""
                //graphVar.legendRenderer?.resetStyles()
            }
        }
        graphVar?.legendRenderer?.resetStyles()
    }

/*
    private fun writeToFile(text: String, append: Boolean = true) {
        graphViewModel.fileUri.value.let { uri ->
            try {
                val mode = if (append) "wa" else "w"
                if (uri != null) {
                    requireContext().contentResolver.openOutputStream(uri, mode)?.use { outputStream ->
                        outputStream.write(text.toByteArray())
                    }
                }
                Toast.makeText(context, "Données écrites avec succès.", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("GraphParametersFragment", "Erreur lors de l'écriture dans le fichier: ${e.message}", e)
                Toast.makeText(context, "Erreur lors de l'écriture dans le fichier.", Toast.LENGTH_SHORT).show()
            }
        } ?: run {
            Toast.makeText(context, "Aucun fichier sélectionné pour l'écriture.", Toast.LENGTH_SHORT).show()
        }
    }
*/
    private fun writeToFile(fileName: String, text: String) {
        val uri = graphViewModel.getFileUri(fileName)
        uri?.let {
            requireActivity().contentResolver.openOutputStream(it,"wa")?.use { outputStream ->
                outputStream.write(text.toByteArray())
                Log.e("RU Write sekoooooo !!", "Writing sekooooooooo")
            }
        }
    }

//------------------------------------------------ Buffer part----------------------------------------------------------
    @Synchronized
    fun write(buffer: ByteArrayOutputStream,data: String, fileName : String) {
        try {
            buffer.write(data.toByteArray())
            if (buffer.size() >= bufferSize) {
                flushToFile(buffer, fileName)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Synchronized
    fun flushToFile(buffer: ByteArrayOutputStream, fileName : String) {
        try {
            val uri = graphViewModel.getFileUri(fileName)
            requireActivity().contentResolver.openOutputStream(uri!!, "wa")?.use { outputStream ->
                buffer.writeTo(outputStream)
            }
            buffer.reset()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Synchronized
    fun close(buffer: ByteArrayOutputStream, fileName : String) {
        flushToFile(buffer,fileName)
        try {
            buffer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
//------------------------------------------------ Buffer part----------------------------------------------------------

    /*
        companion object {
            private var displayFragment = false
            private var listener: RealTimeUpdatesListener? = null

            fun setDisplayFragment(value: Boolean) {
                displayFragment = value
                listener?.onValueChanged(value)
            }

            fun getDisplayFragment(): Boolean {
                return displayFragment
            }

            fun setListener(realTimeUpdatesListener: RealTimeUpdatesListener) {
                listener = realTimeUpdatesListener
            }
        }
    */





/*
    fun getCurrentTimeStamp(): Double? {
        var currentTimestamp: Double? = null
        if (results != null && results!!.landmarks() != null) {
            if (results!!.landmarks().isNotEmpty()) {
               currentTimestamp = results!!.timestampMs().toDouble()
            }
        }
        return currentTimestamp
    }
*/







/*
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        sharedViewModel.setRealtimeUpdateActive(true)
        Log.e("onCreateRU","onCreateRU")
    }


    override fun onDestroy() {
        super.onDestroy()
        sharedViewModel.setRealtimeUpdateActive(false)
    }
*/







    //NOT USEFUL
/*
    private fun generateData(): Array<DataPoint> {
        val count = 30
        return Array(count) { i ->
            val x = i.toDouble()
            val f = mRand.nextDouble() * 0.15 + 0.3
            val y = Math.sin(i * f + 2) + mRand.nextDouble() * 0.3
            DataPoint(x, y)
        }
    }

    //Not useful
    private var mLastRandom = 2.0
    private val mRand = Random()

    private fun getRandom(): Double {
        mLastRandom += mRand.nextDouble() * 0.5 - 0.25
        return mLastRandom
    }*/
    //-----------------------------------------------------------------------------

    fun getMatchingSubstrings(map: MutableMap<String, Uri>, substrings: List<String>): List<String> {
        return substrings.filter { substring ->
            map.keys.any { key -> key.contains(substring) }
        }
    }

    fun getMatchingKey(map: MutableMap<String, DataBuffer>, substring: String): String? {
        return map.keys.find { key ->
            key.contains(substring)
        }
    }
}