package rs.example.playlistmaker.search.ui.view_model

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rs.example.playlistmaker.AppConstant.Companion.SEARCH_DEBOUNCE_DELAY
import rs.example.playlistmaker.search.domain.api.TracksInteractor
import rs.example.playlistmaker.search.domain.models.Track
import rs.example.playlistmaker.search.ui.SearchState


class SearchViewModel(private val tracksInteractor: TracksInteractor) : ViewModel() {
    private val stateLiveData = MutableLiveData<SearchState>()
    fun observeState(): LiveData<SearchState> = stateLiveData

    private var latestSearchText: String? = null
    private val handler = Handler(Looper.getMainLooper())

    fun searchDebounce(changedText: String, error: Boolean = false) {
        if (latestSearchText == changedText && !error) {
            return
        }
        this.latestSearchText = changedText
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)

        val searchRunnable = Runnable { search(changedText) }

        val postTime = SystemClock.uptimeMillis() + SEARCH_DEBOUNCE_DELAY
        handler.postAtTime(
            searchRunnable,
            SEARCH_REQUEST_TOKEN,
            postTime,
        )
    }

    override fun onCleared() {
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
    }

    private val tracks = ArrayList<Track>()

    fun searchHistory() {
        val history = tracksInteractor.getTrackList()
        if (history.isNotEmpty())
            renderState(
                SearchState.EmptyInput(
                    history
                )
            ) else renderState(
            SearchState.AllEmpty
        )
    }

    fun setTrack(track: Track) {
        tracksInteractor.setTrack(track)
    }

    fun clear() {
        tracksInteractor.clear()
    }


    private fun search(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(
                SearchState.Loading
            )
            tracksInteractor.searchTracks(
                newSearchText
            ) { foundTrack, errorMessage ->
                handler.post {
                    if (foundTrack != null) {
                        tracks.clear()
                        tracks.addAll(foundTrack)
                    }
                    when {
                        errorMessage != null -> {
                            renderState(
                                SearchState.Error(
                                    errorMessage
                                )
                            )
                        }

                        tracks.isEmpty() -> {
                            renderState(
                                SearchState.Empty(
                                    tracksInteractor.getEmptyMessage(),
                                )
                            )
                        }

                        else -> {
                            renderState(
                                SearchState.Content(
                                    tracks
                                )
                            )
                        }
                    }

                }
            }
        } else {
            searchHistory()
        }
    }

    private fun renderState(state: SearchState) {
        stateLiveData.postValue(state)
    }

    companion object {
        private val SEARCH_REQUEST_TOKEN = Any()
    }
}