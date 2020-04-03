package codestart.info.kotlinphoto

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import kotlinx.android.synthetic.main.caption_view.*

class captionView: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.caption_view)

        val iv_photo = findViewById(R.id.iv_photo) as ImageView
        val extras = intent.extras
        val myUri = Uri.parse(extras!!.getString("imageUri"))
        iv_photo.setImageURI(myUri)

        val bundle: Bundle? = intent.extras
        val msg = bundle!!.getString( "user_message")

        txvBase64view.text=msg

    }
}