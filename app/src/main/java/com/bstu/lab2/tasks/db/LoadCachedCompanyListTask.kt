package com.bstu.lab2.network

import android.os.AsyncTask
import com.bstu.lab2.db.CompanyDAO
import com.bstu.lab2.models.Company

class LoadCachedCompanyListTask(
    private val companyDAO: CompanyDAO,
    private val callback: (List<Company>?) -> Unit
) : AsyncTask<Void, Void, List<Company>?>() {

    override fun doInBackground(vararg params: Void?): List<Company>? {
        return companyDAO.getCompanies().map { it.toCompany() }
    }

    override fun onPostExecute(result: List<Company>?) {
        callback(result)
    }
}