/*
 Welcome to the Xero technical exercise!
 ---------------------------------------------------------------------------------
 The test consists of a small invoice application that has a number of issues.

 Your job is to fix them and make sure you can perform the functions in each method below
 and display the list of invoices from getInvoices() inside a RecyclerView.

 Note your first job is to get the solution compiling!

 Rules
 ---------------------------------------------------------------------------------
 * The entire solution must be written in Kotlin
 * You can modify any of the code in this solution, split out classes, add projects etc
 * You can modify Invoice and InvoiceLine, rename and add methods, change property types (hint)
 * Feel free to use any libraries or frameworks you like
 * Feel free to write tests (hint)
 * Show off your skills!

 Good luck :)

 When you have finished the solution please zip it up and email it back to the recruiter or
 developer who sent it to you
 */
package com.xero.exercise.invoice.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xero.exercise.invoice.model.Invoice
import com.xero.exercise.invoice.model.InvoiceLine

class MainViewModel : ViewModel() {

    private val _invoices = MutableLiveData<List<Invoice>>()
    val invoices: LiveData<List<Invoice>> = _invoices

    init {
        createInvoiceWithOneItem()
        createInvoiceWithMultipleItemsAndQuantities()
        removeItem()
        mergeInvoices()
        cloneInvoice()
        orderLineItems()
        previewLineItems()
        removeExtraItems()
        invoiceToString()
    }

    private fun invoiceToString() {

    }

    private fun createInvoiceWithOneItem() {
        val invoice = Invoice()

        invoice.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 1,
                description = "Pizza",
                quantity = 1,
                cost = 9.99
            )
        )
        Log.d("invoice-OneItem", "Total ${invoice.getTotal()}")
    }

    private fun createInvoiceWithMultipleItemsAndQuantities() {
        val invoice = Invoice()

        invoice.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 1,
                description = "Banana",
                quantity = 4,
                cost = 10.21
            )
        )

        invoice.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 2,
                description = "Orange",
                quantity = 1,
                cost = 5.21
            )
        )

        invoice.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 3,
                description = "Pizza",
                quantity = 5,
                cost = 5.21
            )
        )

        Log.d("invoice-MultipleItems", "Total ${invoice.getTotal()}")
    }

    private fun removeItem() {
        val invoice = Invoice()

        invoice.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 1,
                description = "Orange",
                quantity = 1,
                cost = 5.22
            )
        )

        invoice.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 2,
                description = "Banana",
                quantity = 4,
                cost = 10.33
            )
        )

        invoice.removeInvoiceLine(id = 1)
        Log.d("removeItem", "Total ${invoice.getTotal()}")
    }

    private fun mergeInvoices() {
        val invoice1 = Invoice()

        invoice1.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 1,
                description = "Banana",
                quantity = 4,
                cost = 10.33
            )
        )

        val invoice2 = Invoice()

        invoice2.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 2,
                description = "Orange",
                quantity = 1,
                cost = 5.22
            )
        )

        invoice2.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 3,
                description = "Blueberries",
                quantity = 3,
                cost = 6.27
            )
        )

        invoice1.mergeInvoices(sourceInvoice = invoice2)
        Log.d("mergeInvoices", "Total ${invoice1.getTotal()}")
    }

    private fun cloneInvoice() {
        val invoice = Invoice()
        invoice.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 1,
                description = "Apple",
                quantity = 1,
                cost = 6.99
            )
        )

        invoice.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 2,
                description = "Blueberries",
                quantity = 3,
                cost = 6.27
            )
        )

        val clonedInvoice = invoice.clone()
        Log.d("cloneInvoice", "Total ${clonedInvoice.getTotal()}")
    }

    private fun orderLineItems() {
        val invoice = Invoice()

        invoice.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 3,
                description = "Banana",
                quantity = 4,
                cost = 10.21
            )
        )

        invoice.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 2,
                description = "Orange",
                quantity = 1,
                cost = 5.21
            )
        )

        invoice.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 1,
                description = "Pizza",
                quantity = 5,
                cost = 5.21
            )
        )

        invoice.orderLineItems()
        Log.d("orderLineItems", invoice.toString())
    }

    private fun previewLineItems() {
        val invoice = Invoice()

        invoice.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 1,
                description = "Banana",
                quantity = 4,
                cost = 10.21
            )
        )

        invoice.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 2,
                description = "Orange",
                quantity = 1,
                cost = 5.21
            )
        )

        invoice.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 3,
                description = "Pizza",
                quantity = 5,
                cost = 5.21
            )
        )

        val items = invoice.previewLineItems(1)
        Log.d("previewLineItems", items.toString())
    }

    private fun removeExtraItems() {
        var invoice1 = Invoice()

        invoice1.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 1,
                description = "Banana",
                quantity = 4,
                cost = 10.33
            )
        )

        invoice1.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 3,
                description = "Blueberries",
                quantity = 3,
                cost = 6.27
            )
        )

        val invoice2 = Invoice()

        invoice2.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 2,
                description = "Orange",
                quantity = 1,
                cost = 5.22
            )
        )

        invoice2.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 3,
                description = "Blueberries",
                quantity = 3,
                cost = 6.27
            )
        )

        invoice2.removeItems(sourceInvoice = invoice1)
        Log.d("removeExtraItems", "Total ${invoice2.getTotal()}")
    }

    private fun getInvoices(): List<Invoice> {
        val invoice1 = Invoice()

        invoice1.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 1,
                description = "Banana",
                quantity = 4,
                cost = 10.33
            )
        )

        val invoice2 = Invoice()

        invoice2.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 1,
                description = "Orange",
                quantity = 1,
                cost = 5.22
            )
        )

        invoice2.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 2,
                description = "Blueberries",
                quantity = 3,
                cost = 6.27
            )
        )

        val invoice3 = Invoice()

        invoice3.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 1,
                description = "Pizza",
                quantity = 1,
                cost = 9.99
            )
        )

        return listOf(invoice1, invoice2, invoice3)
    }

    fun loadViewData() {
        _invoices.value = getInvoices()
    }

    internal class Companion {

        fun InvoiceToString() {
            val invoice = Invoice()
            invoice.addInvoiceLine(
                InvoiceLine(
                    invoiceLineId = 1,
                    description = "Apple",
                    quantity = 1,
                    cost = 6.99
                )
            )

            Log.d("MainViewModel", invoice.toString())
        }
    }
}