package rs.example.playlistmaker.settings.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.example.playlistmaker.R
import rs.example.playlistmaker.databinding.ActivitySettingsBinding
import rs.example.playlistmaker.settings.util.ActionType
import rs.example.playlistmaker.settings.view_model.SettingsViewModel

class SettingsActivity : AppCompatActivity() {

    private val binding: ActivitySettingsBinding by lazy {
        ActivitySettingsBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModel<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.settings_activity))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top=systemBars.top, bottom = systemBars.bottom)
            insets
        }

        binding.apply {

            setSupportActionBar(tbSetting)

            tbSetting.setNavigationOnClickListener {
                this@SettingsActivity.finish()
            }

            swModeUi.apply {
                isChecked = viewModel.getTheme()
                setOnCheckedChangeListener { sender, isChecked ->
                    viewModel.execute(ActionType.Theme(sender.isChecked))

                }
            }

            shareApp.setOnClickListener {
                viewModel.execute(ActionType.Share)
            }

            supportApp.setOnClickListener {
                viewModel.execute(ActionType.Support)
            }

            termsApp.setOnClickListener {
                viewModel.execute(ActionType.Term)
            }
        }
    }

    companion object {
        fun show(context: Context) {
            context.startActivity(Intent(context, SettingsActivity::class.java))
        }
    }
}