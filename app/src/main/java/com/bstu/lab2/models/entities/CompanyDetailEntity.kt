package com.bstu.lab2.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bstu.lab2.models.Company
import com.bstu.lab2.models.CompanyDetail

@Entity
data class CompanyDetailEntity(
    @PrimaryKey var id: Int,
    var name: String,
    var ticker: String,
    var logoUrl: String,
    var price: Double,
    var marketCap: Long,
    var description: String,
    var sector: String
) {
    fun toCompanyDetail(relatedCompanies: List<Company>): CompanyDetail {
        return CompanyDetail(
            id = this.id,
            name = this.name,
            ticker = this.ticker,
            logoUrl = this.logoUrl,
            price = this.price,
            marketCap = this.marketCap.toULong(),
            description = this.description,
            sector = this.sector,
            relatedCompanies = relatedCompanies
        )
    }
}

