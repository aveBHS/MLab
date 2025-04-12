package com.bstu.lab2.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompanyImageEntity(
    @PrimaryKey val companyId: Int,
    val image: ByteArray
)