package com.bstu.lab2.models

import com.bstu.lab2.models.entities.CompanyDetailEntity

data class CompanyDetail(
    val id: Int,
    val name: String,
    val ticker: String,
    val logoUrl: String,
    val price: Double,
    val marketCap: ULong,
    val description: String,
    val sector: String,
    val relatedCompanies: List<Company>
) {
    fun toEntity(): CompanyDetailEntity {
        return CompanyDetailEntity(
            id = this.id,
            name = this.name,
            ticker = this.ticker,
            logoUrl = this.logoUrl,
            price = this.price,
            marketCap = this.marketCap.toLong(),
            description = this.description,
            sector = this.sector
        )
    }
}

