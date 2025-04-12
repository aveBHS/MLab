package com.bstu.lab2.network

import android.os.AsyncTask
import com.bstu.lab2.db.CompanyDAO
import com.bstu.lab2.models.entities.RelatedCompanyEntity
import com.bstu.lab2.models.CompanyDetail

class CacheCompanyDetailTask(
    private val companyDAO: CompanyDAO,
    private val companyDetail: CompanyDetail
) : AsyncTask<Void, Void, Unit>() {
    override fun doInBackground(vararg params: Void?): Unit {
        companyDAO.insertCompanyDetail(companyDetail.toEntity())
        return companyDAO.insertRelatedCompanies(companyDetail.relatedCompanies.map {
            RelatedCompanyEntity(companyId = companyDetail.id.toInt(), relatedCompanyId = it.id.toInt())
        })
    }
}