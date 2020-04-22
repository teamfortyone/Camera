package teamfortyone.projects.multipart.Helper


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class Mediahelper {


    //    https://stackoverflow.com/a/4830846
    var nameFile=""
    var fileUri = Uri.parse("")
    val RC_CAMERA = 100
//    var mediaFile: File=""


    fun getMyFileName() : String{
        return this.nameFile
    }

    fun getRcCamera():Int{
        return  this.RC_CAMERA
    }

    fun getOutputMediaFile(): File?{
        val mediaStorageDir = File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DCIM), "kotlinCam")
        if(!mediaStorageDir.exists())
            if(!mediaStorageDir.mkdirs()){
                Log.e("mkdir" , "failed to make directory")
            }
        val mediaFile = File(mediaStorageDir.path+ File.separator+ "${this.nameFile}")
        Log.e("mediaFile-getopmf" , mediaFile.toString())
        return mediaFile
    }

    fun path(): String{
        var pathOfCamera:String = getOutputMediaFile().toString()
        Log.e("Path from Path function" , pathOfCamera)
        return  pathOfCamera
    }

    fun getOutputMediaFileUri(): Uri {
        val timeStamp:String = SimpleDateFormat("yyyyMMddHHmmss" , Locale.getDefault()).format(Date())
        this.nameFile = "DC_${timeStamp}.jpg"
        this.fileUri = Uri.fromFile(getOutputMediaFile())
        return this.fileUri
    }

    fun bitmapTOString(bmp : Bitmap) : String{
        val outputStream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG,60,outputStream)
        val byteArray = outputStream.toByteArray()
        return Base64.encodeToString(byteArray , Base64.DEFAULT)
    }

    fun getBitmapToString(imV : ImageView, uri : Uri):String{
        var bmp: Bitmap = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888)
        bmp = BitmapFactory.decodeFile(this.fileUri.path)
        var dim = 720
        if (bmp.height  > bmp.width){
            bmp = Bitmap.createScaledBitmap(bmp,
                (bmp.width*dim).div(bmp.height),dim,true)
        }else{
            bmp = Bitmap.createScaledBitmap(bmp,
                dim,(bmp.height*dim).div(bmp.width),true)
        }
        imV.setImageBitmap(bmp)
        return  bitmapTOString(bmp)
        return ""
    }

}