package rs.example.playlistmaker.data.network

enum class StatusResponse(val statusString: String) {
    SUCCESS(""),
    EMPTY(""),
    ERROR(""),
    PROGRESS("")
}