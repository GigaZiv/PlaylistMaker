package rs.example.playlistmaker.search.data.network

import rs.example.playlistmaker.search.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}