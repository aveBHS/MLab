package com.bstu.lab2.network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import com.bstu.lab2.db.CompanyDAO

class LoadCachedImageTask(
    private val companyID: Int,
    private val companyDAO: CompanyDAO,
    private val imageView: ImageView,
    private val callback: (Bitmap?) -> Unit
) : AsyncTask<Void, Void, Bitmap?>() {

    override fun doInBackground(vararg params: Void?): Bitmap? {
        val cached = companyDAO.getImage(companyID)
        if (cached != null) return BitmapFactory.decodeByteArray(cached, 0, cached.size)
        return null;
    }

    override fun onPostExecute(result: Bitmap?) {
        if (result !== null) imageView.setImageBitmap(result)
        else callback(result)
    }
}