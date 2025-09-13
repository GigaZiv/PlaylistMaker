package rs.example.playlistmaker.data.dto

class TunesResponse(
    val resultCount: Int,
    val expression: String,
    val results: List<TrackDto>
): Response()