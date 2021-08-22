package com.esh1n.guidtoarchapp.presentation.phones

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

class PhonesFragment : Fragment(R.layout.fragment_phones) {
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

        val phones = arrayListOf(PhoneEntry("единый телефон экстренных служб", "112"),
            PhoneEntry("пожарная служба и спасатели", "101"),
            PhoneEntry("полиция", "102"),
            PhoneEntry("скорая медицинская помощь", "103"),
            PhoneEntry("служба газа", "104"),
            PhoneEntry("«телефон доверия»", "+7 (495) 637-22-22"))
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
}