package com.xero.exercise.invoice.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xero.exercise.databinding.InvoiceListItemBinding
import com.xero.exercise.invoice.model.Invoice
import kotlinx.android.synthetic.main.invoice_list_item.view.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class InvoiceAdapter(val items: MutableList<Invoice> = mutableListOf()): RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceViewHolder {
        val binding = InvoiceListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InvoiceViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: InvoiceViewHolder, position: Int) {
        if (position < items.size) holder.bind(items[position])
    }

    inner class InvoiceViewHolder(itemView: InvoiceListItemBinding): RecyclerView.ViewHolder(itemView.root){
        @SuppressLint("SimpleDateFormat")
        fun bind(invoice: Invoice) {
            itemView.invoice_item_uuid.text = invoice.invoiceNumber.toString()
            itemView.invoice_item_quantity.text = "${invoice.lineItems.size}"
            itemView.invoice_item_cost.text = NumberFormat.getCurrencyInstance().apply {
                maximumIntegerDigits = 2
                currency = Currency.getInstance("NZD")

            }.format(invoice.getTotal())
            itemView.invoice_item_date.text = SimpleDateFormat("dd/MM/YYYY").format(invoice.invoiceDate)
        }
    }
}