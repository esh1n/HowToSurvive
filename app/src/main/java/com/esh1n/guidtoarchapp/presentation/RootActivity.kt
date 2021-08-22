package com.esh1n.guidtoarchapp.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.presentation.utils.observeNonNull
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootActivity : AppCompatActivity(R.layout.activity_root) {

    private val themeSwitcherViewModel: ThemeSwitcherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        themeSwitcherViewModel.nighModeLiveData.observeNonNull(this, delegate::setLocalNightMode)
    }
}