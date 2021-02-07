package com.esh1n.guidtoarchapp.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.esh1n.guidtoarchapp.R
import kotlinx.android.synthetic.main.fragment_profile_container.*


class ProfileContainerFragment : Fragment(R.layout.fragment_profile_container) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment_profile_container__button__open_auth_flow.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.activity_root__fragment__nav_host)
                .navigate(R.id.action__MainFragment__to__AuthFlow)
        }
    }

}