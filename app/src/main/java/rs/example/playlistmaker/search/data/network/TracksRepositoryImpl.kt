package rs.example.playlistmaker.search.data.network

import android.content.Context
import rs.example.playlistmaker.R
import rs.example.playlistmaker.search.data.dto.SearchRequest
import rs.example.playlistmaker.search.data.dto.TrackResponse
import rs.example.playlistmaker.search.data.mapper.TrackMapper
import rs.example.playlistmaker.search.domain.api.TracksRepository
import rs.example.playlistmaker.search.domain.models.Track
import rs.example.playlistmaker.util.Resource

class TracksRepositoryImpl(private val networkClient: NetworkClient,
                           private val mapper: TrackMapper,
                           private val context: Context) :
    TracksRepository {

    override fun searchTracks(expression: String): Resource<List<Track>> {
        val response = networkClient.doRequest(SearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error(context.getString(R.string.c_not_internet))
            }

            200 -> {
                Resource.Success((response as TrackResponse).results.map {
                    mapper.trackMap(it)
                })
            }

            else -> {
                Resource.Error(context.getString(R.string.c_not_internet))
            }
        }
    }
    override fun getMessage(): String {
        return context.getString(R.string.c_nothing_not_found)
    }
}
