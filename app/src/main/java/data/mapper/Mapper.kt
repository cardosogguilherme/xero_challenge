package data.mapper

import com.xero.exercise.invoice.model.Invoice
import com.xero.exercise.invoice.model.InvoiceLine
import data.EntityInvoice
import data.EntityInvoiceLine
import data.EntityInvoiceWithLines
import java.util.*
import kotlin.math.cos

fun Invoice.mapToDb(): EntityInvoiceWithLines {
    val entityInvoice = EntityInvoice(
        uuid = this.invoiceNumber.toString(),
        date = this.invoiceDate.time
    )
    return EntityInvoiceWithLines(
        entityInvoice,
        this.lineItems.map {
            EntityInvoiceLine(
                description = it.description,
                quantity = it.quantity,
                cost = it.cost,
                invoiceId = entityInvoice.id,
                invoiceUuid = entityInvoice.uuid
            )
        }
    )
}

fun EntityInvoiceWithLines.toViewInvoices() = Invoice(
    invoiceNumber = UUID.fromString(this.invoice.uuid),
    invoiceDate = Date(this.invoice.date),
    lineItems = this.invoiceLines.map {
        InvoiceLine(
            invoiceLineId = it.id,
            description = it.description,
            quantity = it.quantity,
            cost = it.cost
        )
    } .toMutableList()
)