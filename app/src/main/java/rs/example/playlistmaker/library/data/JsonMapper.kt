package rs.example.playlistmaker.library.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import rs.example.playlistmaker.search.domain.models.Track
import java.lang.reflect.Type
import kotlin.collections.mutableListOf

class JsonMapper(
    private val gson: Gson
) {
    fun convertToList(jsonList: String?): MutableList<Track> {
        val listOfMyClassObject: Type = object : TypeToken<ArrayList<Track>?>() {}.type
        return if (!jsonList.isNullOrEmpty()) gson.fromJson(jsonList, listOfMyClassObject) else mutableListOf()
    }

    fun setTrack(track: Track, jsonList: String?): String {
        val tracks = convertToList(jsonList)
        tracks.add(0, track)
        return write(tracks)
    }


    private fun write(tracks: MutableList<Track>): String {
        return gson.toJson(tracks)
    }
}
