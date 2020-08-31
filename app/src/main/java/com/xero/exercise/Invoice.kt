package com.xero.exercise

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

data class Invoice(var invoiceNumber: Int,
                   var invoiceDate: Date = Date(),
                   var lineItems: MutableList<InvoiceLine> = mutableListOf()) {

    fun addInvoiceLine(line: InvoiceLine) {
        lineItems.add(line)
    }

    fun removeInvoiceLine(id: Int) {
        //In order to prevent a crash due non-existent id
        val index = lineItems.indexOfFirst { it.invoiceLineId == id }
        if (index > -1) {
            lineItems.removeAt(index)
        }
    }

    /// GetTotal should return the sum of (Cost * Quantity) for each line item
    fun getTotal(): Double = lineItems.sumByDouble { it.quantity * it.cost }

    /// MergeInvoices appends the items from the sourceInvoice to the current invoice
    fun mergeInvoices(sourceInvoice: Invoice) {
        sourceInvoice.lineItems.forEach { addInvoiceLine(it) }
    }

    /// Creates a deep clone of the current invoice (all fields and properties)
    //Using copy() creates a shallow copy, which means lineItems would be "shared"
    fun clone(): Invoice = Invoice(
        invoiceNumber = invoiceNumber,
        invoiceDate = invoiceDate,
        lineItems = lineItems
    )

    /// order the lineItems by Id
    fun orderLineItems() {
        lineItems.sortBy { it.invoiceLineId }
    }

    /// returns the number of the line items specified in the variable `max`
    //Since we need to return 'max' items, we need to make sure it's a value > 0
    fun previewLineItems(max: Int): List<InvoiceLine> =
        if (max >= 1) lineItems.subList(0, max + 1) else listOf()

    /// remove the line items in the current invoice that are also in the sourceInvoice
    fun removeItems(sourceInvoice: Invoice) = this.lineItems.minus(sourceInvoice)

    /// Outputs string containing the following (replace [] with actual values):
    /// Invoice Number: [InvoiceNumber], InvoiceDate: [DD/MM/YYYY], LineItemCount: [Number of items in LineItems]
    @SuppressLint("SimpleDateFormat")
    override fun toString(): String
            = "${this.invoiceNumber}, ${SimpleDateFormat("DD/MM/YYYY").format(this.invoiceDate)}, LineItemCount: ${this.lineItems.size}"
}