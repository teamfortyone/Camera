package teamfortyone.projects.multipart


import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.crystal.crystalpreloaders.widgets.CrystalPreloader
import id.zelory.compressor.Compressor
import teamfortyone.projects.dagger2.Component.DaggerMainComponent
import teamfortyone.projects.dagger2.Component.MainComponent
import teamfortyone.projects.multipart.Model.ResponseData
import teamfortyone.projects.multipart.Views.MainView
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.lang.reflect.Method
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Logger


class MainActivity : AppCompatActivity(), MainView, View.OnClickListener {

    private var mImageFileLocation = ""
    private var fileUri: Uri? = null
    private lateinit var imageView: ImageView
    val IMAGE_PICK_CODE = 1000
    val PERMISSION_CODE = 1001
    lateinit var pickImg: Button
    lateinit var uploadImg: Button
    //lateinit var picked_img: CircleImageView
    lateinit var loading: CrystalPreloader
    lateinit var mainComponent: MainComponent
    var filePath: String? = null
    var postPath: String? = null
    lateinit var compressedFile: File
    lateinit var selectedImage: Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            val m: Method = StrictMode::class.java.getMethod("disableDeathOnFileUriExposure")
            m.invoke(null)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        initViews()
        initObjects()
        pickImg.setOnClickListener(this)
        uploadImg.setOnClickListener(this)

    }

    override fun initObjects() {
        mainComponent = DaggerMainComponent.create()
    }

    override fun initViews() {
        pickImg = findViewById(R.id.pickImg)
        uploadImg = findViewById(R.id.uploadImg)
        loading = findViewById(R.id.loading)
        imageView = findViewById<ImageView>(R.id.preview)
    }

    override fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loading.visibility = View.GONE
    }

    override fun onClick(view: View?) {
        if (askForPermissions()) {
            // Permissions are already granted, do your stuff
            when (view!!.id) {
                R.id.pickImg -> {
                    MaterialDialog.Builder(this)
                        .title("Select option")
                        .items(R.array.uploadImages)
                        .itemsIds(R.array.itemIds)
                        .itemsCallback { dialog, view, which, text ->
                            when (which) {
                                0 -> {
                                    val galleryIntent = Intent(
                                        Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                    )
                                    startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO)
                                }
                                1 -> captureImage()
                                2 -> imageView.setImageResource(0)
                            }
                        }.show()
                }
                R.id.uploadImg -> {
                    showLoading()
                    if (postPath == null || postPath == "") {
                        Toast.makeText(this, "please select an image ", Toast.LENGTH_LONG).show()
                        return
                    } else {
                        val file = File(postPath!!)
                        compressedFile = Compressor(this).compressToFile(file)
                        val requestBody = RequestBody.create(MediaType.parse("*/*"), compressedFile)
                        val multipartBody: MultipartBody.Part =
                            MultipartBody.Part.createFormData("file", file.name, requestBody)
                        mainComponent.connect().getService().uploadFile(multipartBody)
                            .enqueue(object : retrofit2.Callback<ResponseData> {
                                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                                    Log.w("UploadFile", t.message.toString())
                                    val Icon = BitmapFactory.decodeResource(
                                        resources,
                                        R.drawable.ic_close
                                    )
                                    hideLoading()
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Uploaded failed",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                override fun onResponse(
                                    call: Call<ResponseData>,
                                    response: Response<ResponseData>
                                ) {
                                    if (response.isSuccessful) {
                                        hideLoading()
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Uploaded successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        Log.e(
                                            "Response_Body",
                                            response.body()!!.beam_k3
                                        )//GITHUB-Teamfortyone
                                        Log.e("Response_Body", response.body()!!.beam_k5)
                                        Log.e(
                                            "Response_Body",
                                            response.body()!!.greedy
                                        )//.toString())
                                        Log.e("Message", response.message())
                                        beamk3 = response.body()!!.beam_k3
                                        beamk5 = response.body()!!.beam_k5
                                        greedy = response.body()!!.greedy
                                        //sendNotifications("Uploaded successfully", selectedImage)
                                        val respo =
                                            Intent(applicationContext, captionActivity::class.java)
                                        startActivity(respo)
                                    } else {
                                        hideLoading()
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Uploaded failed",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            })
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if(requestCode == REQUEST_TAKE_PHOTO || requestCode == REQUEST_PICK_PHOTO ) {
                if (data == null) {
                    Toast.makeText(this@MainActivity, "Unable to choose image", Toast.LENGTH_SHORT)
                        .show()
                    return
                }
                if (data != null) {
                    selectedImage = data.data!!
                    //picked_img.setImageURI(selectedImage)
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    val cursor =
                        contentResolver.query(selectedImage, filePathColumn, null, null, null)
                    assert(cursor != null)
                    cursor!!.moveToFirst()
                    val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                    filePath = cursor.getString(columnIndex)
                    fileP = filePath
                    imageView.setImageBitmap(BitmapFactory.decodeFile(filePath))
                    cursor.close()
                    postPath = filePath
                }
            } else if (requestCode == CAMERA_PIC_REQUEST) {
                if (Build.VERSION.SDK_INT > 21) {

                    Glide.with(this).load(mImageFileLocation).into(imageView)
                    postPath = mImageFileLocation

                } else {
                    Glide.with(this).load(fileUri).into(imageView)
                    postPath = fileUri!!.path
                    fileP = fileUri.toString()

                }

            }
        }else if (resultCode != Activity.RESULT_CANCELED) {
            Toast.makeText(this, "Sorry, there was an error!", Toast.LENGTH_LONG).show()
        }
    }

    fun pickImageFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, IMAGE_PICK_CODE)
    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        when (requestCode) {
//            PERMISSION_CODE -> {
//                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    //permission from popup granted
//                    pickImageFromGallery()
//                } else {
//                    //permission from popup denied
//                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }

    fun isPermissionsAllowed(): Boolean {
        return if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            false
        } else true
    }

    fun askForPermissions(): Boolean {
        if (!isPermissionsAllowed()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this as Activity,Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(this as Activity,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),PERMISSION_CODE)
            }
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<String>,grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission is granted, you can perform your operation here
                    pickImageFromGallery()
                } else {
                    // permission is denied, you can ask for permission again, if you want
                    //  askForPermissions()
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton("App Settings",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    // send to app settings if permission is denied permanently
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri = Uri.fromParts("package", getPackageName(), null)
                    intent.data = uri
                    startActivity(intent)
                })
            .setNegativeButton("Cancel",null)
            .show()
    }


    /**
     * Launching camera app to capture image
     */
    private fun captureImage() {
        if (Build.VERSION.SDK_INT > 21) { //use this if Lollipop_Mr1 (API 22) or above
            val callCameraApplicationIntent = Intent()
            callCameraApplicationIntent.action = MediaStore.ACTION_IMAGE_CAPTURE

            // We give some instruction to the intent to save the image
            var photoFile: File? = null

            try {
                // If the createImageFile will be successful, the photo file will have the address of the file
                photoFile = createImageFile()
                // Here we call the function that will try to catch the exception made by the throw function
            } catch (e: IOException) {
                Logger.getAnonymousLogger().info("Exception error in generating the file")
                e.printStackTrace()
            }

            // Here we add an extra file to the intent to put the address on to. For this purpose we use the FileProvider, declared in the AndroidManifest.
            val outputUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", photoFile!!)
            callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri)

            // The following is a new line with a trying attempt
            callCameraApplicationIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)

            Logger.getAnonymousLogger().info("Calling the camera App by intent")

            // The following strings calls the camera app and wait for his file in return.
            startActivityForResult(callCameraApplicationIntent, CAMERA_PIC_REQUEST)
        } else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE)

            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)

            // start the image capture Intent
            startActivityForResult(intent, CAMERA_PIC_REQUEST)
        }

    }

    @Throws(IOException::class)
    internal fun createImageFile(): File {
        Logger.getAnonymousLogger().info("Generating the image - method started")

        // Here we create a "non-collision file name", alternatively said, "an unique filename" using the "timeStamp" functionality
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmSS").format(Date())
        val imageFileName = "IMAGE_" + timeStamp
        // Here we specify the environment location and the exact path where we want to save the so-created file
        val storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/caption_generate_app")
        Logger.getAnonymousLogger().info("Storage directory set")

        // Then we create the storage directory if does not exists
        if (!storageDirectory.exists()) storageDirectory.mkdir()

        // Here we create the file using a prefix, a suffix and a directory
        val image = File(storageDirectory, imageFileName + ".jpg")
        // File image = File.createTempFile(imageFileName, ".jpg", storageDirectory);

        // Here the location is saved into the string mImageFileLocation
        Logger.getAnonymousLogger().info("File name and path set")

        mImageFileLocation = image.absolutePath
        fileP = mImageFileLocation
        // fileUri = Uri.parse(mImageFileLocation);
        // The file is returned to the previous intent across the camera application
        return image
    }


    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri")
    }


    /**
     * Receiving activity result method will be called after closing the camera
     */

    /**
     * ------------ Helper Methods ----------------------
     */

    /**
     * Creating file uri to store image/video
     */
    fun getOutputMediaFileUri(type: Int): Uri {
        return Uri.fromFile(getOutputMediaFile(type))
    }


    companion object {
        private val REQUEST_TAKE_PHOTO = 0
        private val REQUEST_PICK_PHOTO = 2
        private val CAMERA_PIC_REQUEST = 1111

        var beamk3:String = ""
        var beamk5:String = ""
        var greedy:String = ""

        var fileP: String? = null

        private val TAG = MainActivity::class.java.simpleName

        val MEDIA_TYPE_IMAGE = 1
        val IMAGE_DIRECTORY_NAME = "Android File Upload"

        /**
         * returning image / video
         */
        private fun getOutputMediaFile(type: Int): File? {

            // External sdcard location
            val mediaStorageDir = File(
                Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME)

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d(TAG, "Oops! Failed create "
                            + IMAGE_DIRECTORY_NAME + " directory")
                    return null
                }
            }

            // Create a media file name
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(Date())
            val mediaFile: File
            if (type == MEDIA_TYPE_IMAGE) {
                mediaFile = File(mediaStorageDir.path + File.separator
                        + "IMG_" + ".jpg")
            } else {
                return null
            }

            return mediaFile
        }
    }
}