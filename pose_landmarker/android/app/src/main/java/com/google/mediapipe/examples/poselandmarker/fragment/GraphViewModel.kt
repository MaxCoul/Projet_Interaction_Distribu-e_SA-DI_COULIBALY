package com.google.mediapipe.examples.poselandmarker.fragment

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.mediapipe.examples.poselandmarker.DataBuffer

class GraphViewModel : ViewModel() {


    //-----------------------------------Hand--------------------------------------------------------
    private val _isCurveRightHandXVisible = MutableLiveData(true)
    val isCurveRightHandXVisible: LiveData<Boolean> get() = _isCurveRightHandXVisible

    private val _isCurveRightHandYVisible = MutableLiveData(true)
    val isCurveRightHandYVisible: LiveData<Boolean> get() = _isCurveRightHandYVisible

    private val _isCurveRightHandZVisible = MutableLiveData(true)
    val isCurveRightHandZVisible: LiveData<Boolean> get() = _isCurveRightHandZVisible

    private val _isCurveLeftHandXVisible = MutableLiveData(true)
    val isCurveLeftHandXVisible: LiveData<Boolean> get() = _isCurveLeftHandXVisible

    private val _isCurveLeftHandYVisible = MutableLiveData(true)
    val isCurveLeftHandYVisible: LiveData<Boolean> get() = _isCurveLeftHandYVisible

    private val _isCurveLeftHandZVisible = MutableLiveData(true)
    val isCurveLeftHandZVisible: LiveData<Boolean> get() = _isCurveLeftHandZVisible


    fun setCurveRightHandXVisible(visible: Boolean) {
        _isCurveRightHandXVisible.value = visible
        setNumberOfVisibleCurve(visible)
        Log.e("Button xRH value changed", "$visible")
    }

    fun setCurveRightHandYVisible(visible: Boolean) {
        _isCurveRightHandYVisible.value = visible
        //setNumberOfVisibleCurve()
        setNumberOfVisibleCurve(visible)
        Log.e("Button yRH value changed", "$visible")

    }

    fun setCurveRightHandZVisible(visible: Boolean) {
        _isCurveRightHandZVisible.value = visible
        //setNumberOfVisibleCurve()
        setNumberOfVisibleCurve(visible)
        Log.e("Button zRH value changed", "$visible")
    }

    fun setCurveLeftHandXVisible(visible: Boolean) {
        _isCurveLeftHandXVisible.value = visible
        //setNumberOfVisibleCurve()
        setNumberOfVisibleCurve(visible)
    }

    fun setCurveLeftHandYVisible(visible: Boolean) {
        _isCurveLeftHandYVisible.value = visible
        //setNumberOfVisibleCurve()
        setNumberOfVisibleCurve(visible)
    }

    fun setCurveLeftHandZVisible(visible: Boolean) {
        _isCurveLeftHandZVisible.value = visible
        //setNumberOfVisibleCurve()
        setNumberOfVisibleCurve(visible)
    }

    //*******speed*******
    private val _isCurveRightHandDXVisible = MutableLiveData(true)
    val isCurveRightHandDXVisible: LiveData<Boolean> get() = _isCurveRightHandDXVisible

    private val _isCurveRightHandDYVisible = MutableLiveData(true)
    val isCurveRightHandDYVisible: LiveData<Boolean> get() = _isCurveRightHandDYVisible

    private val _isCurveRightHandDZVisible = MutableLiveData(true)
    val isCurveRightHandDZVisible: LiveData<Boolean> get() = _isCurveRightHandDZVisible

    private val _isCurveLeftHandDXVisible = MutableLiveData(true)
    val isCurveLeftHandDXVisible: LiveData<Boolean> get() = _isCurveLeftHandDXVisible

    private val _isCurveLeftHandDYVisible = MutableLiveData(true)
    val isCurveLeftHandDYVisible: LiveData<Boolean> get() = _isCurveLeftHandDYVisible

    private val _isCurveLeftHandDZVisible = MutableLiveData(true)
    val isCurveLeftHandDZVisible: LiveData<Boolean> get() = _isCurveLeftHandDZVisible


    fun setCurveRightHandDXVisible(visible: Boolean) {
        _isCurveRightHandDXVisible.value = visible
        setNumberOfVisibleCurve(visible)
        Log.e("Button xRH value changed", "$visible")
    }

    fun setCurveRightHandDYVisible(visible: Boolean) {
        _isCurveRightHandDYVisible.value = visible
        setNumberOfVisibleCurve(visible)
        Log.e("Button xRH value changed", "$visible")
    }

