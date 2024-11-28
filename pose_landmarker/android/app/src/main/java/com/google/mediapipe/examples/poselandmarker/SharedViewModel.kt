package com.google.mediapipe.examples.poselandmarker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.mediapipe.tasks.components.containers.NormalizedLandmark


class SharedViewModel : ViewModel() {
    private val _isRealtimeUpdateActive = MutableLiveData<Boolean>()        //private and writable+readable inside the class
    val isRealtimeUpdateActive: LiveData<Boolean> = _isRealtimeUpdateActive //public and readable outside the class (just avoid creating method getIsRealtimeUpdate)

    private val _timerFinished = MutableLiveData<Boolean>()
    val timerFinished: LiveData<Boolean> = _timerFinished

    private val _shouldStartRealtimeUpdate = MediatorLiveData<Boolean>().apply {
        addSource(timerFinished) { checkConditions() }
        addSource(isRealtimeUpdateActive) { checkConditions() }
    }
    val shouldStartRealtimeUpdate: LiveData<Boolean> = _shouldStartRealtimeUpdate


    private val _currentTimestamp = MutableLiveData<Long>()
    val currentTimestamp : LiveData<Long> = _currentTimestamp

    private var graphLastYValue : Float = 0F

    fun setGraphLastYValue(yvalue : Float){
        graphLastYValue = yvalue
    }

    fun getGraphLastYValue(): Float{
        return graphLastYValue
    }


    private var graphLastTimeValue : Float = 0F

    fun setGraphLastTimeValue(xvalue : Float){
        graphLastTimeValue = xvalue
    }

    fun getGraphLastTimeValue(): Float{
        return graphLastTimeValue
    }



    private var realTimeUpdateStartTime : Long? = null

    fun setRealTimeUpdateStartTime(realTimeUpdateStartTimeValue: Long?){
        /*
        if(realTimeUpdateStartTime == 0.toLong()){
            realTimeUpdateStartTime = realTimeUpdateStartTimeValue
            Log.e ("realTimeUpdateStartTime", "realTimeUpdateStartTime = $realTimeUpdateStartTime")
        }*/
        realTimeUpdateStartTime = realTimeUpdateStartTimeValue
        Log.e ("realTimeUpdateStartTime", "realTimeUpdateStartTime = $realTimeUpdateStartTime")
    }

    fun getRealTimeUpdateStartTime (): Long? {
        return realTimeUpdateStartTime
    }


    private fun checkConditions() {
        _shouldStartRealtimeUpdate.value = (_timerFinished.value == true && _isRealtimeUpdateActive.value == false)
    }

    fun setRealtimeUpdateActive(isActive: Boolean) {
        _isRealtimeUpdateActive.value = isActive
    }

    fun setTimerFinished(isFinished: Boolean) {
        _timerFinished.value = isFinished
    }

    fun setCurrentTimestamp(currentTimestamp:Long) {
        _currentTimestamp.value = currentTimestamp

    }


    private var sharedLandmarks : MutableList<NormalizedLandmark> = mutableListOf()

    fun setSharedLandmarks(landmarkResultList: MutableList<NormalizedLandmark>){
        sharedLandmarks = landmarkResultList.toMutableList()
    }

    fun getSharedLandmarks(): MutableList<NormalizedLandmark> {
        return sharedLandmarks
    }


}