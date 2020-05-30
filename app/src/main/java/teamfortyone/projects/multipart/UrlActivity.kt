package teamfortyone.projects.multipart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_url.*

class UrlActivity : AppCompatActivity() {
    companion object {
        var url:String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_url)

        val btnUrl = findViewById<EditText>(R.id.btnUrl)
        val Submit = findViewById<Button>(R.id.btnSubmit)

        Submit.setOnClickListener {
            url = btnUrl.text.toString()
//            Toast.makeText(this , url.toString() , Toast.LENGTH_SHORT).show()
            Toast.makeText(this , "Connection Successful" , Toast.LENGTH_SHORT).show()
            val Intent  = Intent(this , MainActivity::class.java)
            startActivity(Intent)
        }
    }


}