    fun setCurveRightHandDZVisible(visible: Boolean) {
        _isCurveRightHandDZVisible.value = visible
        setNumberOfVisibleCurve(visible)
        Log.e("Button xRH value changed", "$visible")
    }

    fun setCurveLeftHandDXVisible(visible: Boolean) {
        _isCurveLeftHandDXVisible.value = visible
        setNumberOfVisibleCurve(visible)
        Log.e("Button xRH value changed", "$visible")
    }

    fun setCurveLeftHandDYVisible(visible: Boolean) {
        _isCurveLeftHandDYVisible.value = visible
        setNumberOfVisibleCurve(visible)
        Log.e("Button xRH value changed", "$visible")
    }

    fun setCurveLeftHandDZVisible(visible: Boolean) {
        _isCurveLeftHandDZVisible.value = visible
        setNumberOfVisibleCurve(visible)
        Log.e("Button xRH value changed", "$visible")
    }

    //*******speed*******



    //------------------------------------Elbow----------------------------------------------------------------
    private val _isCurveRightElbowXVisible = MutableLiveData(false)
    val isCurveRightElbowXVisible: LiveData<Boolean> get() = _isCurveRightElbowXVisible

    private val _isCurveRightElbowYVisible = MutableLiveData(false)
    val isCurveRightElbowYVisible: LiveData<Boolean> get() = _isCurveRightElbowYVisible

    private val _isCurveRightElbowZVisible = MutableLiveData(false)
    val isCurveRightElbowZVisible: LiveData<Boolean> get() = _isCurveRightElbowZVisible

    private val _isCurveLeftElbowXVisible = MutableLiveData(false)
    val isCurveLeftElbowXVisible: LiveData<Boolean> get() = _isCurveLeftElbowXVisible

    private val _isCurveLeftElbowYVisible = MutableLiveData(false)
    val isCurveLeftElbowYVisible: LiveData<Boolean> get() = _isCurveLeftElbowYVisible

    private val _isCurveLeftElbowZVisible = MutableLiveData(false)
    val isCurveLeftElbowZVisible: LiveData<Boolean> get() = _isCurveLeftElbowZVisible


    fun setCurveRightElbowXVisible(visible: Boolean) {
        _isCurveRightElbowXVisible.value = visible
        //setNumberOfVisibleCurve()
        setNumberOfVisibleCurve(visible)
    }

    fun setCurveRightElbowYVisible(visible: Boolean) {
        _isCurveRightElbowYVisible.value = visible
        //setNumberOfVisibleCurve()
        setNumberOfVisibleCurve(visible)
    }

    fun setCurveRightElbowZVisible(visible: Boolean) {
        _isCurveRightElbowZVisible.value = visible
        //setNumberOfVisibleCurve()
        setNumberOfVisibleCurve(visible)
    }

    fun setCurveLeftElbowXVisible(visible: Boolean) {
        _isCurveLeftElbowXVisible.value = visible
        //setNumberOfVisibleCurve()
        setNumberOfVisibleCurve(visible)
    }

    fun setCurveLeftElbowYVisible(visible: Boolean) {
        _isCurveLeftElbowYVisible.value = visible
        //setNumberOfVisibleCurve()
        setNumberOfVisibleCurve(visible)
    }

    fun setCurveLeftElbowZVisible(visible: Boolean) {
        _isCurveLeftElbowZVisible.value = visible
        //setNumberOfVisibleCurve()
        setNumberOfVisibleCurve(visible)
    }

    //*******speed*******
    private val _isCurveRightElbowDXVisible = MutableLiveData(true)
    val isCurveRightElbowDXVisible: LiveData<Boolean> get() = _isCurveRightElbowDXVisible

    private val _isCurveRightElbowDYVisible = MutableLiveData(true)
    val isCurveRightElbowDYVisible: LiveData<Boolean> get() = _isCurveRightElbowDYVisible

    private val _isCurveRightElbowDZVisible = MutableLiveData(true)
    val isCurveRightElbowDZVisible: LiveData<Boolean> get() = _isCurveRightElbowDZVisible

    private val _isCurveLeftElbowDXVisible = MutableLiveData(true)
    val isCurveLeftElbowDXVisible: LiveData<Boolean> get() = _isCurveLeftElbowDXVisible

    private val _isCurveLeftElbowDYVisible = MutableLiveData(true)
    val isCurveLeftElbowDYVisible: LiveData<Boolean> get() = _isCurveLeftElbowDYVisible

