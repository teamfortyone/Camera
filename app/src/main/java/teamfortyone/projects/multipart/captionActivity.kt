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


class captionActivity : AppCompatActivity(){

    lateinit var mTTS:TextToSpeech

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
        Log.e("Image:" ,"Image displayed")

        file = MainActivity.Companion.fileP


        imgV.setImageBitmap(BitmapFactory.decodeFile(file))

        val arrayAdapter: ArrayAdapter<*>
        val users = arrayOf(
            "Beam (3): "+Beamk3,"Beam (5): "+Beamk5,"Greedy: "+Greedy
        )

        // access the listView from xml file
        var listView = findViewById<ListView>(R.id.capList)
        arrayAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, users)
        listView.adapter = arrayAdapter

        mTTS = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR){
                //if there is no error then set language
                mTTS.language = Locale.US
            }
        })

        // get reference to button
        val spkk3 = findViewById<Button>(R.id.spkbeamk3)
        val spkk5 = findViewById<Button>(R.id.spkbeamk5)
        val spkgreed = findViewById<Button>(R.id.spkgreedy)


        // set on-click listener
        spkk3.setOnClickListener {
            // your code to perform when the user clicks on the button
            btnbeamk3 = this.btnbeamk3
            val textk3 = this.Beamk3
            mTTS.speak(textk3, TextToSpeech.QUEUE_FLUSH, null)

        }

        spkk5.setOnClickListener {
            // your code to perform when the user clicks on the button

            btnbeamk3 = this.btnbeamk5
            val textk5 = this.Beamk5
            mTTS.speak(textk5, TextToSpeech.QUEUE_FLUSH, null)

        }

        spkgreed.setOnClickListener {
            // your code to perform when the user clicks on the button

            btngreedy = this.btngreedy
            val textg = this.Greedy
            mTTS.speak(textg, TextToSpeech.QUEUE_FLUSH, null)

        }

    }




//    override fun onInit(status: Int) {
//
//        if (status == TextToSpeech.SUCCESS) {
//            // set US English as language for tts
//            val result = tts!!.setLanguage(Locale.US)
//
//            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                Log.e("TTS", "The Language specified is not supported")
//            } else {
//                spkbeamk3!!.isEnabled = true
//                spkbeamk5!!.isEnabled = true
//                spkgreedy!!.isEnabled = true
//            }
//        } else {
//            Log.e("TTS", "Initialization Failed")
//        }
//    }

    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }


}
