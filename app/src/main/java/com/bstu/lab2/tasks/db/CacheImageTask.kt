package com.bstu.lab2.network

import android.graphics.Bitmap
import android.os.AsyncTask
import com.bstu.lab2.db.CompanyDAO
import com.bstu.lab2.models.entities.CompanyImageEntity
import java.io.ByteArrayOutputStream

class CacheImageTask(
    private val companyID: Int,
    private val bitmap: Bitmap,
    private val companyDAO: CompanyDAO,
) : AsyncTask<Void, Void, Unit>() {
    override fun doInBackground(vararg params: Void?): Unit {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        companyDAO.insertImage(CompanyImageEntity(companyID, stream.toByteArray()))
    }
}