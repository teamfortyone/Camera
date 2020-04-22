package teamfortyone.projects.multipart

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_caption.*

class captionActivity : AppCompatActivity() {

    var file: String? = null
//    private lateinit var listView ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caption)

        val bundle: Bundle? = intent.extras
//        file = bundle!!.getString("imageView")
        file = MainActivity.Companion.fileP
//        var Beamk3 = bundle!!.get("Beam_k3")
//        var BeamK5 = bundle!!.get("Beam_k5")
//        var Greedy = bundle!!.get("Greedy")

        var Beamk3:String = MainActivity.Companion.beamk3
        var Beamk5:String = MainActivity.Companion.beamk5
        var Greedy:String = MainActivity.Companion.greedy

        imgV.setImageBitmap(BitmapFactory.decodeFile(file))

        val arrayAdapter: ArrayAdapter<*>
        val users = arrayOf(
//            "Virat Kohli", "Rohit Sharma", "Steve Smith", "Kane Williamson", "Ross Taylor"
            "Beam_k3: "+Beamk3,"Beamk5: "+Beamk5,"Greedy: "+Greedy
        )

        // access the listView from xml file
        var listView = findViewById<ListView>(R.id.capList)
        arrayAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, users)
        listView.adapter = arrayAdapter




    }
}
