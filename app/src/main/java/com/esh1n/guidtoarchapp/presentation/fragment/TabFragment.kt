package com.esh1n.guidtoarchapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.presentation.utils.addFragmentToStack

/**
 * Fragment for each of the bottom navigation tabs.
 *
 * @author bherbst
 */
class TabFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        val text = view.findViewById<View>(R.id.text) as TextView
        val addChildButton =
            view.findViewById<View>(R.id.add_child) as Button
        val number = arguments!!.getInt(ARG_KEY_NUMBER)
        text.text = "Tab $number"
        addChildButton.setOnClickListener { fragmentManager.addFragmentToStack(ChildFragment()) }
    }

    companion object {
        private const val ARG_KEY_NUMBER = "tab_number"
        fun newInstance(number: Int): TabFragment {
            val args = Bundle()
            args.putInt(ARG_KEY_NUMBER, number)
            val frag = TabFragment()
            frag.arguments = args
            return frag
        }
    }
}