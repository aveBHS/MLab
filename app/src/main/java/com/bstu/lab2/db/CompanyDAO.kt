package com.bstu.lab2.db

import androidx.room.*
import com.bstu.lab2.models.entities.CompanyDetailEntity
import com.bstu.lab2.models.entities.CompanyEntity
import com.bstu.lab2.models.entities.CompanyImageEntity
import com.bstu.lab2.models.entities.RelatedCompanyEntity

@Dao
interface CompanyDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCompanies(companies: List<CompanyEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCompanyDetail(detail: CompanyDetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRelatedCompany(relatedCompany: RelatedCompanyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRelatedCompanies(relatedCompanies: List<RelatedCompanyEntity>)

    @Query("SELECT * FROM CompanyEntity")
    fun getCompanies(): List<CompanyEntity>

    @Query("SELECT * FROM CompanyEntity WHERE id IN (SELECT relatedCompanyId FROM RelatedCompanyEntity WHERE companyId = :id)")
    fun getRelatedCompanies(id: Int): List<CompanyEntity>

    @Query("SELECT * FROM CompanyEntity WHERE id = :id")
    fun getCompany(id: Int): CompanyEntity?

    @Query("SELECT * FROM CompanyDetailEntity WHERE id = :id")
    fun getCompanyDetail(id: Int): CompanyDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImage(image: CompanyImageEntity)

    @Query("SELECT image FROM CompanyImageEntity WHERE companyId = :companyId")
    fun getImage(companyId: Int): ByteArray?

}