    private val _isCurveLeftElbowDZVisible = MutableLiveData(true)
    val isCurveLeftElbowDZVisible: LiveData<Boolean> get() = _isCurveLeftElbowDZVisible


    fun setCurveRightElbowDXVisible(visible: Boolean) {
        _isCurveRightElbowDXVisible.value = visible
        setNumberOfVisibleCurve(visible)
        Log.e("Button xRH value changed", "$visible")
    }

    fun setCurveRightElbowDYVisible(visible: Boolean) {
        _isCurveRightElbowDYVisible.value = visible
        setNumberOfVisibleCurve(visible)
        Log.e("Button xRH value changed", "$visible")
    }

    fun setCurveRightElbowDZVisible(visible: Boolean) {
        _isCurveRightElbowDZVisible.value = visible
        setNumberOfVisibleCurve(visible)
        Log.e("Button xRH value changed", "$visible")
    }

    fun setCurveLeftElbowDXVisible(visible: Boolean) {
        _isCurveLeftElbowDXVisible.value = visible
        setNumberOfVisibleCurve(visible)
        Log.e("Button xRH value changed", "$visible")
    }

    fun setCurveLeftElbowDYVisible(visible: Boolean) {
        _isCurveLeftElbowDYVisible.value = visible
        setNumberOfVisibleCurve(visible)
        Log.e("Button xRH value changed", "$visible")
    }

    fun setCurveLeftElbowDZVisible(visible: Boolean) {
        _isCurveLeftElbowDZVisible.value = visible
        setNumberOfVisibleCurve(visible)
        Log.e("Button xRH value changed", "$visible")
    }
    //*******speed*******



    //--------------------------------Shoulder--------------------------------------------------

    private val _isCurveRightShoulderXVisible = MutableLiveData(false)
    val isCurveRightShoulderXVisible: LiveData<Boolean> get() = _isCurveRightShoulderXVisible

    private val _isCurveRightShoulderYVisible = MutableLiveData(false)
    val isCurveRightShoulderYVisible: LiveData<Boolean> get() = _isCurveRightShoulderYVisible

    private val _isCurveRightShoulderZVisible = MutableLiveData(false)
    val isCurveRightShoulderZVisible: LiveData<Boolean> get() = _isCurveRightShoulderZVisible

    private val _isCurveLeftShoulderXVisible = MutableLiveData(false)
    val isCurveLeftShoulderXVisible: LiveData<Boolean> get() = _isCurveLeftShoulderXVisible

    private val _isCurveLeftShoulderYVisible = MutableLiveData(false)
    val isCurveLeftShoulderYVisible: LiveData<Boolean> get() = _isCurveLeftShoulderYVisible

    private val _isCurveLeftShoulderZVisible = MutableLiveData(false)
    val isCurveLeftShoulderZVisible: LiveData<Boolean> get() = _isCurveLeftShoulderZVisible


    fun setCurveRightShoulderXVisible(visible: Boolean) {
        _isCurveRightShoulderXVisible.value = visible
        setNumberOfVisibleCurve(visible)
    }

    fun setCurveRightShoulderYVisible(visible: Boolean) {
        _isCurveRightShoulderYVisible.value = visible
        //setNumberOfVisibleCurve()
        setNumberOfVisibleCurve(visible)
    }

    fun setCurveRightShoulderZVisible(visible: Boolean) {
        _isCurveRightShoulderZVisible.value = visible
        //setNumberOfVisibleCurve()
        setNumberOfVisibleCurve(visible)
    }

    fun setCurveLeftShoulderXVisible(visible: Boolean) {
        _isCurveLeftShoulderXVisible.value = visible
        //setNumberOfVisibleCurve()
        setNumberOfVisibleCurve(visible)
    }

    fun setCurveLeftShoulderYVisible(visible: Boolean) {
        _isCurveLeftShoulderYVisible.value = visible
        //setNumberOfVisibleCurve()
        setNumberOfVisibleCurve(visible)
    }

    fun setCurveLeftShoulderZVisible(visible: Boolean) {
        _isCurveLeftShoulderZVisible.value = visible
        //setNumberOfVisibleCurve()
        setNumberOfVisibleCurve(visible)
    }

    //*******speed*******
    private val _isCurveRightShoulderDXVisible = MutableLiveData(true)
    val isCurveRightShoulderDXVisible: LiveData<Boolean> get() = _isCurveRightShoulderDXVisible

    private val _isCurveRightShoulderDYVisible = MutableLiveData(true)
    val isCurveRightShoulderDYVisible: LiveData<Boolean> get() = _isCurveRightShoulderDYVisible

