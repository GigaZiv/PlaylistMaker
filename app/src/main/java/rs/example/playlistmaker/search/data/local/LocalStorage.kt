package rs.example.playlistmaker.search.data.local

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import rs.example.playlistmaker.search.data.dto.TrackDto
import java.lang.reflect.Type
import androidx.core.content.edit
import rs.example.playlistmaker.AppConstant.Companion.SEARCH_KEY_STORE

class LocalStorage(private val sharedPreferences: SharedPreferences) {
    fun getTrackList(): List<TrackDto> {
        return read()
    }

    fun setTrack(track: TrackDto) {
        val tracks = read()
        tracks.apply {
            if (!remove(track) && size >= 10) {
                removeAt(9)
            }
            add(0, track)
        }
        sharedPreferences.edit { putString(SEARCH_KEY_STORE, Gson().toJson(tracks)) }
    }

    private fun read(): MutableList<TrackDto> {
        val json = sharedPreferences.getString(SEARCH_KEY_STORE, null)
            ?: return mutableListOf()
        val listOfMyClassObject: Type = object : TypeToken<ArrayList<TrackDto>?>() {}.type
        return Gson().fromJson(json, listOfMyClassObject)
    }

    fun clear() {
        sharedPreferences.edit {
            remove(SEARCH_KEY_STORE)
        }
    }

}