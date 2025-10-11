package rs.example.playlistmaker.search.data.local

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import rs.example.playlistmaker.AppConstant.Companion.SEARCH_KEY_STORE
import rs.example.playlistmaker.search.data.dto.TrackDto
import java.lang.reflect.Type
import androidx.core.content.edit

class SharedPreferenceLocalStorage(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : LocalStorage {

    override fun getTrackList(): List<TrackDto> {
        return read()
    }

    override fun setTrack(track: TrackDto) {
        val tracks = read()
        if (!tracks.remove(track) && tracks.size >= 10) tracks.removeAt(
            10 - 1
        )
        tracks.add(0, track)
        write(tracks)
    }

    private fun read(): MutableList<TrackDto> {
        val json = sharedPreferences.getString(SEARCH_KEY_STORE, null)
            ?: return mutableListOf()
        val listOfMyClassObject: Type = object : TypeToken<ArrayList<TrackDto>?>() {}.type
        return gson.fromJson(json, listOfMyClassObject)
    }

    private fun write(tracks: MutableList<TrackDto>) {
        val json = gson.toJson(tracks)
        sharedPreferences.edit {
            putString(SEARCH_KEY_STORE, json)
        }
    }

    override fun clear() {
        sharedPreferences.edit {
            remove(SEARCH_KEY_STORE)
        }
    }
}
