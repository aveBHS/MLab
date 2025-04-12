package com.bstu.lab2.models

import com.bstu.lab2.models.entities.CompanyEntity

data class Company(
    val id: Int,
    val name: String,
    val ticker: String,
    val logoUrl: String,
    val price: Double
) {
    fun toEntity(): CompanyEntity {
        return CompanyEntity(
            id = this.id,
            name = this.name,
            ticker = this.ticker,
            logoUrl = this.logoUrl,
            price = this.price
        )
    }
}

