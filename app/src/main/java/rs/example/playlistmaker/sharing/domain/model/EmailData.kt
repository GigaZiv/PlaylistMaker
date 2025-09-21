package rs.example.playlistmaker.sharing.domain.model


data class EmailData(
    val emailAddressee: Array<String>,
    val emailTopic: String,
    val emailMessage: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EmailData

        if (!emailAddressee.contentEquals(other.emailAddressee)) return false
        if (emailTopic != other.emailTopic) return false
        if (emailMessage != other.emailMessage) return false

        return true
    }

    override fun hashCode(): Int {
        var result = emailAddressee.contentHashCode()
        result = 31 * result + emailTopic.hashCode()
        result = 31 * result + emailMessage.hashCode()
        return result
    }
}