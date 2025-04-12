package com.bstu.lab2.network

import android.os.AsyncTask
import com.bstu.lab2.api.CompanyAPI
import com.bstu.lab2.models.CompanyDetail

class LoadCompanyTask(
    private val companyID: Int,
    private val callback: (CompanyDetail?) -> Unit
) : AsyncTask<Void, Void, CompanyDetail?>() {

    override fun doInBackground(vararg params: Void?): CompanyDetail? {
        return CompanyAPI.get(companyID)
    }

    override fun onPostExecute(result: CompanyDetail?) {
        callback(result)
    }
}