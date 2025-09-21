package rs.example.playlistmaker.utils

import java.text.SimpleDateFormat
import java.util.Locale

object StaffFunctions {

    const val ZERO_TIME = "00:00"

    fun getSimpleDateFormatLong(i: Long): String {
        return SimpleDateFormat(
            "mm:ss",
            Locale.getDefault()
        ).format(i)
    }

    fun getSimpleDateFormatInt(i: Int): String {
        return SimpleDateFormat(
            "mm:ss",
            Locale.getDefault()
        ).format(i)
    }
}