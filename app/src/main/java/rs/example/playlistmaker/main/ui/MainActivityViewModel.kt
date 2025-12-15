package rs.example.playlistmaker.main.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rs.example.playlistmaker.search.domain.models.Track

class MainActivityViewModel : ViewModel() {

    private val currentLiveData = MutableLiveData<Track>()
    fun getCurrentTrack(): LiveData<Track> = currentLiveData

    fun setCurrentTrack(track: Track) {
        currentLiveData.postValue(track)
    }

}