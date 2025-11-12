package rs.example.playlistmaker.settings.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.example.playlistmaker.databinding.FragmentSettingsBinding
import rs.example.playlistmaker.settings.ui.view_model.SettingsViewModel
import rs.example.playlistmaker.settings.util.ActionType


class SettingsFragment : Fragment() {

    private val viewModel by viewModel<SettingsViewModel>()
    private lateinit var binding: FragmentSettingsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.swModeUi.isChecked = viewModel.getTheme()
        binding.swModeUi.setOnCheckedChangeListener { _, checked ->
            viewModel.execute(ActionType.Theme(checked))
        }
        binding.shareApp.setOnClickListener {
            viewModel.execute(ActionType.Share)
        }
        binding.supportApp.setOnClickListener {
            viewModel.execute(ActionType.Support)
        }
        binding.termsApp.setOnClickListener {
            viewModel.execute(ActionType.Term)
        }
    }
}
