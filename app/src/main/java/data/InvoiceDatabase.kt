package data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EntityInvoice::class, EntityInvoiceLine::class], exportSchema = false, version = 1)
abstract class InvoiceDatabase: RoomDatabase() {

    abstract fun invoiceDao(): InvoiceDao

    companion object {
        @Volatile
        private var INSTANCE: InvoiceDatabase? = null
        private const val DB_NAME = "exchange_rate_db"


        fun getDatabase(context: Context): InvoiceDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    val instance  = Room.databaseBuilder(context.applicationContext, InvoiceDatabase::class.java, DB_NAME).build()
                    INSTANCE = instance
                }
            }
            return INSTANCE!!
        }

        fun destroyDatabase() {
            INSTANCE = null
        }
    }
}