    private val _isCurveRightShoulderDZVisible = MutableLiveData(true)
    val isCurveRightShoulderDZVisible: LiveData<Boolean> get() = _isCurveRightShoulderDZVisible

    private val _isCurveLeftShoulderDXVisible = MutableLiveData(true)
    val isCurveLeftShoulderDXVisible: LiveData<Boolean> get() = _isCurveLeftShoulderDXVisible

    private val _isCurveLeftShoulderDYVisible = MutableLiveData(true)
    val isCurveLeftShoulderDYVisible: LiveData<Boolean> get() = _isCurveLeftShoulderDYVisible

    private val _isCurveLeftShoulderDZVisible = MutableLiveData(true)
    val isCurveLeftShoulderDZVisible: LiveData<Boolean> get() = _isCurveLeftShoulderDZVisible


    fun setCurveRightShoulderDXVisible(visible: Boolean) {
        _isCurveRightShoulderDXVisible.value = visible
        setNumberOfVisibleCurve(visible)
        Log.e("Button xRH value changed", "$visible")
    }

    fun setCurveRightShoulderDYVisible(visible: Boolean) {
        _isCurveRightShoulderDYVisible.value = visible
        setNumberOfVisibleCurve(visible)
        Log.e("Button xRH value changed", "$visible")
    }

    fun setCurveRightShoulderDZVisible(visible: Boolean) {
        _isCurveRightShoulderDZVisible.value = visible
        setNumberOfVisibleCurve(visible)
        Log.e("Button xRH value changed", "$visible")
    }

    fun setCurveLeftShoulderDXVisible(visible: Boolean) {
        _isCurveLeftShoulderDXVisible.value = visible
        setNumberOfVisibleCurve(visible)
        Log.e("Button xRH value changed", "$visible")
    }

    fun setCurveLeftShoulderDYVisible(visible: Boolean) {
        _isCurveLeftShoulderDYVisible.value = visible
        setNumberOfVisibleCurve(visible)
        Log.e("Button xRH value changed", "$visible")
    }

    fun setCurveLeftShoulderDZVisible(visible: Boolean) {
        _isCurveLeftShoulderDZVisible.value = visible
        setNumberOfVisibleCurve(visible)
        Log.e("Button xRH value changed", "$visible")
    }

    //*******speed*******




//____________________________________________curves related tools________________________________________________________________________________________

    //list of all booleans informing about the presence of curves on the graph
    val curvesVisibilityMap: Map <  String,  Map<  String,LiveData<Boolean>  >    > = mapOf(
        "RightHand" to mapOf("x" to isCurveRightHandXVisible, "y" to isCurveRightHandYVisible,"z" to  isCurveRightHandZVisible) ,
        "LeftHand" to mapOf("x" to isCurveLeftHandXVisible, "y" to isCurveLeftHandYVisible, "z" to isCurveLeftHandZVisible) ,
        "RightElbow" to mapOf("x" to isCurveRightElbowXVisible, "y" to isCurveRightElbowYVisible,  "z" to isCurveRightElbowZVisible),
        "LeftElbow" to mapOf("x" to isCurveLeftElbowXVisible, "y" to isCurveLeftElbowYVisible, "z" to isCurveLeftElbowZVisible),
        "RightShoulder" to mapOf("x" to isCurveRightShoulderXVisible,"y" to isCurveRightShoulderYVisible, "z" to isCurveRightShoulderZVisible),
        "RightShoulder" to mapOf("x" to isCurveLeftShoulderXVisible,"y" to isCurveLeftShoulderYVisible, "z" to isCurveLeftShoulderZVisible)
    )

    val curvesVisibilityList: List < Map<  String,LiveData<Boolean>  >    > = listOf(
        mapOf("x" to isCurveRightHandXVisible, "y" to isCurveRightHandYVisible,"z" to  isCurveRightHandZVisible) ,
        mapOf("x" to isCurveLeftHandXVisible, "y" to isCurveLeftHandYVisible, "z" to isCurveLeftHandZVisible) ,
        mapOf("x" to isCurveRightElbowXVisible, "y" to isCurveRightElbowYVisible,  "z" to isCurveRightElbowZVisible),
        mapOf("x" to isCurveLeftElbowXVisible, "y" to isCurveLeftElbowYVisible, "z" to isCurveLeftElbowZVisible),
        mapOf("x" to isCurveRightShoulderXVisible,"y" to isCurveRightShoulderYVisible, "z" to isCurveRightShoulderZVisible),
        mapOf("x" to isCurveLeftShoulderXVisible,"y" to isCurveLeftShoulderYVisible, "z" to isCurveLeftShoulderZVisible)
    )

