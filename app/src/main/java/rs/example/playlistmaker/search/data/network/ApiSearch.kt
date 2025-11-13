package rs.example.playlistmaker.search.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import rs.example.playlistmaker.search.data.dto.TrackResponse

interface ApiSearch {
    @GET("/search?entity=song")
    suspend fun search(@Query("term") text: String): TrackResponse
}