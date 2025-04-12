package com.bstu.lab2.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RelatedCompanyEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val companyId: Int,
    val relatedCompanyId: Int
)