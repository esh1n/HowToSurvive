package com.esh1n.guidtoarchapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.presentation.di.GlobalDI
import com.esh1n.guidtoarchapp.presentation.utils.observeNonNull

class RootActivity : AppCompatActivity(R.layout.activity_root) {

    //private val appBarConfiguration by appBarConfig(R.id.activity_root__fragment__nav_host)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.activity_root__fragment__nav_host) as NavHostFragment
//        val navController = navHostFragment.navController
//        toolbar.setupWithNavController(navController, appBarConfiguration)
        GlobalDI.getPreferenceRepository().nightModeLive.observeNonNull(
            this,
            delegate::setLocalNightMode
        )
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.activity_root__fragment__nav_host)
//        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//    }
}