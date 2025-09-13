package rs.example.playlistmaker.data.network

import rs.example.playlistmaker.data.NetworkClient
import rs.example.playlistmaker.data.dto.Response
import rs.example.playlistmaker.data.dto.TunesRequest

class RetrofitNetworkClient : NetworkClient {

    override fun doRequest(dto: Any): Response {
        if (dto is TunesRequest) {
            val resp = SearchTrackInstance.getService().search(dto.expression).execute()

            val body = resp.body() ?: Response()

            return body.apply { resultCode = resp.code() }
        } else {
            return Response().apply { resultCode = 400 }
        }
    }
}
