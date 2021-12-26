package com.example.parsinglocaljsonfile

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private var listUrl = ArrayList<String>()
    private lateinit var adapter : RVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = RVAdapter(this,listUrl)
        rvMain.adapter = adapter
        rvMain.layoutManager = GridLayoutManager(this,2)
        loadJson(this)

    }



    private fun updateUI(result: String?) {


        var jsonObject = JSONArray(result)
        for (i in 0..jsonObject.length() - 1) {
            var holdURL = jsonObject.getJSONObject(i).getString("url")
            listUrl.add(holdURL)

        }

        adapter.notifyDataSetChanged()


    }


    // write fun to read data from data.json
    private fun loadJson(context: Context) {
        var input: InputStream? = null
        var jsonString: String

        try {

            // create input stream
            input = context.assets.open("data.json")

            var size = input.available()

            // create buffer with the size
            var buffer = ByteArray(size)

            // read data from inputStream into buffer
            input.read(buffer)

            // create json string
            jsonString = String(buffer)
            print("!!!!! $jsonString")
            updateUI(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            input?.close()
        }

    }
}