package com.bstu.lab2

import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
import com.bstu.lab2.network.CacheCompanyListTask
import com.bstu.lab2.network.LoadCachedCompanyListTask
import com.bstu.lab2.network.LoadCompanyListTask

class MainActivity : AppCompatActivity() {

    private lateinit var companyDAO: CompanyDAO;
    private lateinit var recyclerView: RecyclerView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.ticker_list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val db = Room.databaseBuilder(applicationContext, DB::class.java, "companies_db")
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .fallbackToDestructiveMigration()
            .build()
        companyDAO = db.companyDAO()

        LoadCompanyListTask { companies ->
            if (companies != null) {
                displayCompanies(companies);
                CacheCompanyListTask (companyDAO, companies).execute()
            } else {
                Toast.makeText(this, "Нет доступа к сети, загружены данные из кеша", Toast.LENGTH_SHORT).show()
                LoadCachedCompanyListTask (companyDAO) { companies ->
                    companies?.let { displayCompanies(companies) }
                }.execute()
            }
        }.execute()
    }

    private fun displayCompanies(companies: List<Company>) {
        recyclerView.adapter = CompanyAdapter(companyDAO, companies) { position ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("company_id", companies[position].id)
            startActivity(intent)
        }
    }
}