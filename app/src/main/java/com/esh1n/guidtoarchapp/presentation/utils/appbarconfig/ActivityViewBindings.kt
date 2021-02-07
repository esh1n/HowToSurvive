package com.esh1n.guidtoarchapp.presentation.utils.appbarconfig

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ComponentActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration


@JvmName("viewBindingActivity")
public fun AppCompatActivity.appBarConfig(@IdRes navHostFragmentId: Int): ViewBindingProperty<ComponentActivity, AppBarConfiguration> {
    return appBarConfig {
        val navHostFragment =
            supportFragmentManager.findFragmentById(navHostFragmentId) as NavHostFragment
        val navController = navHostFragment.navController
        AppBarConfiguration(navController.graph)
    }
}

public fun AppCompatActivity.appBarConfig(
    topLevelDestinationIds: Set<Int>,
    fallbackOnNavigateUpListener: () -> Boolean = { false }
): ViewBindingProperty<ComponentActivity, AppBarConfiguration> {
    return appBarConfig {
        AppBarConfiguration.Builder(topLevelDestinationIds)
            .setOpenableLayout(null)
            .setFallbackOnNavigateUpListener(fallbackOnNavigateUpListener)
            .build()
    }
}

@JvmName("viewBindingFragment")
public fun <A : ComponentActivity, T : Any> ComponentActivity.appBarConfig(appBarProvider: (A) -> T): ViewBindingProperty<A, T> {
    return ActivityAppBarConfigProperty(appBarProvider)
}

class ActivityAppBarConfigProperty<A : ComponentActivity, T : Any>(appBarProvider: (A) -> T) :
    ViewBindingProperty<A, T>(appBarProvider) {

    override fun getLifecycleOwner(thisRef: A) = thisRef
}
