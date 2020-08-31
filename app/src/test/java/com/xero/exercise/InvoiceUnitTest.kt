package com.xero.exercise

import org.junit.Assert
import org.junit.Test
import java.util.*

class InvoiceUnitTest {

    @Test
    fun emptyInvoice() {
        val date = Date()
        val uuid = UUID.randomUUID()
        val emptyInvoice = Invoice(
            invoiceNumber = uuid,
            invoiceDate = date
        )

        assert(emptyInvoice.invoiceDate == date)
        assert(!emptyInvoice.invoiceDate.before(date))
        assert(!emptyInvoice.invoiceDate.after(date))

        assert(emptyInvoice.invoiceNumber == uuid)

        assert(emptyInvoice.lineItems.isEmpty())
    }

    @Test
    fun equalInvoice() {
        val date = Date()
        val uuid = UUID.randomUUID()

        val firstInvoice = Invoice(
            invoiceNumber = uuid,
            invoiceDate = date
        )

        val secondInvoice = Invoice(
            invoiceNumber = UUID.randomUUID(),
            invoiceDate = date
        )

        val sameInvoice = Invoice(
            invoiceNumber = uuid,
            invoiceDate = Date()
        )

        Assert.assertNotEquals(firstInvoice, secondInvoice)
        Assert.assertNotEquals(sameInvoice, secondInvoice)

        Assert.assertEquals(firstInvoice, sameInvoice)
        Assert.assertNotEquals(firstInvoice.invoiceDate, sameInvoice.invoiceDate)
    }
}