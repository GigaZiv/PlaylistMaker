package rs.example.playlistmaker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import rs.example.playlistmaker.adapter.TracksAdapter
import rs.example.playlistmaker.models.Track

class SearchActivity : AppCompatActivity() {

    private var sText: String = ""
    private lateinit var etSearch: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        etSearch = findViewById<EditText>(R.id.etSearch)
        val iwClear = findViewById<ImageView>(R.id.iwClear)

        iwClear.setOnClickListener {
            etSearch.text.clear()
            val view: View? = this.currentFocus
            val iMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            iMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
        }

        val t = findViewById<TextView>(R.id.back_to_main_from_search)

        t.setOnClickListener {
            this.finish()
        }

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                iwClear.visibility = clearVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {
                s?.let { sText = s.toString() }
            }
        }

        etSearch.addTextChangedListener(watcher)

        init()

    }

    private fun init() {

        val listTrack = buildList<Track> {
            resources.getStringArray(R.array.tracks).forEach { s ->
                val arrayItem = s.split(",").toTypedArray()
                add(
                    Track(
                        trackName = arrayItem[0],
                        artistName = arrayItem[1],
                        trackTime = arrayItem[2],
                        artworkUrl100 = arrayItem[3]
                    )
                )
            }
        }

        val rw = findViewById<RecyclerView>(R.id.rcvSearch)
        rw.layoutManager = LinearLayoutManager(this)
        rw.adapter = TracksAdapter(listTrack)

    }

    private fun clearVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(ID_SEARCH_QUERY, sText)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        sText = savedInstanceState.getString(ID_SEARCH_QUERY).toString()
        etSearch.setText(sText)
    }

    /* Идентификатор */
    companion object {
        private const val ID_SEARCH_QUERY = "ID_SEARCH_QUERY"
    }

}