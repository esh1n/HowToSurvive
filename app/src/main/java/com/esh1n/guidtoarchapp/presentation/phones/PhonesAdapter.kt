package com.esh1n.guidtoarchapp.presentation.phones

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.data.PhoneEntry

class PhonesAdapter internal constructor(
    context: Context,
    private val clickOnItem: (PhoneEntry) -> (Unit)
) : RecyclerView.Adapter<PhonesAdapter.PhonesVH>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var phones = emptyList<PhoneEntry>() // Cached copy of words

    inner class PhonesVH(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val titleTextView: TextView = itemView.findViewById(R.id.phone_title)
        private val phoneTextView: TextView = itemView.findViewById(R.id.phone_number)

        init {
            itemView.setOnClickListener(this)
        }

        fun populate(word: PhoneEntry) {
            titleTextView.text = word.title
            phoneTextView.text = word.content
        }

        override fun onClick(v: View?) {
            val category = phones[bindingAdapterPosition]
            clickOnItem(category)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhonesVH {
        val itemView = inflater.inflate(R.layout.item_phone, parent, false)
        return PhonesVH(itemView)
    }

    override fun onBindViewHolder(holder: PhonesVH, position: Int) {
        val current = phones[position]
        holder.populate(current)
    }

    internal fun setPhones(phones: List<PhoneEntry>) {
        this.phones = phones
        notifyDataSetChanged()
    }

    override fun getItemCount() = phones.size
}
