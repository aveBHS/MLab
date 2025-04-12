package com.bstu.lab2.network

import android.os.AsyncTask
import com.bstu.lab2.db.CompanyDAO
import com.bstu.lab2.models.CompanyDetail

class LoadCachedCompanyDetailTask(
    private val companyID: Int,
    private val companyDAO: CompanyDAO,
    private val callback: (CompanyDetail?) -> Unit
) : AsyncTask<Void, Void, CompanyDetail?>() {

    override fun doInBackground(vararg params: Void?): CompanyDetail? {
        return companyDAO.getCompanyDetail(companyID)?.toCompanyDetail(
            companyDAO.getRelatedCompanies(companyID).map { it.toCompany() }
        )
    }

    override fun onPostExecute(result: CompanyDetail?) {
        callback(result)
    }
}