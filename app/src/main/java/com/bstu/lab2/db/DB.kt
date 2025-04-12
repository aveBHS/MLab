package com.bstu.lab2.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bstu.lab2.models.entities.CompanyEntity
import com.bstu.lab2.models.entities.CompanyDetailEntity
import com.bstu.lab2.models.entities.CompanyImageEntity
import com.bstu.lab2.models.entities.RelatedCompanyEntity

@Database(entities = [
    CompanyEntity::class, CompanyDetailEntity::class,
    RelatedCompanyEntity::class, CompanyImageEntity::class
], version = 1)

abstract class DB : RoomDatabase() {
    abstract fun companyDAO(): CompanyDAO
}