package rs.example.playlistmaker.search.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import rs.example.playlistmaker.search.data.dto.TrackResponse

interface ApiSearch {
    @GET("/search?entity=song")
    fun search(@Query("term") text: String): Call<TrackResponse>
}