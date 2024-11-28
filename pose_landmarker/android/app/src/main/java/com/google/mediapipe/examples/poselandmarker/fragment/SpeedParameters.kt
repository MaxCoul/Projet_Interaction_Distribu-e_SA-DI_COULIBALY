package com.google.mediapipe.examples.poselandmarker.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import android.widget.ToggleButton
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.mediapipe.examples.poselandmarker.DataBuffer
import com.google.mediapipe.examples.poselandmarker.MainActivity
import com.google.mediapipe.examples.poselandmarker.R
import androidx.activity.OnBackPressedCallback

class SpeedParameters : Fragment() {
    private lateinit var graphViewModel: GraphViewModel

    private var fileUri: Uri? = null

    companion object {
        private const val CREATE_FILE_REQUEST_CODE = 1
        private const val REQUEST_CODE_OPEN_DOCUMENT = 1
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment

        //setupOnBackPressed()
        return inflater.inflate(R.layout.fragment_speed_parameters, container, false)
    }

    // ATTENTION FOR NOW : ajout uniquement par dessus du fragment 

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Ici, tu gères ce qui doit se passer lors du retour
                // Par exemple : fermer le fragment ou faire autre chose
                Toast.makeText(context, "Retour intercepté dans le Fragment", Toast.LENGTH_SHORT).show()

