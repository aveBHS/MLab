package com.bstu.lab2.network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import java.io.InputStream
import java.net.URL

class LoadImageTask(
    private val imageURL: String,
    private val imageView: ImageView,
    private val callback: (Bitmap?) -> Unit
) : AsyncTask<Void, Void, Bitmap?>() {

    override fun doInBackground(vararg params: Void?): Bitmap? {
        return try {
            val input: InputStream = URL(imageURL).openStream()
            BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun onPostExecute(result: Bitmap?) {
        imageView.setImageBitmap(result)
        callback(result);
    }
}