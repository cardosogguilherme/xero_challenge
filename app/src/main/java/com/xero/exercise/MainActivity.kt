package com.xero.exercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

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
        TODO("Not implemented")
    }

    /**
     * Populates recycler view with the invoices
     */
    private fun showInvoices(invoices: List<Invoice>) {
        TODO("Not implemented")
    }
}