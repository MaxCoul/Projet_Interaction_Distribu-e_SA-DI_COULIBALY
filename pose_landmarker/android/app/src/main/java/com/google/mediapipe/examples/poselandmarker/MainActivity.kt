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
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.ViewTreeObserver
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.mediapipe.examples.poselandmarker.databinding.ActivityMainBinding
import com.google.mediapipe.examples.poselandmarker.fragment.GraphViewModel
import com.google.mediapipe.examples.poselandmarker.fragment.RealTimeUpdatesListener
import com.google.mediapipe.examples.poselandmarker.fragment.RealtimeUpdates

import com.ingescape.*;



class MainActivity : AppCompatActivity(), RealTimeUpdatesListener {
    private lateinit var activityMainBinding: ActivityMainBinding
//    private val viewModel : MainViewModel by viewModels()

    private val sharedViewModel: SharedViewModel by viewModels<SharedViewModel>()
    private val graphViewModel: GraphViewModel by viewModels<GraphViewModel>()

    //lignes ingescape
    //private val globalContext = Global("ws://localhost:9009")
    //val a: Agent = globalContext.agentCreate()

    private val createFileLauncher = registerForActivityResult(ActivityResultContracts.CreateDocument("application/pdf")) { uri: Uri? ->
        uri?.let {
            graphViewModel.setFileUri(it)
        }
    }

    companion object{
        private var isObservingRealtimeUpdate = true

        fun setIsObservingRealtimeUpdate(boolean: Boolean){
            isObservingRealtimeUpdate = boolean
        }

        private const val CREATE_FILE_REQUEST_CODE = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        activityMainBinding.navigation.setupWithNavController(navController)
        activityMainBinding.navigation.setOnNavigationItemReselectedListener {
        // ignore the reselection


        }



        sharedViewModel.shouldStartRealtimeUpdate.observe(this) { shouldStart ->
            if (shouldStart && isObservingRealtimeUpdate == true) {
                supportFragmentManager.commit {
                    add(R.id.fragment_container, RealtimeUpdates())
                    addToBackStack("RealtimeUpdates")
                    sharedViewModel.setRealtimeUpdateActive(true)
                }
                //sharedViewModel.shouldStartRealtimeUpdate.removeObserver(sharedViewModel.shouldStartRealtimeUpdate)
                //sharedViewModel.shouldStartRealtimeUpdate.observe(this, sharedViewModel.shouldStartRealtimeUpdate)
                //val backStackEntryCount = supportFragmentManager.backStackEntryCount
                //Log.d("BackStackEntryCount", "Number of entries in back stack: $backStackEntryCount")
                //Log.d("FragmentTransaction", "RealtimeUpdates fragment added to back stack")
            }
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val backStackEntryCount = supportFragmentManager.backStackEntryCount
            Log.d("BackStackEntryCount", "Number of entries in back stack: $backStackEntryCount")
        }





        /*
        sharedViewModel.timerFinished.observe(this) { isFinished ->
            if (isFinished) {
                sharedViewModel.setRealtimeUpdateActive(true)
            /*    supportFragmentManager.commit {
                addFragmentGraph()
                }*/
                supportFragmentManager.commit {
                    add(R.id.fragment_container, RealtimeUpdates())
                    addToBackStack("RealtimeUpdatesFragment")

                }
                val backStackEntryCount = supportFragmentManager.backStackEntryCount
                Log.d("BackStackEntryCount", "Number of entries in back stack: $backStackEntryCount")
                Log.d("FragmentTransaction", "RealtimeUpdates fragment added to back stack")
            }
        }
        */

/*        // Ajouter un GlobalLayoutListener pour attendre que la vue soit prête
        navHostFragment.view?.viewTreeObserver?.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // Récupérer la vue OverlayView à partir du NavHostFragment
                val overlayView = navHostFragment.requireView().findViewById<OverlayView>(R.id.overlay)

                // Initialiser le ViewModel dans la vue OverlayView
                overlayView.initViewModel(sharedViewModel)

                // Supprimer le listener après l'exécution
                navHostFragment.view?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
            }
        })

        //RealtimeUpdates.setListener(this)

        sharedViewModel.timerFinished.observe(this) {
            isFinished ->
            if (isFinished ==true) {
                addFragmentGraph()
            }
        }                                                       */
    }



//***********************************MARCHE****************************
//    override fun onBackPressed() {
//        if (supportFragmentManager.backStackEntryCount > 0) {
//            //sharedViewModel.shouldStartRealtimeUpdate.removeObserver(shouldStartRealtimeUpdate)
//            isObservingRealtimeUpdate = false
//            supportFragmentManager.popBackStack()
//            //supportFragmentManager.popBackStackImmediate()
//            //sharedViewModel.setRealtimeUpdateActive(false)
//            //isObservingRealtimeUpdate = true
//        }
//        else {
//            super.onBackPressed()
//        }
//
//    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager

        if (fragmentManager.backStackEntryCount > 0) {
            val lastFragmentName = fragmentManager.getBackStackEntryAt(fragmentManager.backStackEntryCount - 1).name

            when (lastFragmentName) {
                "SpeedParameters" -> fragmentManager.popBackStack("RealtimeUpdates", 0)
                //"FragmentD" -> fragmentManager.popBackStack("FragmentB", 0)
                else -> fragmentManager.popBackStack()
            }
        } else {
            super.onBackPressed()
        }
    }




    /*
    override fun onBackPressed() {
        finish()
    }*/


    override fun onValueChanged(newValue: Boolean) {
        // Vérifie la nouvelle valeur
        if (newValue) {
            val existingFragment = supportFragmentManager.findFragmentByTag("RealtimeUpdatesFragment")
            if (existingFragment == null) {
                addFragmentGraph()
            }
        }
    }

    fun addFragmentGraph() {
        val fragment = RealtimeUpdates()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_container, fragment, "RealtimeUpdatesFragment")
        fragmentTransaction.addToBackStack("RealtimeUpdates")
        fragmentTransaction.commit()

        val backStackEntryCount = supportFragmentManager.backStackEntryCount
        Log.d("BackStackEntryCount", "Number of entries in back stack: $backStackEntryCount")
        Log.d("BackStackEntryCount", "Number of entries in back stack")
    }


    fun createFile(fileName: String) {
        val downloadsUri = getDownloadsUri(contentResolver)
        downloadsUri?.let { uri ->
            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                type = "application/txt"
                addCategory(Intent.CATEGORY_OPENABLE)
                putExtra(Intent.EXTRA_TITLE, fileName)
                putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri)
            }
            createFileLauncher.launch(fileName)
        }
    }

    private fun getDownloadsUri(contentResolver: ContentResolver): Uri? {
        //return MediaStore.Downloads.EXTERNAL_CONTENT_URI
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Downloads.EXTERNAL_CONTENT_URI
        } else {
            MediaStore.Files.getContentUri("external")
        }
    }
}

