package com.esh1n.guidtoarchapp.presentation.settings

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.presentation.ThemeSwitcherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_settings.*

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var onBackPressedCallback: OnBackPressedCallback? = null

    private val themeSwitcherViewModel: ThemeSwitcherViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        themeSwitcherViewModel.isDarkThemeLive.observe(
            viewLifecycleOwner,
            { isDarkTheme ->
                switch_dark_theme.setText(if (isDarkTheme) R.string.text_turn_on_dark_theme else R.string.text_turn_off_dark_theme)
                isDarkTheme?.let(switch_dark_theme::setChecked)
            })

        switch_dark_theme.setOnCheckedChangeListener { _, checked ->
            themeSwitcherViewModel.setIsDarkTheme(checked)
        }
        onBackPressedCallback =
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, false) {
                //showToast("есть изменения!")
            }
    }

}