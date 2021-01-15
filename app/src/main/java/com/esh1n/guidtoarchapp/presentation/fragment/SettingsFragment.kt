package com.esh1n.guidtoarchapp.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.esh1n.guidtoarchapp.App
import com.esh1n.guidtoarchapp.R
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val darkThemeSwitch: SwitchMaterial = view.findViewById(R.id.switch_dark_theme)
        val preferenceRepository = (requireActivity().application as App).preferenceRepository

        preferenceRepository.isDarkThemeLive.observe(
            viewLifecycleOwner,
            Observer<Boolean> { isDarkTheme ->
                isDarkTheme?.let { darkThemeSwitch.isChecked = it }
            })

        darkThemeSwitch.setOnCheckedChangeListener { _, checked ->
            preferenceRepository.isDarkTheme = checked
        }
    }

    companion object {

        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}