    //keep the number of active curves in before a change
    var previousNumberOfVisibleCurve: Int? = null

    //current number of active curve
    private val _numberOfVisibleCurve = MutableLiveData<Int>(0)
    val numberOfVisibleCurve: LiveData<Int> get() = _numberOfVisibleCurve


    fun setNumberOfVisibleCurve(boolean:Boolean) {
        if (boolean){
            _numberOfVisibleCurve.value = _numberOfVisibleCurve.value?.plus(1)
        }

        else{
            _numberOfVisibleCurve.value = _numberOfVisibleCurve.value?.minus(1)
        }

        Log.e("GVM numberOfVisibleCurve", "numberOfVisibleCurve = ${numberOfVisibleCurve.value}" )

    }


    /*
    fun initNumberOfVisibleCurve() {
        for(element in curvesVisibilityList){
            if (element.value == true ){
                _numberOfVisibleCurve.value = _numberOfVisibleCurve.value?.plus(1)
            }
        }

        Log.e("numberOfVisibleCurve", "numberOfVisibleCurve = ${numberOfVisibleCurve.value}" )

    }
    */
    fun initNumberOfVisibleCurve() {
        for(map in curvesVisibilityList){
            val keys = map.keys
            for (key in keys ) {
                if (map[key]!!.value == true ){
                    _numberOfVisibleCurve.value = _numberOfVisibleCurve.value?.plus(1)
                }
            }

        }

        Log.e("numberOfVisibleCurve", "numberOfVisibleCurve = ${numberOfVisibleCurve.value}" )

    }



//_____________________________________________graph recording ________________________________________________________
    /*
    private val _isRecordingActivated = MutableLiveData<Boolean>()
    val isRecordingActivated: LiveData<Boolean> get() = _isRecordingActivated

    fun setRecordingState(boolean: Boolean){
        _isRecordingActivated.value = boolean
    }
    */



    // Stores Uri of a created file
    private val _fileUri = MutableLiveData<Uri?>()
    val fileUri: LiveData<Uri?> get() = _fileUri

    fun setFileUri(uri: Uri) {
        _fileUri.value = uri
    }

    //Stores the Uri of the created file.
    private val _selectedDirectoryUri = MutableLiveData<Uri?>()
    val selectedDirectoryUri: LiveData<Uri?> get() = _selectedDirectoryUri

    fun setSelectedDirectoryUri(uri: Uri) {
        _selectedDirectoryUri.value = uri
    }

/*
    // Uri List
    private val _uriList = mutableListOf<Uri>()
    val uriList: MutableList<Uri>  = _uriList
*/

    private val _fileUris = MutableLiveData<MutableMap<String, Uri>>()
    val fileUris: LiveData<MutableMap<String, Uri>> get() = _fileUris

    init {
        _fileUris.value = mutableMapOf()
    }

    fun addFile(fileName: String, uri: Uri) {
        _fileUris.value?.put(fileName, uri)
    }

    fun getFileUri(fileName: String): Uri? {
        return _fileUris.value?.get(fileName)
    }





    //RecordDatas state
    private val _isRecordDatasChecked = MutableLiveData<Boolean>(false)
    val isRecordDatasChecked: LiveData<Boolean> get() = _isRecordDatasChecked

    fun setIsRecordDatasChecked(visible: Boolean) {
        _isRecordDatasChecked.value = visible
        setNumberOfVisibleCurve(visible)
    }





    //ESSAI AVEC VARIABLES :
    val listeDePoints = List(4) { mutableListOf<Double>() }

    val listeDePoints2 = List(4) { mutableListOf<Double>() }


    //Liste de DataBuffers
    /*
    private val _listDataBuffers = MutableLiveData< MutableList<DataBuffer> >()
    val listDataBuffers: LiveData< MutableList<DataBuffer> > get() = _listDataBuffers
    */

    /*
    private val _mapDataBuffers = MutableLiveData<  MutableMap< String, DataBuffer >   >()
    val mapDataBuffers: LiveData<     MutableMap<   String, DataBuffer   >     > get() = _mapDataBuffers
    */

    private val _mapDataBuffers = MutableLiveData<MutableMap<String, DataBuffer>>().apply {
        value = mutableMapOf()  // Initialiser avec un MutableMap vide
    }
    val mapDataBuffers: LiveData<MutableMap<String, DataBuffer>> get() = _mapDataBuffers

}


