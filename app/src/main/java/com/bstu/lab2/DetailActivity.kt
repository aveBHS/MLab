package com.bstu.lab2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bstu.lab2.adapters.CompanyAdapter
import com.bstu.lab2.db.CompanyDAO
import com.bstu.lab2.db.DB
import com.bstu.lab2.models.Company
import com.bstu.lab2.models.CompanyDetail
import com.bstu.lab2.network.CacheCompanyDetailTask
import com.bstu.lab2.network.CacheCompanyListTask
import com.bstu.lab2.network.CacheImageTask
import com.bstu.lab2.network.LoadCachedCompanyDetailTask
import com.bstu.lab2.network.LoadCachedCompanyListTask
import com.bstu.lab2.network.LoadCachedCompanyTask
import com.bstu.lab2.network.LoadCachedImageTask
import com.bstu.lab2.network.LoadCompanyTask
import com.bstu.lab2.network.LoadImageTask

class DetailActivity : AppCompatActivity() {

    private lateinit var companyDAO: CompanyDAO;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.detail_activity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = Room.databaseBuilder(applicationContext, DB::class.java, "companies_db")
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .fallbackToDestructiveMigration()
            .build()
        companyDAO = db.companyDAO()

        val companyID = intent.getSerializableExtra("company_id") as Int
        LoadCompanyTask(companyID) { company ->
            if (company != null) {
                displayCompany(company);
                CacheCompanyDetailTask (companyDAO, company).execute()
            } else {
                LoadCachedCompanyDetailTask (companyID, companyDAO) { company ->
                    if (company !== null) {
                        displayCompany(company)
                        Toast.makeText(this, "Нет доступа к сети, загружены данные из кеша", Toast.LENGTH_SHORT).show()
                    } else {
                        LoadCachedCompanyTask (companyID, companyDAO) { company ->
                            if (company !== null) {
                                displayCroppedCompany(company);
                                Toast.makeText(this, "Нет доступа к сети, загружены усеченные данные из кеша", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, "Нет доступа к сети, загрузка невозможна", Toast.LENGTH_SHORT).show()
                            }
                        }.execute()
                    }
                }.execute()
            }
        }.execute()
    }

    private fun displayCompany(company: CompanyDetail) {
        loadImage(company.id, company.logoUrl)
        findViewById<Toolbar>(R.id.header).title = company.name
        findViewById<TextView>(R.id.name).text = company.name
        findViewById<TextView>(R.id.description).text = company.description
        findViewById<TextView>(R.id.ticker).text = company.ticker
        findViewById<TextView>(R.id.market_cap).text = this.formatMarketCap(company.marketCap)
        findViewById<TextView>(R.id.sector).text = company.sector
        findViewById<TextView>(R.id.price).text = String.format("%s ₽", company.price)

        if (company.relatedCompanies.isNotEmpty()) {
            val recyclerView: RecyclerView = findViewById(R.id.ticker_list)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = CompanyAdapter(companyDAO, company.relatedCompanies) { position: Int ->
                val intent = Intent(this@DetailActivity, DetailActivity::class.java)
                intent.putExtra("company_id", company.relatedCompanies[position].id)
                startActivity(intent)
            }
        } else {
            val view = findViewById<LinearLayout>(R.id.similar_tickers)
            (view.parent as LinearLayout).removeView(view)
        }
    }

    private fun displayCroppedCompany(company: Company) {
        loadImage(company.id, company.logoUrl)
        findViewById<Toolbar>(R.id.header).title = company.name
        findViewById<TextView>(R.id.name).text = company.name
        findViewById<TextView>(R.id.ticker).text = company.ticker
        findViewById<TextView>(R.id.price).text = String.format("%s ₽", company.price)

        val aboutView = findViewById<LinearLayout>(R.id.about)
        (aboutView.parent as LinearLayout).removeView(aboutView)

        val similarTicketsView = findViewById<LinearLayout>(R.id.similar_tickers)
        (similarTicketsView.parent as LinearLayout).removeView(similarTicketsView)
    }

    private fun formatMarketCap(value: ULong): String {
        return when {
            value >= 1_000_000_000u -> String.format("%s млрд. ₽", value / 1_000_000_000u)
            value >= 1_000_000u -> String.format("%s млн. ₽", value / 1_000_000u)
            else -> String.format("%s ₽", value)
        }
    }

    private fun loadImage(companyID: Int, logoURL: String) {
        val logoImageView = findViewById<ImageView>(R.id.logo);
        LoadCachedImageTask (companyID, companyDAO, logoImageView) { image ->
            if (image === null) LoadImageTask(logoURL, logoImageView) { image ->
                if (image !== null) CacheImageTask(companyID, image, companyDAO).execute()
            }.execute()
        }.execute()
    }
}