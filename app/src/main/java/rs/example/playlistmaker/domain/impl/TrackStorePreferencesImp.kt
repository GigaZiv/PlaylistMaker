package rs.example.playlistmaker.domain.impl

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import rs.example.playlistmaker.AppConstant
import rs.example.playlistmaker.domain.api.TrackStorePreferencesInteractor
import rs.example.playlistmaker.domain.models.Track

class TrackStorePreferencesImp(private val sharedPreference: SharedPreferences) :
    TrackStorePreferencesInteractor {
    override fun setTrack(track: Track) {
        val tracks = readTracks()
        tracks.apply {
            if (!remove(track) && size >= 10) {
                removeAt(9)
            }
            add(0, track)
        }
        sharedPreference.edit { putString(AppConstant.SEARCH_KEY_STORE, Gson().toJson(tracks)) }
    }

    override fun readTracks(): MutableList<Track> {
        val json = sharedPreference.getString(AppConstant.SEARCH_KEY_STORE, null)
            ?: return mutableListOf()
        return Gson().fromJson(json, object : TypeToken<ArrayList<Track>?>() {}.type)
    }

    override fun getTrack(trackId: String): Track? {
        val json = sharedPreference.getString(AppConstant.SEARCH_KEY_STORE, null)
            ?: return null
        return (Gson().fromJson(
            json,
            object : TypeToken<ArrayList<Track>?>() {}.type
        ) as MutableList<Track>).first { it.trackId == trackId }
    }

    override fun clearTracks() {
        sharedPreference.edit { remove(AppConstant.SEARCH_KEY_STORE) }
    }
}