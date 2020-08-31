package com.xero.exercise

data class InvoiceLine(
    val invoiceLineId: Int,
    val description: String,
    val quantity: Int,
    val cost: Double
) {
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is InvoiceLine) {
            return false
        }
        return other.invoiceLineId == invoiceLineId
    }

    override fun hashCode(): Int {
        var result = invoiceLineId
        result = 31 * result + description.hashCode()
        result = 31 * result + quantity
        result = 31 * result + cost.hashCode()
        return result
    }
}