package com.xero.exercise

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotSame
import org.junit.Assert
import org.junit.Test
import java.util.*

class InvoiceUnitTest {

    //Checking the basic creation of invoices
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

    //Checking the equality check of 2 invoices
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
//        Assert.assertNotEquals(firstInvoice.invoiceDate.time, sameInvoice.invoiceDate.time)
    }

    //Checking total for single lines
    @Test
    fun invoiceTotalSingleItem() {
        val firstInvoice = Invoice()
        firstInvoice.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 1,
                description = "",
                quantity = 1,
                cost = 5.0
        ))

        assertEquals(5.0, firstInvoice.getTotal())
    }

    //Checking total for invoices with items added later
    @Test
    fun invoiceTotalAddedItemsLater() {
        val firstInvoice = Invoice()
        firstInvoice.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 1,
                description = "",
                quantity = 1,
                cost = 5.0
            ))

        assertEquals(5.0, firstInvoice.getTotal())

        firstInvoice.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 2,
                description = "",
                quantity = 3,
                cost = 5.0
            ))

        assertEquals(20.0, firstInvoice.getTotal())

        firstInvoice.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 3,
                description = "",
                quantity = 0,
                cost = 5.0
            ))

        assertEquals(20.0, firstInvoice.getTotal())
    }

    //Checking total for invoices with items added later, then have some removed
    @Test
    fun invoiceTotalAddingLinesThenRemoving() {
        val firstInvoice = Invoice()
        firstInvoice.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 1,
                description = "",
                quantity = 1,
                cost = 5.0
            ))

        assertEquals(5.0, firstInvoice.getTotal())

        firstInvoice.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 2,
                description = "",
                quantity = 3,
                cost = 5.0
            ))

        assertEquals(20.0, firstInvoice.getTotal())

        firstInvoice.addInvoiceLine(
            InvoiceLine(
                invoiceLineId = 3,
                description = "",
                quantity = 0,
                cost = 5.0
            ))

        assertEquals(20.0, firstInvoice.getTotal())

        firstInvoice.removeInvoiceLine(2)

        assertEquals(5.0, firstInvoice.getTotal())
    }

    //Checking the merge of invoices
    @Test
    fun mergeInvoices() {
        val firstInvoice = Invoice()
        val secondInvoice = Invoice()

        firstInvoice.addInvoiceLine(InvoiceLine(
                invoiceLineId = 1,
                description = "",
                quantity = 1,
                cost = 5.0
            )
        )

        secondInvoice.addInvoiceLine(InvoiceLine(
                invoiceLineId = 2,
                description = "",
                quantity = 2,
                cost = 5.0
            )
        )

        firstInvoice.mergeInvoices(secondInvoice)
        assertEquals(15.0, firstInvoice.getTotal())
        assertEquals(2, firstInvoice.lineItems.size)

        //We need to check the other way around as well
        secondInvoice.mergeInvoices(Invoice().also { it.lineItems.add(
            InvoiceLine(
                invoiceLineId = 1,
                description = "",
                quantity = 1,
                cost = 5.0
            )
        ) })

        assertEquals(15.0, secondInvoice.getTotal())
        assertEquals(2, secondInvoice.lineItems.size)
    }

    @Test
    fun deepClone() {
        val firstInvoice = Invoice()
        val cloneInvoice = firstInvoice.clone() //UUID should be the same

        assertEquals(firstInvoice.invoiceNumber, cloneInvoice.invoiceNumber)
        assertEquals(firstInvoice, cloneInvoice)

        //Proving isn't a shallow copy, we would add an invoice line to only one

        firstInvoice.addInvoiceLine(InvoiceLine(
            invoiceLineId = 1,
            description = "",
            quantity = 1,
            cost = 5.0
        ))

        assertNotSame(firstInvoice.lineItems, cloneInvoice.lineItems)
        assertNotSame(firstInvoice.lineItems.size, cloneInvoice.lineItems.size)
    }

    @Test
    fun orderItems() {
        val firstInvoice = Invoice()

        firstInvoice.addInvoiceLine(InvoiceLine(
            invoiceLineId = 2,
            description = "",
            quantity = 2,
            cost = 10.0
        ))

        firstInvoice.addInvoiceLine(InvoiceLine(
            invoiceLineId = 1,
            description = "",
            quantity = 1,
            cost = 5.0
        ))

        firstInvoice.orderLineItems()

        assertEquals(2, firstInvoice.lineItems.size) //We still have 2 items
        assertEquals(1, firstInvoice.lineItems.first().invoiceLineId)
    }

    @Test
    fun previewItemsLessThanSize() {
        val firstInvoice = Invoice()
        val previewItems = firstInvoice.previewLineItems(1)

        assertEquals(0, previewItems.size)
    }

    @Test
    fun previewItemsMoreThanSize() {
        val firstInvoice = Invoice()

        firstInvoice.addInvoiceLine(InvoiceLine(
            invoiceLineId = 2,
            description = "",
            quantity = 2,
            cost = 10.0
        ))

        firstInvoice.addInvoiceLine(InvoiceLine(
            invoiceLineId = 1,
            description = "",
            quantity = 1,
            cost = 5.0
        ))

        val previewItems = firstInvoice.previewLineItems(3)
        assertEquals(2, previewItems.size)
    }

    @Test
    fun previewItemsLessThanSizeBiggerThanZero() {
        val firstInvoice = Invoice()

        firstInvoice.addInvoiceLine(InvoiceLine(
            invoiceLineId = 1,
            description = "",
            quantity = 2,
            cost = 10.0
        ))

        firstInvoice.addInvoiceLine(InvoiceLine(
            invoiceLineId = 2,
            description = "",
            quantity = 1,
            cost = 5.0
        ))

        firstInvoice.addInvoiceLine(InvoiceLine(
            invoiceLineId = 3,
            description = "",
            quantity = 1,
            cost = 5.0
        ))

        val previewItems = firstInvoice.previewLineItems(2)
        assertEquals(2, previewItems.size)
        assertEquals(1, previewItems.first().invoiceLineId)
        assertEquals(2, previewItems[1].invoiceLineId)
    }

    @Test
    fun notEqualDifferentType() {
        assertNotSame(Invoice(), String())
    }

    @Test
    fun notEqualNull() {
        assertNotSame(Invoice(), null)
    }

    @Test
    fun invoiceLinesEqual() {
        val invoiceLine = InvoiceLine(
            invoiceLineId = 1,
            description = "",
            quantity = 1,
            cost = 5.0
        )

        val invoiceLineSameId = InvoiceLine(
            invoiceLineId = 1,
            description = "",
            quantity = 2,
            cost = 3.0
        )

        val clone = invoiceLine.copy()

        assertEquals(invoiceLine, invoiceLineSameId)
        assertEquals(invoiceLine, clone)
    }

    @Test
    fun invoiceLinesNotEqualDifferentId() {
        val invoiceLine = InvoiceLine(
            invoiceLineId = 1,
            description = "1",
            quantity = 1,
            cost = 5.0
        )

        val invoiceLineDiffId = InvoiceLine(
            invoiceLineId = 3,
            description = "3",
            quantity = 1,
            cost = 5.0
        )

        assertNotSame(invoiceLine, invoiceLineDiffId)
    }

    @Test
    fun invoiceLinesNotEqualDifferentType() {
        assertNotSame(InvoiceLine(
            invoiceLineId = 1,
            description = "",
            quantity = 1,
            cost = 5.0
        ), String())
    }

    @Test
    fun invoiceLinesNotEqualNull() {
        assertNotSame(InvoiceLine(
            invoiceLineId = 1,
            description = "",
            quantity = 1,
            cost = 5.0
        ), null)
    }
}