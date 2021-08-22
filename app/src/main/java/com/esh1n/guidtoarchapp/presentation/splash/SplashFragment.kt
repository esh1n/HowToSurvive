package com.esh1n.guidtoarchapp.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.aaglobal.jnc_playground.ui.splash.SplashNavCommand
import com.aaglobal.jnc_playground.ui.splash.SplashViewModel
import com.esh1n.guidtoarchapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_splash.*


@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        splashContainer.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                viewModel.onSplashShown()
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {

            }

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
        })

        // Navigate with SingleLiveEvent from Splash screen
        viewModel.splashNavCommand.observe(viewLifecycleOwner, { splashNavCommand ->
            val navController = Navigation.findNavController(
                requireActivity(),
                R.id.activity_root__fragment__nav_host
            )
            val directions = when (splashNavCommand) {
                SplashNavCommand.NAVIGATE_TO_MAIN -> SplashFragmentDirections.actionSplashFragmentToMainFragment()
                SplashNavCommand.NAVIGATE_TO_ONBOARDING -> SplashFragmentDirections.actionSplashFragmentToMainFragment()
                null -> throw IllegalArgumentException("Illegal splash navigation command")
            }
            navController.navigate(directions)
        })
    }

    override fun onStart() {
        super.onStart()
        splashContainer.startLayoutAnimation()
    }
}