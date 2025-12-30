package rs.example.playlistmaker.main.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rs.example.playlistmaker.library.domain.model.PlayList
import rs.example.playlistmaker.search.domain.models.Track

class MainActivityViewModel : ViewModel() {

    private val currentLiveData = MutableLiveData<Track>()
    fun setCurrentTrack(track: Track) {
        currentLiveData.postValue(track)
    }

    fun getCurrentTrack(): LiveData<Track> = currentLiveData

    private val playListLiveData = MutableLiveData<PlayList>()
    fun setPlayList(playList: PlayList) {
        playListLiveData.postValue(playList)
    }

    fun getPlayList(): LiveData<PlayList> = playListLiveData

}