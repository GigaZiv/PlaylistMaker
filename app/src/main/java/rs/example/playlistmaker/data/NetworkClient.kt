package rs.example.playlistmaker.data

import rs.example.playlistmaker.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}