package codestart.info.kotlinphoto

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.net.Uri
import android.util.Base64
import android.widget.ImageView
import kotlinx.android.synthetic.main.preview_image.*
import android.content.Intent
//import android.graphics.Paint
//import android.os.Build
//import android.support.annotation.RequiresApi
import android.util.Log
//import android.view.accessibility.CaptioningManager
import android.widget.Toast
import codestart.info.kotlinphoto.activities.Api
import codestart.info.kotlinphoto.activities.model.Caption
import okhttp3.RequestBody
//import org.json.JSONException
//import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.*
//import java.lang.StringBuilder
import java.util.*
//import java.io.File
//import java.util.Base64

//import khttp.get
//import khttp.post
//import android.provider.ContactsContract.CommonDataKinds.Website.URL
//import okhttp3.*
//import java.nio.charset.Charset
//import javax.net.ssl.HttpsURLConnection


class imagePreview : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.preview_image)


        val iv_photo = findViewById(R.id.iv_photo) as ImageView
        val extras = intent.extras
        val myUri = Uri.parse(extras!!.getString("imageUri"))
        Log.i("TAG", "uri received")
        Log.i("uridata" , myUri.toString())
        iv_photo.setImageURI(myUri)
        //print(myUri.toString())


        //slicing the base64 string
        val raw = encode(myUri)
        Log.i("TAG", "encoding done")
        //print(raw)
        //Log.e("BASE64" , raw.toString())
        //Toast.makeText(this , raw , Toast.LENGTH_SHORT).show()
        val subtext = raw.substring(0, 20)
        Log.i("TAG", "slicing done")
        txvBase64view.text = subtext

        Toast.makeText(this, subtext , Toast.LENGTH_SHORT).show()

        var resbody:String = ""

        //Retrofit builder
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://6d4cb525.ngrok.io/")
                /*
                .addHeader("Content-Type", "application/json")
                .addHeader("client", "android")
                .addHeader("language", Locale.getDefault().language)
                .addHeader("os", android.os.Build.VERSION.RELEASE)
                 */
                .build()

        //object to call method
        val Api = retrofit.create(Api::class.java)

        btnToConvertToBase64.setOnClickListener {

            val map = HashMap<String, RequestBody>()

            val data = Caption(myUri.toString())
            val call = Api.sendBase64(data)  //"Caption", map

            //val call: Call<List<Caption>> = Api.getCaption()

            call.enqueue(object : Callback<List<Caption>> {
                override fun onFailure(call: Call<List<Caption>>, t: Throwable) {
                    //var rescode = t.message.toString()
                    //hidepDialog()
                    Log.v("Response gotten is", t.message)
                }

                override fun onResponse(call: Call<List<Caption>>, response: Response<List<Caption>>) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            //hidepDialog()
                            val serverResponse = response.body()
                            //Toast.makeText(applicationContext, serverResponse.message, Toast.LENGTH_SHORT).show()

                        }
                    } else {
                        //hidepDialog()
                        Toast.makeText(applicationContext, "problem uploading image", Toast.LENGTH_SHORT).show()
                    }
                    //var resbody = response.body().toString()
                    //response.body()
                    //response.code()
                }
            })

            //send the image to captionView view
            val uri = myUri
            val intent = Intent(this, captionView::class.java)
            intent.putExtra("imageUri", uri.toString())
            intent.putExtra( "user_message" , resbody )
            Log.i("TAG", "Activity 3 started")
            startActivity(intent)



        }

    }
    /*
    @RequiresApi(Build.VERSION_CODES.O)
    fun encode(uri: Uri): Uri{
        val bytes = File(uri).readBytes()
        val base64 = Base64.getEncoder().encodeToString(bytes)
        return base64
    }
    */

    fun encode(imageUri: Uri): String {
        val input = this.getContentResolver().openInputStream(imageUri)

        val image = BitmapFactory.decodeStream(input, null, null)
        //encode image to base64 string
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        var imageBytes = baos.toByteArray()
        val imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
        Log.i("encoded data" , imageString)
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
