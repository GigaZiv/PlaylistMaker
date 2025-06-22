package rs.example.playlistmaker

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import rs.example.playlistmaker.adapter.TracksAdapter
import rs.example.playlistmaker.databinding.ActivitySearchBinding
import rs.example.playlistmaker.models.Track
import rs.example.playlistmaker.network.SearchTrackInstance
import rs.example.playlistmaker.utils.StaffFunctions.clearVisibility
import retrofit2.Callback
import retrofit2.Response
import rs.example.playlistmaker.network.TunesResponse

class SearchActivity : AppCompatActivity() {

    private val binding: ActivitySearchBinding by lazy {
        ActivitySearchBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {

            intent.extras?.let {
                etSearch.setText(it.getString(ID_SEARCH_QUERY))
            }

            iwClear.setOnClickListener {
                etSearch.text.clear()
                rcvSearch.visibility = View.GONE

                val view: View? = this@SearchActivity.currentFocus
                val iMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                iMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

            }

            rcvSearch.layoutManager = LinearLayoutManager(this@SearchActivity)

            etSearch.apply {
                doAfterTextChanged { s ->
                    iwClear.visibility = clearVisibility(s)
                }
                setOnEditorActionListener { sender, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        onRequest(sender.text.trim().toString())
                        true
                    }
                    false
                }

            }

            backToMainFromSearch.setOnClickListener {
                this@SearchActivity.finish()

            }

            bRefreshNetwork.setOnClickListener {
                llNwNotFound.visibility = View.GONE
                onRequest(etSearch.text.trim().toString())

            }
        }

    }

    private fun onRequest(paramSearch: String) {
        onShowLoading()
        SearchTrackInstance.getService()
            .search(text = paramSearch)
            .enqueue(object : Callback<TunesResponse> {
                override fun onResponse(
                    call: Call<TunesResponse>,
                    response: Response<TunesResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            val listTrack =
                                buildList<Track> { addAll(response.body()?.results!!) }
                            if (listTrack.isNotEmpty()) {
                                onShowResult(listTrack)
                            } else {
                                onShowEmpty()
                            }
                        } else -> { onShowError() }
                    }

                }

                override fun onFailure(
                    call: Call<TunesResponse>,
                    t: Throwable
                ) {
                    onShowError()
                    Log.d("RS", t.message.toString())
                }
            }
            )
    }

    private fun onShowLoading() {
        binding.rcvSearch.visibility = View.GONE
        binding.llNwNotFound.visibility = View.GONE
        binding.llTrackNotFound.visibility = View.GONE
    }

    private fun onShowEmpty() {
        binding.rcvSearch.visibility = View.GONE
        binding.llNwNotFound.visibility = View.GONE
        binding.llTrackNotFound.visibility = View.VISIBLE
        binding.twTrNotFound.text = getString(R.string.c_nothing_not_found)
    }

    private fun onShowError() {
        binding.rcvSearch.visibility = View.GONE
        binding.llNwNotFound.visibility = View.VISIBLE
        binding.llTrackNotFound.visibility = View.GONE
        binding.twErrorNetwork.text = getString(R.string.c_not_internet)
    }

    private fun onShowResult(track: List<Track>) {
        binding.rcvSearch.adapter = TracksAdapter(track)
        binding.rcvSearch.visibility = View.VISIBLE
        binding.llNwNotFound.visibility = View.GONE
        binding.llTrackNotFound.visibility = View.GONE

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(ID_SEARCH_QUERY, binding.etSearch.text.toString())
    }

    /* Идентификатор */
    companion object {
        private const val ID_SEARCH_QUERY = "ID_SEARCH_QUERY"
    }

}