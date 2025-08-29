package rs.example.playlistmaker.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.core.widget.doAfterTextChanged
import rs.example.playlistmaker.databinding.ActivitySearchBinding
import rs.example.playlistmaker.domain.models.Track
import rs.example.playlistmaker.AppConstant
import rs.example.playlistmaker.AppConstant.Companion.ID_SEARCH_QUERY
import rs.example.playlistmaker.AppConstant.Companion.SEARCH_DEBOUNCE_DELAY
import rs.example.playlistmaker.AppConstant.Companion.CLICK_DEBOUNCE_DELAY
import rs.example.playlistmaker.R
import rs.example.playlistmaker.creator.Creator
import rs.example.playlistmaker.data.network.StatusResponse
import rs.example.playlistmaker.presentation.adapter.TracksAdapter

class SearchActivity : AppCompatActivity() {
    private var isClickAllowed = true
    private val threadHandler by lazy {
        Handler(Looper.getMainLooper())
    }
    private val binding: ActivitySearchBinding by lazy {
        ActivitySearchBinding.inflate(layoutInflater)
    }

    private val getProvideTrackInteractor by lazy {
        Creator.provideTrackInteractor()
    }
    private val trackStorePreferences by lazy {
        Creator.getTrackStorePreferences(
            getSharedPreferences(
                AppConstant.Companion.SHARED_PREF_ID, MODE_PRIVATE
            )
        )
    }
    private val tracks: MutableList<Track> = mutableListOf()
    private val adapter = TracksAdapter(tracks) {
        if (clickDebounce()) {
            trackStorePreferences.setTrack(it)
            openPlayer(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.search_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top = systemBars.top, bottom = systemBars.bottom)
            insets
        }

        binding.apply {

            intent.extras?.let {
                etSearch.setText(it.getString(ID_SEARCH_QUERY))
            }

            setSupportActionBar(tbSearch)

            tbSearch.setNavigationOnClickListener {
                hideKeyboard(); this@SearchActivity.finish()
            }

            iwClear.setOnClickListener {
                etSearch.text.clear(); tracks.clear(); onShowResult(StatusResponse.SUCCESS)
                adapter.notifyDataSetChanged(); hideKeyboard()
            }

            rcvSearch.adapter = adapter

            etSearch.apply {

                val runnableSearch = Runnable {
                    onRequest(this.text.trim().toString())
                }

                doAfterTextChanged { s ->
                    iwClear.isVisible = !s.isNullOrEmpty()
                    threadHandler.removeCallbacks(runnableSearch)
                    if (this.hasFocus() && s?.isEmpty() == true) {
                        if (!trackStorePreferences.readTracks().isEmpty()) {
                            rcvSearch.isVisible = false
                            llHistory.isVisible = true
                        }
                    } else {
                        llHistory.isVisible = false
                        threadHandler.apply {
                            postDelayed(runnableSearch, SEARCH_DEBOUNCE_DELAY)
                        }
                    }
                    rcvHistory.adapter = TracksAdapter(trackStorePreferences.readTracks()) {
                        trackStorePreferences.setTrack(it)
                        openPlayer(it)
                    }
                }

                setOnFocusChangeListener { view, hasFocus ->
                    llHistory.isVisible =
                        hasFocus && (view as EditText).text.isEmpty() && !trackStorePreferences.readTracks()
                            .isEmpty()
                    rcvHistory.adapter = TracksAdapter(trackStorePreferences.readTracks()) {
                        trackStorePreferences.setTrack(it)
                        openPlayer(it)
                    }
                }
            }

            bRefreshNetwork.setOnClickListener {
                llNwNotFound.isVisible = false
                onRequest(etSearch.text.trim().toString())

            }

            bClearHistory.setOnClickListener {
                trackStorePreferences.clearTracks(); llHistory.isVisible = false
            }
        }

    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            threadHandler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    private fun openPlayer(track: Track) {
        startActivity(
            Intent(
                this@SearchActivity, AudioPlayer::class.java
            ).apply {
                putExtra("trackId", track.trackId)
            })
    }

    private fun hideKeyboard() {
        this.currentFocus?.let { view ->
            val imm = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun onRequest(paramSearch: String) {
        onShowResult(StatusResponse.PROGRESS)
        getProvideTrackInteractor.searchTracks(paramSearch, {
            threadHandler.post {
                if (it.isNotEmpty()) {
                    tracks.apply {
                        clear(); addAll(it)
                    }

                    adapter.notifyDataSetChanged()

                    if (tracks.isNotEmpty()) {
                        onShowResult(StatusResponse.SUCCESS)
                    } else {
                        onShowResult(StatusResponse.EMPTY)
                    }
                }
            }
        }, {
            threadHandler.post {
                onShowResult(StatusResponse.ERROR)
            }
        })
    }

    private fun onShowResult(statusResponse: StatusResponse) = with(binding) {
        when (statusResponse) {
            StatusResponse.SUCCESS -> {
                rcvSearch.isVisible = true
                llNwNotFound.isVisible = false
                llTrackNotFound.isVisible = false
                pbProgress.isVisible = false
            }

            StatusResponse.EMPTY -> {
                pbProgress.isVisible = false
                rcvSearch.isVisible = false
                llNwNotFound.isVisible = false
                llTrackNotFound.isVisible = true
                twTrNotFound.text = getString(R.string.c_nothing_not_found)
            }

            StatusResponse.ERROR -> {
                pbProgress.isVisible = false
                rcvSearch.isVisible = false
                llNwNotFound.isVisible = true
                llTrackNotFound.isVisible = false
                twErrorNetwork.text = getString(R.string.c_not_internet)
            }

            StatusResponse.PROGRESS -> {
                pbProgress.isVisible = true
                rcvSearch.isVisible = false
                llNwNotFound.isVisible = false
                llTrackNotFound.isVisible = false
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(ID_SEARCH_QUERY, binding.etSearch.text.toString())
    }

}