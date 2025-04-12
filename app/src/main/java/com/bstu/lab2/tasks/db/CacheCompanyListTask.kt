package com.bstu.lab2.network

import android.os.AsyncTask
import com.bstu.lab2.db.CompanyDAO
import com.bstu.lab2.models.Company

class CacheCompanyListTask(
    private val companyDAO: CompanyDAO,
    private val companyList: List<Company>
) : AsyncTask<Void, Void, Unit>() {
    override fun doInBackground(vararg params: Void?): Unit {
        return companyDAO.insertCompanies(companyList.map { it.toEntity() })
    }
}