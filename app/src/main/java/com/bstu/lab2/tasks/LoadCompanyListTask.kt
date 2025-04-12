package com.bstu.lab2.network

import android.os.AsyncTask
import com.bstu.lab2.api.CompanyAPI
import com.bstu.lab2.models.Company

class LoadCompanyListTask(
    private val callback: (List<Company>?) -> Unit
) : AsyncTask<Void, Void, List<Company>?>() {

    override fun doInBackground(vararg params: Void?): List<Company>? {
        return CompanyAPI.getList()
    }

    override fun onPostExecute(result: List<Company>?) {
        callback(result)
    }
}