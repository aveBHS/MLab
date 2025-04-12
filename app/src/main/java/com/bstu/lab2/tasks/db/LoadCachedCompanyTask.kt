package com.bstu.lab2.network

import android.os.AsyncTask
import com.bstu.lab2.db.CompanyDAO
import com.bstu.lab2.models.Company

class LoadCachedCompanyTask(
    private val companyID: Int,
    private val companyDAO: CompanyDAO,
    private val callback: (Company?) -> Unit
) : AsyncTask<Void, Void, Company?>() {

    override fun doInBackground(vararg params: Void?): Company? {
        return companyDAO.getCompany(companyID)?.toCompany()
    }

    override fun onPostExecute(result: Company?) {
        callback(result)
    }
}