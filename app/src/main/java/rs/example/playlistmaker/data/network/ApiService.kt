package rs.example.playlistmaker.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import rs.example.playlistmaker.data.dto.TunesResponse

interface ApiService {
    @GET("/search?entity=song")
    fun search(@Query("term") text: String): Call<TunesResponse>
}