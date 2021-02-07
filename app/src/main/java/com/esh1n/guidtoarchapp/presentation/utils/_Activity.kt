package com.esh1n.guidtoarchapp.presentation.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

inline fun <reified T : Activity> Context?.startActivity() {
    this?.let {
        val intent = Intent(this, T::class.java)
        it.startActivity(intent)
    }
}

inline fun <reified T : Activity> Context?.startActivity(args: Bundle) {
    this?.let {
        val intent = Intent(this, T::class.java)
        intent.putExtras(args)
        it.startActivity(intent)
    }
}

fun FragmentActivity?.addSingleFragmentToContainer(
    fragment: Fragment,
    hide: Boolean = false,
    tag: String? = fragment.tag,
    @IdRes containerViewId: Int
) {
    this?.let {
        val fragmentNotExist =
            supportFragmentManager.findFragmentById(containerViewId) == null
        if (fragmentNotExist) {
            addFragment(fragment, hide, tag, containerViewId)
        }
    }
}

fun FragmentActivity?.addFragment(
    fragment: Fragment,
    hide: Boolean = false,
    tag: String? = fragment.tag, @IdRes containerViewId: Int
) {
    this?.let {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(containerViewId, fragment, tag)
        if (hide) {
            transaction.hide(fragment)
        }
        transaction.commit()
    }
}

fun FragmentManager?.replaceFragment(fragment: Fragment, tag: String, @IdRes containerViewId: Int) {
    this?.let {
        val transaction = beginTransaction()
        transaction.replace(containerViewId, fragment, tag)
        transaction.commit()
    }
}

fun FragmentManager?.addFragmentToStack(fragment: Fragment, @IdRes containerViewId: Int) {
    this?.let {
        val tag = fragment.javaClass.simpleName
        val transaction = beginTransaction()
        transaction.add(containerViewId, fragment, tag)
        transaction.addToBackStack(null).commit()
    }
}

fun AppCompatActivity.setABTitle(title: CharSequence?) {
    if (!title.isNullOrBlank()) {
        supportActionBar?.title = title
    }

}
