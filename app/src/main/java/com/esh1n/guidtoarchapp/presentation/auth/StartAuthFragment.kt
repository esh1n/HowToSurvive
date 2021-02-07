package com.esh1n.guidtoarchapp.presentation.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.esh1n.guidtoarchapp.R
import kotlinx.android.synthetic.main.fragment_start_auth.*

class StartAuthFragment : Fragment(R.layout.fragment_start_auth) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment_start_auth__button.setOnClickListener {
            findNavController().navigate(R.id.action__StartAuthFragment__to__FinishAuthFragment)
        }
    }

}