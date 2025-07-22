package rs.example.playlistmaker.network

enum class StatusResponse(val statusString: String) {
    SUCCESS(""),
    EMPTY(""),
    ERROR(""),
    PROGRESS("")
}