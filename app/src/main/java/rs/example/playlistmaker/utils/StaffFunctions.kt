package rs.example.playlistmaker.utils

import java.text.SimpleDateFormat
import java.util.Locale

object StaffFunctions {

    fun getSimpleDateFormat(i: Long): String {
        return SimpleDateFormat(
            "mm:ss",
            Locale.getDefault()
        ).format(i)
    }

}