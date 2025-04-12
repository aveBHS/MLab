package com.bstu.lab2.api

import com.bstu.lab2.models.Company
import com.bstu.lab2.models.CompanyDetail
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object CompanyAPI {
    private const val API_URL = "https://mobile-apps-programming-labs.firebaseapp.com/task2/companies/"

    fun getList(): List<Company>? {
        return try {
            val connection = URL(API_URL).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()

            val responseCode = connection.responseCode
            if (responseCode == 200) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = reader.readText()
                reader.close()

                val type = object : TypeToken<List<Company>>() {}.type
                Gson().fromJson<List<Company>>(response, type)
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun get(companyID: Int): CompanyDetail? {
        return try {
            val connection = URL(API_URL + companyID).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()

            val responseCode = connection.responseCode
            if (responseCode == 200) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = reader.readText()
                reader.close()

                val type = object : TypeToken<CompanyDetail>() {}.type
                Gson().fromJson<CompanyDetail>(response, type)
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
