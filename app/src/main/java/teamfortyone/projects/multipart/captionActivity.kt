package teamfortyone.projects.multipart

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_caption.*

class captionActivity : AppCompatActivity() {

    var file: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caption)

        file = MainActivity.Companion.fileP

        var Beamk3:String = MainActivity.Companion.beamk3
        var Beamk5:String = MainActivity.Companion.beamk5
        var Greedy:String = MainActivity.Companion.greedy

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

    }
}
