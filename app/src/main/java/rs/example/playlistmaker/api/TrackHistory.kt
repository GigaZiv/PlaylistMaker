package rs.example.playlistmaker.api

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import rs.example.playlistmaker.models.Track
import androidx.core.content.edit
import rs.example.playlistmaker.AppConstant

class TrackHistory(private val sharedPreference: SharedPreferences) {

    fun setTrack(track: Track) {
        val tracks = readTracks()
        tracks.apply {
            if (!remove(track) && size >= 10) {
                removeAt(9)
            }
            add(0, track)
        }
        sharedPreference.edit { putString(AppConstant.SEARCH_KEY_STORE, Gson().toJson(tracks)) }
    }

    fun readTracks(): MutableList<Track> {
        val json = sharedPreference.getString(AppConstant.SEARCH_KEY_STORE, null)
            ?: return mutableListOf()
        return Gson().fromJson(json, object : TypeToken<ArrayList<Track>?>() {}.type)
    }

    fun getTrack(trackId: String): Track? {
        val json = sharedPreference.getString(AppConstant.SEARCH_KEY_STORE, null)
            ?: return null
        return (Gson().fromJson(
            json,
            object : TypeToken<ArrayList<Track>?>() {}.type
        ) as MutableList<Track>).first { it.trackId == trackId }
    }

    fun clearTracks() {
        sharedPreference.edit { remove(AppConstant.SEARCH_KEY_STORE) }
    }

}