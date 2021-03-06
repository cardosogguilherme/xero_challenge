package com.xero.exercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xero.exercise.databinding.ActivityMainBinding
import com.xero.exercise.invoice.model.Invoice
import com.xero.exercise.invoice.view.InvoiceAdapter
import com.xero.exercise.invoice.vm.MainViewModel
import com.xero.exercise.invoice.vm.ViewModelFactory
import data.InvoiceRepository

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels { ViewModelFactory(application) }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setupViews()

        mainViewModel.invoices.observe(this, Observer {
            showInvoices(it)
        })
        mainViewModel.loadViewData()
    }

    /**
     * Prepares recycler view
     */
    private fun setupViews() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.recyclerInvoices.layoutManager = LinearLayoutManager(this)
        binding.recyclerInvoices.adapter = InvoiceAdapter()
    }

    /**
     * Populates recycler view with the invoices
     */
    private fun showInvoices(invoices: List<Invoice>) {
        binding.recyclerInvoices.adapter.takeIf { it is InvoiceAdapter }?.apply {
            (this as InvoiceAdapter).items.clear()
            items.addAll(invoices)
            notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.invoice_list_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_add -> {
            Toast.makeText(this, "Add Action", Toast.LENGTH_SHORT).show()
            true
        }
        R.id.action_delete -> {
            Toast.makeText(this, "Delete Action", Toast.LENGTH_SHORT).show()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}