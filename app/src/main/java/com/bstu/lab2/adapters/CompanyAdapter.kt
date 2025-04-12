package com.bstu.lab2.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bstu.lab2.R
import com.bstu.lab2.db.CompanyDAO
import com.bstu.lab2.models.Company
import com.bstu.lab2.network.CacheImageTask
import com.bstu.lab2.network.LoadCachedImageTask
import com.bstu.lab2.network.LoadImageTask

class CompanyAdapter(
    private val companyDAO: CompanyDAO,
    private val companies: List<Company>,
    private val onClick: (Int) -> Unit
) :
    RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder>() {

    private val logTag: String = "LAB2_CompanyAdapter"
    private var onCreateViewHolderCount = 0;
    private var onBindViewHolder = 0;
    private var getItemCount = 0;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        Log.w(logTag, "[${++onCreateViewHolderCount}] onCreateViewHolder() called -> viewType = $viewType")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return CompanyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        Log.w(logTag, "[${++onBindViewHolder}] onBindViewHolder() called -> position = $position")
        val company = this.companies[position]
        holder.bind(company)
        holder.itemView.setOnClickListener { this.onClick(position) }
    }

    override fun getItemCount(): Int {
        Log.w(logTag, "[${++getItemCount}] getItemCount() called")
        return this.companies.size
    }

    inner class CompanyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val logoImageView: ImageView = itemView.findViewById(R.id.ticker_logo)
        private val nameTextView: TextView = itemView.findViewById(R.id.ticker_name)
        private val tickerTextView: TextView = itemView.findViewById(R.id.ticker_ticker)
        private val priceTextView: TextView = itemView.findViewById(R.id.ticker_price)

        fun bind(company: Company) {
            LoadCachedImageTask (company.id, companyDAO, logoImageView) { image ->
                if (image === null) LoadImageTask(company.logoUrl, logoImageView) { image ->
                    if (image !== null) CacheImageTask(company.id, image, companyDAO).execute()
                }.execute()
            }.execute()
            nameTextView.text = company.name
            tickerTextView.text = company.ticker
            priceTextView.text = "${company.price} ₽"
        }
    }
}

// Количество вызовов функций выше
// Переиспользование элементов