package codestart.info.kotlinphoto

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.net.Uri
import android.util.Base64
import android.widget.ImageView
import kotlinx.android.synthetic.main.preview_image.*
import java.io.ByteArrayOutputStream
import android.content.ContentResolver




class imagePreview : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.preview_image)


        //val iv_photo = findViewById(R.id.iv_photo) as ImageView
        val extras = intent.extras
        val myUri = Uri.parse(extras!!.getString("imageUri"))
        //iv_photo.setImageURI(myUri)

        val text = encode(myUri)
        txvBase64view.text = text


    }


    fun encode(imageUri: Uri): String {
        val input = this.getContentResolver().openInputStream(imageUri)

        val image = BitmapFactory.decodeStream(input, null, null)
        //encode image to base64 string
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        var imageBytes = baos.toByteArray()
        val imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
        return imageString
    }
}

/*
    fun decode(imageString: String) {

        //decode base64 string to image
        val imageBytes = Base64.decode(imageString, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        imageview.setImageBitmap(decodedImage)

    }

 */

/*
    // start some dummy thread that is different from UI thread
    Thread(Runnable {
        // performing some dummy time taking operation



        // try to touch View of UI thread
        this@imagePreview.runOnUiThread(java.lang.Runnable {
            this.txvBase64view.text="Converting"
        })
    }).start()
}

 */
