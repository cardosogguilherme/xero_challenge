package data

import com.xero.exercise.invoice.model.Invoice
import data.mapper.mapToDb

class InvoiceRepository(private val invoiceDao: InvoiceDao) {

    suspend fun insert(invoice: Invoice) {
        val invoiceRelationship = invoice.mapToDb()
        invoiceDao.insertInvoiceAndLists(invoiceRelationship.invoice, invoiceRelationship.invoiceLines)
    }

    suspend fun insertAll(invoices: List<Invoice>) {
        invoices.forEach {
            val invoiceRelationship = it.mapToDb()
            invoiceDao.insertInvoiceAndLists(invoiceRelationship.invoice, invoiceRelationship.invoiceLines)
        }
    }

    suspend fun deleteInvoice(invoice: Invoice) {
        invoiceDao.deleteAllInvoiceLines(invoice.invoiceNumber.toString())
        invoiceDao.deleteInvoice(invoice.invoiceNumber.toString())
    }

    suspend fun deleteLine(invoiceLineId: Int) {
        invoiceDao.deleteInvoiceLine(invoiceLineId)
    }

    suspend fun getAllInvoices() = invoiceDao.getAllInvoices()
}