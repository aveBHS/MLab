package com.bstu.lab2.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bstu.lab2.models.Company

@Entity
data class CompanyEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val ticker: String,
    val logoUrl: String,
    val price: Double
)  {
    fun toCompany(): Company {
        return Company(
            id = this.id,
            name = this.name,
            ticker = this.ticker,
            logoUrl = this.logoUrl,
            price = this.price
        )
    }
}