package rs.example.playlistmaker.utils

import android.view.View
import java.text.SimpleDateFormat
import java.util.Locale

object StaffFunctions {

    fun clearVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    fun getSimpleDateFormat(i: Int): String {
        return SimpleDateFormat(
            "mm:ss",
            Locale.getDefault()
        ).format(i)
    }

}