package com.esh1n.guidtoarchapp.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.presentation.di.GlobalDI
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val darkThemeSwitch: SwitchMaterial = view.findViewById(R.id.switch_dark_theme)
        val preferenceRepository = GlobalDI.getPreferenceRepository()


        preferenceRepository.isDarkThemeLive.observe(
            viewLifecycleOwner,
            { isDarkTheme ->
                switch_dark_theme.setText(if (isDarkTheme) R.string.text_turn_on_dark_theme else R.string.text_turn_off_dark_theme)
                isDarkTheme?.let { darkThemeSwitch.isChecked = it }
            })

        darkThemeSwitch.setOnCheckedChangeListener { _, checked ->
            preferenceRepository.isDarkTheme = checked
        }
    }

}