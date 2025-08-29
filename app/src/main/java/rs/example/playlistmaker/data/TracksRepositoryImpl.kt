package rs.example.playlistmaker.data

import rs.example.playlistmaker.data.dto.TunesRequest
import rs.example.playlistmaker.data.dto.TunesResponse
import rs.example.playlistmaker.data.network.RetrofitNetworkClient
import rs.example.playlistmaker.domain.api.TracksRepository
import rs.example.playlistmaker.domain.models.Track

class TracksRepositoryImpl(private val networkClient: RetrofitNetworkClient) : TracksRepository {
    override fun searchTracks(expression: String): List<Track> {
        val response = try {
            networkClient.doRequest(TunesRequest(expression))
        } catch (e: Exception) {
            throw Exception(e)
        }
        if (response.resultCode == 200) {
            return (response as TunesResponse).results.map {
                Track(
                    it.trackId,
                    it.collectionName,
                    it.releaseDate,
                    it.primaryGenreName,
                    it.country,
                    it.trackName,
                    it.artistName,
                    it.trackTimeMillis,
                    it.artworkUrl100,
                    it.previewUrl
                )
            }
        } else {
            return emptyList()
        }
    }

}