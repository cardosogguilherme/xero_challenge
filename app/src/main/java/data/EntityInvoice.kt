package data

import androidx.room.*
import com.xero.exercise.invoice.model.Invoice
import com.xero.exercise.invoice.model.InvoiceLine
import kotlinx.coroutines.flow.Flow
import java.util.*

@Entity(tableName = "invoice_table")
data class EntityInvoice(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "uuid") val uuid: String,
    @ColumnInfo(name = "date") val date: Long
)

@Entity(tableName = "invoice_line_table")
data class EntityInvoiceLine(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id_invoice_line") val id: Int = 0,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "cost") val cost: Double,
    @ColumnInfo(name = "invoice_id") val invoiceId: Int,
    @ColumnInfo(name = "invoice_uuid") val invoiceUuid: String,
)

data class EntityInvoiceWithLines(
    @Embedded val invoice: EntityInvoice,
    @Relation(
        parentColumn = "uuid",
        entityColumn = "invoice_uuid"
    )
    val invoiceLines: List<EntityInvoiceLine>
)

@Dao
interface InvoiceDao {

    @Transaction
    @Query("SELECT * from invoice_table")
    fun getAllInvoices(): List<EntityInvoiceWithLines>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(invoice: EntityInvoice)

    @Insert
    suspend fun insertInvoiceAndLists(invoice: EntityInvoice, invoiceLines: List<EntityInvoiceLine>)

    @Query("DELETE FROM invoice_table")
    suspend fun deleteAllInvoices()

    @Query("DELETE FROM invoice_table where uuid = :uuid")
    suspend fun deleteInvoice(uuid: String)

    @Query("DELETE FROM invoice_line_table where id_invoice_line = :id")
    suspend fun deleteInvoiceLine(id: Int)

    @Query("DELETE FROM invoice_line_table where invoice_uuid = :uuid")
    suspend fun deleteAllInvoiceLines(uuid: String)
}

