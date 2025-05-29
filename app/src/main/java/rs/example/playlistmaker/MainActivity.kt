package rs.example.playlistmaker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val b1 = findViewById<Button>(R.id.find)
        val b2 = findViewById<Button>(R.id.media)
        val b3 = findViewById<Button>(R.id.tools)

        b1.setOnClickListener {
            startActivity(Intent(this@MainActivity, FindActivity::class.java))
        }
        b2.setOnClickListener {
            startActivity(Intent(this@MainActivity, MediaActivity::class.java))
        }
        b3.setOnClickListener {
            startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
        }
    }
}