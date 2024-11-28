package com.google.mediapipe.examples.poselandmarker.fragment

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
import androidx.lifecycle.ViewModelProvider
import com.google.mediapipe.examples.poselandmarker.DataBuffer
import com.google.mediapipe.examples.poselandmarker.MainActivity
import com.google.mediapipe.examples.poselandmarker.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GraphParameters.newInstance] factory method to
 * create an instance of this fragment.
 */
class GraphParameters : Fragment() {
    /*
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_graph_parameters, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GraphParameters.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GraphParameters().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    */

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

        return inflater.inflate(R.layout.fragment_graph_parameters, container, false)
    }


    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the ViewModel
        graphViewModel = ViewModelProvider(requireActivity()).get(GraphViewModel::class.java)

        // Buttons initializations
        val buttonSpeed: Button = view.findViewById(R.id.button_speed)

        val buttonAccessDatas: Button = view.findViewById(R.id.button_acces_datas)
        //buttonAccessDatas.isEnabled = false

        val buttonRecordDatas : ToggleButton = view.findViewById(R.id.button_record_datas)


        // Initialize the switches

            //Hand switches
        val switchRightHandX: Switch = view.findViewById(R.id.switch_x_RH)
        val switchRightHandY: Switch = view.findViewById(R.id.switch_y_RH)
        val switchRightHandZ: Switch = view.findViewById(R.id.switch_z_RH)

        val switchLeftHandX: Switch = view.findViewById(R.id.switch_x_LH)
        val switchLeftHandY: Switch = view.findViewById(R.id.switch_y_LH)
        val switchLeftHandZ: Switch = view.findViewById(R.id.switch_z_LH)


            //Elbow
        val switchRightElbowX: Switch = view.findViewById(R.id.switch_x_RE)
        val switchRightElbowY: Switch = view.findViewById(R.id.switch_y_RE)
        val switchRightElbowZ: Switch = view.findViewById(R.id.switch_z_RE)

        val switchLeftElbowX: Switch = view.findViewById(R.id.switch_x_LE)
        val switchLeftElbowY: Switch = view.findViewById(R.id.switch_y_LE)
        val switchLeftElbowZ: Switch = view.findViewById(R.id.switch_z_LE)



            //Shoulder switches
        val switchRightShoulderX: Switch = view.findViewById(R.id.switch_x_RS)
        val switchRightShoulderY: Switch = view.findViewById(R.id.switch_y_RS)
        val switchRightShoulderZ: Switch = view.findViewById(R.id.switch_z_RS)

        val switchLeftShoulderX: Switch = view.findViewById(R.id.switch_x_LS)
        val switchLeftShoulderY: Switch = view.findViewById(R.id.switch_y_LS)
        val switchLeftShoulderZ: Switch = view.findViewById(R.id.switch_z_LS)

        //Switches config init

        switchRightHandX.setChecked(graphViewModel.isCurveRightHandXVisible.value!!)
        switchRightHandY.setChecked(graphViewModel.isCurveRightHandYVisible.value!!)
        switchRightHandZ.setChecked(graphViewModel.isCurveRightHandZVisible.value!!)
        switchLeftHandX.setChecked(graphViewModel.isCurveLeftHandXVisible.value!!)
        switchLeftHandY.setChecked(graphViewModel.isCurveLeftHandYVisible.value!!)
        switchLeftHandZ.setChecked(graphViewModel.isCurveLeftHandZVisible.value!!)

        switchRightElbowX.setChecked(graphViewModel.isCurveRightElbowXVisible.value!!)
        switchRightElbowY.setChecked(graphViewModel.isCurveRightElbowYVisible.value!!)
        switchRightElbowZ.setChecked(graphViewModel.isCurveRightElbowZVisible.value!!)
        switchLeftElbowX.setChecked(graphViewModel.isCurveLeftElbowXVisible.value!!)
        switchLeftElbowY.setChecked(graphViewModel.isCurveLeftElbowYVisible.value!!)
        switchLeftElbowZ.setChecked(graphViewModel.isCurveLeftElbowZVisible.value!!)

        switchRightShoulderX.setChecked(graphViewModel.isCurveRightShoulderXVisible.value!!)
        switchRightShoulderY.setChecked(graphViewModel.isCurveRightShoulderYVisible.value!!)
        switchRightShoulderZ.setChecked(graphViewModel.isCurveRightShoulderZVisible.value!!)
        switchLeftShoulderX.setChecked(graphViewModel.isCurveLeftShoulderXVisible.value!!)
        switchLeftShoulderY.setChecked(graphViewModel.isCurveLeftShoulderYVisible.value!!)
        switchLeftShoulderZ.setChecked(graphViewModel.isCurveLeftShoulderZVisible.value!!)


        buttonRecordDatas.isChecked = graphViewModel.isRecordDatasChecked.value!!


        // Set listeners for the switches

        switchRightHandX.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveRightHandXVisible(isChecked)
        }

        switchRightHandY.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveRightHandYVisible(isChecked)
        }

        switchRightHandZ.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveRightHandZVisible(isChecked)
        }

        switchLeftHandX.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveLeftHandXVisible(isChecked)
        }

        switchLeftHandY.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveLeftHandYVisible(isChecked)
        }

        switchLeftHandZ.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveLeftHandZVisible(isChecked)
        }






        switchRightElbowX.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveRightElbowXVisible(isChecked)
        }

        switchRightElbowY.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveRightElbowYVisible(isChecked)
        }

        switchRightElbowZ.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveRightElbowZVisible(isChecked)
        }

        switchLeftElbowX.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveLeftElbowXVisible(isChecked)
        }

        switchLeftElbowY.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveLeftElbowYVisible(isChecked)
        }

        switchLeftElbowZ.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveLeftElbowZVisible(isChecked)
        }






        switchRightShoulderX.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveRightShoulderXVisible(isChecked)
        }

        switchRightShoulderY.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveRightShoulderYVisible(isChecked)
        }

        switchRightShoulderZ.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveRightShoulderZVisible(isChecked)
        }

        switchLeftShoulderX.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveLeftShoulderXVisible(isChecked)
        }

        switchLeftShoulderY.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveLeftShoulderYVisible(isChecked)
        }

        switchLeftShoulderZ.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setCurveLeftShoulderZVisible(isChecked)
        }


        //buttonRecordDatas.setChecked(graphViewModel.isRecordDatasChecked.value!!)

        graphViewModel.isRecordDatasChecked.observe(viewLifecycleOwner,{ isChecked ->
            buttonRecordDatas.isChecked = isChecked
        })


        buttonSpeed.setOnClickListener {
            val fragmentSpeedParameters= SpeedParameters()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.add(R.id.fragment_container, fragmentSpeedParameters)
            transaction.addToBackStack("SpeedParameters")
            transaction.commit()
        }


        buttonRecordDatas.setOnCheckedChangeListener { _, isChecked ->
            graphViewModel.setIsRecordDatasChecked(isChecked)
            if (isChecked) {
                var fileName : String? = null
                val filesNames = mutableListOf<String>()

                if (graphViewModel.isCurveRightHandXVisible.value == true || graphViewModel.isCurveRightHandYVisible.value == true || graphViewModel.isCurveRightHandZVisible.value == true){
                    filesNames.add("RightHand")
                }


                if (graphViewModel.isCurveLeftHandXVisible.value == true || graphViewModel.isCurveLeftHandYVisible.value == true || graphViewModel.isCurveLeftHandZVisible.value == true){
                    filesNames.add("LeftHand")
                }

                if (graphViewModel.isCurveRightElbowXVisible.value == true || graphViewModel.isCurveRightElbowYVisible.value == true || graphViewModel.isCurveRightElbowZVisible.value == true){
                    filesNames.add("RightElbow")
                }

                if (graphViewModel.isCurveLeftElbowXVisible.value == true || graphViewModel.isCurveLeftElbowYVisible.value == true || graphViewModel.isCurveLeftElbowZVisible.value == true){
                    filesNames.add("LeftElbow")
                }

                if (graphViewModel.isCurveRightShoulderXVisible.value == true || graphViewModel.isCurveRightShoulderYVisible.value == true || graphViewModel.isCurveRightShoulderZVisible.value == true){
                    filesNames.add("RightShoulder")
                }

                if (graphViewModel.isCurveLeftShoulderXVisible.value == true || graphViewModel.isCurveLeftShoulderYVisible.value == true || graphViewModel.isCurveLeftShoulderZVisible.value == true){
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

                graphViewModel.mapDataBuffers.value!!.put(fileName,DataBuffer(bufferSize = 32 * 1024, contentResolver = requireActivity().contentResolver, fileName = fileName, uri = graphViewModel.getFileUri(fileName)!!) )
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

}