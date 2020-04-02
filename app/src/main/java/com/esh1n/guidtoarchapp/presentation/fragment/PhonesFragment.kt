package com.esh1n.guidtoarchapp.presentation.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.data.PhoneEntry
import com.esh1n.guidtoarchapp.presentation.MainActivity
import com.esh1n.guidtoarchapp.presentation.adapter.PhonesAdapter

class PhonesFragment : Fragment(R.layout.fragment_acticle) {
    private var adapter: PhonesAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        adapter = PhonesAdapter(
            requireActivity(),
            this::openPhone
        )
        val dividerItemDecoration =
            DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val phones = arrayListOf(PhoneEntry("Пожарная", "01"), PhoneEntry("МВД", "02"))
        adapter?.setPhones(phones)
    }

    private fun openPhone(phone: PhoneEntry) {
        startDialer(requireActivity(), phone.content)
    }

    fun startDialer(context: Context, phone: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phone")
        context.startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).setABTitle(getString(R.string.text_phones))
    }

    companion object {

        fun newInstance(): PhonesFragment {
            return PhonesFragment()
        }
    }
}