                // Si tu veux laisser l'événement passer pour quitter le fragment, appelle :
                // isEnabled = false
                // requireActivity().onBackPressed()
            }
        })

        // Get the ViewModel
        graphViewModel = ViewModelProvider(requireActivity()).get(GraphViewModel::class.java)

        // Buttons initializations
        val buttonPosition: Button = view.findViewById(R.id.button_speed)

        val buttonAccessDatas: Button = view.findViewById(R.id.button_acces_datas)
        //buttonAccessDatas.isEnabled = false

        val buttonRecordDatas : ToggleButton = view.findViewById(R.id.button_record_datas)


        // Initialize the switches

        //Hand switches
        val switchRightHandDX: Switch = view.findViewById(R.id.switch_dx_RH)
        val switchRightHandDY: Switch = view.findViewById(R.id.switch_dy_RH)
        val switchRightHandDZ: Switch = view.findViewById(R.id.switch_dz_RH)

        val switchLeftHandDX: Switch = view.findViewById(R.id.switch_dx_LH)
        val switchLeftHandDY: Switch = view.findViewById(R.id.switch_dy_LH)
        val switchLeftHandDZ: Switch = view.findViewById(R.id.switch_dz_LH)


        //Elbow
        val switchRightElbowDX: Switch = view.findViewById(R.id.switch_dx_RE)
        val switchRightElbowDY: Switch = view.findViewById(R.id.switch_dy_RE)
        val switchRightElbowDZ: Switch = view.findViewById(R.id.switch_dz_RE)

        val switchLeftElbowDX: Switch = view.findViewById(R.id.switch_dx_LE)
        val switchLeftElbowDY: Switch = view.findViewById(R.id.switch_dy_LE)
        val switchLeftElbowDZ: Switch = view.findViewById(R.id.switch_dz_LE)



        //Shoulder switches
        val switchRightShoulderDX: Switch = view.findViewById(R.id.switch_dx_RS)
        val switchRightShoulderDY: Switch = view.findViewById(R.id.switch_dy_RS)
        val switchRightShoulderDZ: Switch = view.findViewById(R.id.switch_dz_RS)

        val switchLeftShoulderDX: Switch = view.findViewById(R.id.switch_dx_LS)
        val switchLeftShoulderDY: Switch = view.findViewById(R.id.switch_dy_LS)
        val switchLeftShoulderDZ: Switch = view.findViewById(R.id.switch_dz_LS)

        //Switches config init

        switchRightHandDX.setChecked(graphViewModel.isCurveRightHandDXVisible.value!!)
        switchRightHandDY.setChecked(graphViewModel.isCurveRightHandDYVisible.value!!)
        switchRightHandDZ.setChecked(graphViewModel.isCurveRightHandDZVisible.value!!)
        switchLeftHandDX.setChecked(graphViewModel.isCurveLeftHandDXVisible.value!!)
        switchLeftHandDY.setChecked(graphViewModel.isCurveLeftHandDYVisible.value!!)
        switchLeftHandDZ.setChecked(graphViewModel.isCurveLeftHandDZVisible.value!!)

        switchRightElbowDX.setChecked(graphViewModel.isCurveRightElbowDXVisible.value!!)
        switchRightElbowDY.setChecked(graphViewModel.isCurveRightElbowDYVisible.value!!)
        switchRightElbowDZ.setChecked(graphViewModel.isCurveRightElbowDZVisible.value!!)
        switchLeftElbowDX.setChecked(graphViewModel.isCurveLeftElbowDXVisible.value!!)
        switchLeftElbowDY.setChecked(graphViewModel.isCurveLeftElbowDYVisible.value!!)
        switchLeftElbowDZ.setChecked(graphViewModel.isCurveLeftElbowDZVisible.value!!)

        switchRightShoulderDX.setChecked(graphViewModel.isCurveRightShoulderDXVisible.value!!)
        switchRightShoulderDY.setChecked(graphViewModel.isCurveRightShoulderDYVisible.value!!)
        switchRightShoulderDZ.setChecked(graphViewModel.isCurveRightShoulderDZVisible.value!!)
        switchLeftShoulderDX.setChecked(graphViewModel.isCurveLeftShoulderDXVisible.value!!)
        switchLeftShoulderDY.setChecked(graphViewModel.isCurveLeftShoulderDYVisible.value!!)
        switchLeftShoulderDZ.setChecked(graphViewModel.isCurveLeftShoulderDZVisible.value!!)


        buttonRecordDatas.isChecked = graphViewModel.isRecordDatasChecked.value!!


        // Set listeners for the switches

        switchRightHandDX.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveRightHandDXVisible(isChecked)
        }

        switchRightHandDY.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveRightHandDYVisible(isChecked)
        }

        switchRightHandDZ.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveRightHandDZVisible(isChecked)
        }

        switchLeftHandDX.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveLeftHandDXVisible(isChecked)
        }

        switchLeftHandDY.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveLeftHandDYVisible(isChecked)
        }

        switchLeftHandDZ.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveLeftHandDZVisible(isChecked)
        }






        switchRightElbowDX.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveRightElbowDXVisible(isChecked)
        }

        switchRightElbowDY.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveRightElbowDYVisible(isChecked)
        }

        switchRightElbowDZ.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveRightElbowDZVisible(isChecked)
        }

        switchLeftElbowDX.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveLeftElbowDXVisible(isChecked)
        }

        switchLeftElbowDY.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveLeftElbowDYVisible(isChecked)
        }

        switchLeftElbowDZ.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveLeftElbowDZVisible(isChecked)
        }






        switchRightShoulderDX.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveRightShoulderDXVisible(isChecked)
        }

        switchRightShoulderDY.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveRightShoulderDYVisible(isChecked)
        }

        switchRightShoulderDZ.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveRightShoulderDZVisible(isChecked)
        }

        switchLeftShoulderDX.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveLeftShoulderDXVisible(isChecked)
        }

        switchLeftShoulderDY.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveLeftShoulderDYVisible(isChecked)
        }

        switchLeftShoulderDZ.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveLeftShoulderDZVisible(isChecked)
        }


        //buttonRecordDatas.setChecked(graphViewModel.isRecordDatasChecked.value!!)

        graphViewModel.isRecordDatasChecked.observe(viewLifecycleOwner,{ isChecked ->
            buttonRecordDatas.isChecked = isChecked
        })


        buttonPosition.setOnClickListener {
//            val fragmentSpeedParameters= GraphParameters()
//            val transaction = requireActivity().supportFragmentManager.beginTransaction()
//            transaction.add(R.id.fragment_container, fragmentSpeedParameters)
//            transaction.addToBackStack(null)
//            transaction.commit()
            parentFragmentManager.popBackStack()
        }


        buttonRecordDatas.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setIsRecordDatasChecked(isChecked)
            if (isChecked) {
                var fileName : String? = null
                val filesNames = mutableListOf<String>()

                if (graphViewModel.isCurveRightHandDXVisible.value == true || graphViewModel.isCurveRightHandDYVisible.value == true || graphViewModel.isCurveRightHandDZVisible.value == true){
                    filesNames.add("RightHand")
                }


                if (graphViewModel.isCurveLeftHandDXVisible.value == true || graphViewModel.isCurveLeftHandDYVisible.value == true || graphViewModel.isCurveLeftHandDZVisible.value == true){
                    filesNames.add("LeftHand")
                }

                if (graphViewModel.isCurveRightElbowDXVisible.value == true || graphViewModel.isCurveRightElbowDYVisible.value == true || graphViewModel.isCurveRightElbowDZVisible.value == true){
                    filesNames.add("RightElbow")
                }

                if (graphViewModel.isCurveLeftElbowDXVisible.value == true || graphViewModel.isCurveLeftElbowDYVisible.value == true || graphViewModel.isCurveLeftElbowDZVisible.value == true){
                    filesNames.add("LeftElbow")
                }

                if (graphViewModel.isCurveRightShoulderDXVisible.value == true || graphViewModel.isCurveRightShoulderDYVisible.value == true || graphViewModel.isCurveRightShoulderDZVisible.value == true){
                    filesNames.add("RightShoulder")
                }

                if (graphViewModel.isCurveLeftShoulderDXVisible.value == true || graphViewModel.isCurveLeftShoulderDYVisible.value == true || graphViewModel.isCurveLeftShoulderDZVisible.value == true){
                    filesNames.add("LeftShoulder")
                }

                if(filesNames.size > 0 ){
                    for(fileName in filesNames){
                        createFile(fileName)
                    }

                    /*    if (!context?.let { fileExists(it, fileName) }!!) {
                            createFile(fileName)
                        } else {
                            Toast.makeText(context, "File already exists", Toast.LENGTH_SHORT).show()
                        }*/

                }
                //val fileName = "newfile"

            }
            else{
                //graphViewModel.setIsRecordDatasChecked(false)
                if(graphViewModel.fileUris.value!!.containsKey("RightHand.txt")  || graphViewModel.fileUris.value!!.containsKey("LeftHand.txt")){

                    var maxLength = graphViewModel.listeDePoints.maxOf { it.size }
                    for (i in 0 until maxLength) {
                        for (sublist in graphViewModel.listeDePoints) {
                            if (i < sublist.size) {
                                val graphLastTimeValue = graphViewModel.listeDePoints[0][i]
                                val xValueRightHand = graphViewModel.listeDePoints[1][i]
                                val yValueRightHand = graphViewModel.listeDePoints[2][i]
                                val zValueRightHand = graphViewModel.listeDePoints[3][i]
                                writeToFile("RightHand.txt", "\n $graphLastTimeValue \t $xValueRightHand \t $yValueRightHand \t $zValueRightHand")
                            }
                        }
                    }

                    maxLength = graphViewModel.listeDePoints2.maxOf { it.size }
                    for (i in 0 until maxLength) {
                        for (sublist in graphViewModel.listeDePoints2) {
                            if (i < sublist.size) {
                                val graphLastTimeValue = graphViewModel.listeDePoints2[0][i]
                                val xValueRightHand = graphViewModel.listeDePoints2[1][i]
                                val yValueRightHand = graphViewModel.listeDePoints2[2][i]
                                val zValueRightHand = graphViewModel.listeDePoints2[3][i]
                                writeToFile("LeftHand.txt", "\n $graphLastTimeValue \t $xValueRightHand \t $yValueRightHand \t $zValueRightHand")
                            }
                        }
                    }
                }


                //Toast.makeText(context, "Recording completed", Toast.LENGTH_SHORT).show()

            }

        }


        buttonAccessDatas.setOnClickListener{
            fileUri = graphViewModel.fileUri.value
            if(graphViewModel.fileUris.value!!.size>0){
                openFilePicker(requireContext())
            }
            else {
                Toast.makeText(requireContext(), "No datas recorded", Toast.LENGTH_SHORT).show()
            }
        }
        //P

    }

    private fun createFile(fileName: String) {
        val downloadsUri = getDownloadsUri(requireContext().contentResolver)
        downloadsUri?.let { uri ->
            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                type = "text/plain" //"application/txt"
                addCategory(Intent.CATEGORY_OPENABLE)
                putExtra(Intent.EXTRA_TITLE, fileName)
                putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri)
            }
            startActivityForResult(intent, CREATE_FILE_REQUEST_CODE)
        }
    }

    private fun getDownloadsUri(contentResolver: ContentResolver): Uri? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Downloads.EXTERNAL_CONTENT_URI
        } else {
            MediaStore.Files.getContentUri("external")
        }
    }

    private fun fileExists(context: Context, fileName: String): Boolean {
        val projection = arrayOf(MediaStore.MediaColumns.DISPLAY_NAME)
        val selection = "${MediaStore.MediaColumns.DISPLAY_NAME} = ?"
        val selectionArgs = arrayOf(fileName)

        val uri: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Downloads.EXTERNAL_CONTENT_URI
        } else {
            MediaStore.Files.getContentUri("external")
        }

        context.contentResolver.query(uri, projection, selection, selectionArgs, null)?.use { cursor ->
            //return cursor.count > 0
            Log.d("GP FileExistsCheck", "Cursor count: ${cursor.count}")
            return cursor.count > 0
        }
        return false
    }



    private fun checkAndCreateFile(fileName: String) {
        graphViewModel.fileUri.value?.let { uri ->
            val childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(uri, DocumentsContract.getTreeDocumentId(uri))

            requireActivity().contentResolver.query(childrenUri, arrayOf(DocumentsContract.Document.COLUMN_DISPLAY_NAME), null, null, null)?.use { cursor ->
                var fileExists = false
                while (cursor.moveToNext()) {
                    val displayName = cursor.getString(0)
                    if (displayName == fileName) {
                        fileExists = true
                        break
                    }
                }
                if (fileExists) {
                    Toast.makeText(context, "File already exists", Toast.LENGTH_SHORT).show()
                } else {
                    (activity as MainActivity).createFile(fileName)
                }
            }
        }
    }

    /*
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
    
            if (requestCode == CREATE_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                data?.data?.let { uri ->
                    graphViewModel.setFileUri(uri)
                    graphViewModel.uriList.add(graphViewModel.fileUri.value!!)
                    for(uri in graphViewModel.uriList) {
                        requireActivity().contentResolver.openOutputStream(uri)?.use { outputStream ->
                            outputStream.write("time(ms)  \t \t x \t \t \t y \t \t \t z".toByteArray())
                        }
                        Toast.makeText(context, "File created successfully", Toast.LENGTH_SHORT).show()
                    }
                    /*
                    requireActivity().contentResolver.openOutputStream(uri)?.use { outputStream ->
                        outputStream.write("time(ms)  \t \t x \t \t \t y \t \t \t z".toByteArray())
                    }
                    Toast.makeText(context, "File created successfully", Toast.LENGTH_SHORT).show()
    
                     */
                }
            }
    
            else{
                Toast.makeText(context, "File unsuccessfull creation", Toast.LENGTH_SHORT).show()
            }
        }
    */

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CREATE_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                //val fileName = DocumentsContract.getDocumentId(uri).split(":").last()
                val fileName = getDisplayName(requireContext(), uri) ?: "default_name"
                //val fileName = getDisplayName(requireContext(), uri)
                graphViewModel.addFile(fileName, uri)
                writeToFile(fileName, "time(ms)  \t \t x \t \t \t y \t \t \t z\n")
                Toast.makeText(context, "File created successfully with name: $fileName", Toast.LENGTH_SHORT).show()
                Toast.makeText(context, "File created successfully", Toast.LENGTH_SHORT).show()
                val uri = graphViewModel.getFileUri(fileName)!!
                //val contentResolver = requireActivity().contentResolver

                graphViewModel.mapDataBuffers.value!!.put(fileName,
                    DataBuffer(bufferSize = 32 * 1024, contentResolver = requireActivity().contentResolver, fileName = fileName, uri = graphViewModel.getFileUri(fileName)!!) )
            }
        }
        else{
            graphViewModel.setIsRecordDatasChecked(false)
            Toast.makeText(context, "Unsuccessfull file creation", Toast.LENGTH_SHORT).show()
        }
    }






    private fun writeToFile(fileName: String, text: String) {
        val uri = graphViewModel.getFileUri(fileName)
        uri?.let {
            requireActivity().contentResolver.openOutputStream(it,"wa")?.use { outputStream ->
                outputStream.write(text.toByteArray())
            }
        }
    }

    /*
        private fun openFilePicker(context: Context) {
            if (context !is Activity) {
                Toast.makeText(context, "Le contexte doit être une activité.", Toast.LENGTH_SHORT).show()
                return
            }
    
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "text/plain" // Ou un type spécifique, comme "text/plain"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
            }
    
            // Vérifiez si une activité peut gérer cet intent
            val packageManager = context.packageManager
            val resolveInfo = intent.resolveActivity(packageManager)
    
            if (resolveInfo != null) {
                // Démarrez l'activité pour le résultat
                context.startActivityForResult(intent, REQUEST_CODE_OPEN_DOCUMENT)
            } else {
                Toast.makeText(context, "Aucune application disponible pour ouvrir le sélecteur de fichiers.", Toast.LENGTH_SHORT).show()
            }
        }
    */

    private fun openFilePicker(context: Context) {
        if (context !is Activity) {
            Toast.makeText(context, "Le contexte doit être une activité.", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "text/plain" // Utilisez */* pour tous les types de fichiers
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
        }

        context.startActivityForResult(intent, REQUEST_CODE_OPEN_DOCUMENT)

    }

    private fun openFile(context: Context, uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "text/plain")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "Aucune application disponible pour ouvrir ce fichier.", Toast.LENGTH_SHORT).show()
        }
    }



    //ADD
    private fun scanFile(context: Context, filePath: String) {
        MediaScannerConnection.scanFile(context, arrayOf(filePath), null) { path, uri ->
            Log.d("GP MediaScanner", "Scanned $path:")
            Log.d("GP MediaScanner", "-> uri=$uri")
        }
    }


    /*
        fun getDisplayName(context: Context, uri: Uri): String? {
            var displayName: String? = null
            val projection = arrayOf(OpenableColumns.DISPLAY_NAME)
    
            context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    displayName = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
                }
            }
            return displayName
        }
    */

    fun getDisplayName(context: Context, uri: Uri): String? {
        var displayName: String? = null
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (displayNameIndex != -1) {
                    displayName = it.getString(displayNameIndex)
                }
            }
        }
        return displayName
    }

/*
    override fun onBackPressedDispatcher() {
        val fragmentManager = requireActivity().supportFragmentManager

        // Cette commande pop la back stack jusqu'à "FragmentA"
        fragmentManager.popBackStack("RealTimeUpdates", FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

 */

    private fun setupOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val fragmentManager = requireActivity().supportFragmentManager
                if (isEnabled) {

                    // Cette commande pop la back stack jusqu'à "FragmentA"
                    fragmentManager.popBackStack(
                        "RealtimeUpdates",
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    //fragmentManager.popBackStack()
                    //fragmentManager.popBackStack()
                    Toast.makeText(context, "back in if", Toast.LENGTH_SHORT).show()
                }

                fragmentManager.popBackStack()
                fragmentManager.popBackStack()
                Toast.makeText(context, "back", Toast.LENGTH_SHORT).show()
            }
        })
    }

}