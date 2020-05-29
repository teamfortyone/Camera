package teamfortyone.projects.multipart

import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_caption.*
import java.util.*
//import android.support.v7.app.AppCompatActivity


class captionActivity : AppCompatActivity() , TextToSpeech.OnInitListener {

    var file: String? = null

    var Beamk3:String = MainActivity.Companion.beamk3
    var Beamk5:String = MainActivity.Companion.beamk5
    var Greedy:String = MainActivity.Companion.greedy

    private var tts: TextToSpeech? = null
    private var btnbeamk3: Button? = null
    private var btnbeamk5: Button? = null
    private var btngreedy: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caption)
        Log.e("Image:" ,"Image displayed");

        file = MainActivity.Companion.fileP


        imgV.setImageBitmap(BitmapFactory.decodeFile(file))

        val arrayAdapter: ArrayAdapter<*>
        val users = arrayOf(
            "Beam_k3: "+Beamk3,"Beamk5: "+Beamk5,"Greedy: "+Greedy
        )

        // access the listView from xml file
        var listView = findViewById<ListView>(R.id.capList)
        arrayAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, users)
        listView.adapter = arrayAdapter

//        btnbeamk3 = this.btnbeamk3
//        val btnbeamk3 = findViewById<Button>(R.id.btnbeamk3)

//        btnbeamk3 = this.btnbeamk3
//        btnbeamk5 = this.btnbeamk5
//        btngreedy = this.btngreedy

//        tts = TextToSpeech(this, this)
//
//        btnbeamk3!!.setOnClickListener { speakOut() }
//        btnbeamk5!!.setOnClickListener { speakOut() }
//        btngreedy!!.setOnClickListener { speakOut() }

        // get reference to button
        val spkk3 = findViewById(R.id.spkbeamk3) as Button
        val spkk5 = findViewById(R.id.spkbeamk5) as Button
        val spkgreed = findViewById(R.id.spkgreedy) as Button

        // set on-click listener
        spkk3!!.setOnClickListener {
            // your code to perform when the user clicks on the button

            btnbeamk3 = this.btnbeamk3
             val text = this.Beamk3

            tts = TextToSpeech(this, this)
            speakOut()
        }

        spkk5!!.setOnClickListener {
            // your code to perform when the user clicks on the button

            btnbeamk3 = this.btnbeamk5
            val text = this.Beamk5
            tts = TextToSpeech(this, this)
            speakOut()
        }

        spkgreed!!.setOnClickListener {
            // your code to perform when the user clicks on the button

            btngreedy = this.btngreedy
            val text = this.Greedy
            tts = TextToSpeech(this, this)
            speakOut()
        }

    }


    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported")
            } else {
                btnbeamk3!!.isEnabled = true
            }
        } else {
            Log.e("TTS", "Initialization Failed")
        }
    }

    private fun speakOut() {
        val text = Beamk3
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }


}
