package com.valimade.myfirstaidkit.data.repository

import android.content.Context
import androidx.room.Room
import com.valimade.myfirstaidkit.data.db.database.MedicineDatabase
import com.valimade.myfirstaidkit.data.mapper.MedicineDataMapper
import com.valimade.myfirstaidkit.domain.model.Medicine
import com.valimade.myfirstaidkit.domain.repository.MedicineRepository

class MedicineRepositoryImpl(
    private val context: Context,
    private val mapper: MedicineDataMapper,
): MedicineRepository {

    private val database: MedicineDatabase by lazy {
        Room.databaseBuilder(
            context,
            MedicineDatabase::class.java,
            "app-database"
        ).build()
    }
    private val dao by lazy { database.dao() }

    override suspend fun insertMedicine(medicine: Medicine) {
        val medicineData = mapper.fromDomainToData(medicine)
        dao.insertMedicine(medicineData)
    }

    override suspend fun getAllMedicine(): List<Medicine> {
        return dao.getAllMedicine().map {
            mapper.fromDataToDomain(it)
        }
    }

    override suspend fun getMedicineById(id: Int): Medicine? {
        return dao.getMedicineById(id)?.let {
            mapper.fromDataToDomain(it)
        }
    }

    override suspend fun updateMedicine(medicine: Medicine) {
        val medicineData = mapper.fromDomainToData(medicine)
        dao.updateMedicine(medicineData)
    }

    override suspend fun deleteMedicineById(id: Int) {
        dao.deleteMedicineById(id)
    }

    override suspend fun searchMedicine(
        verificationName: String?,
        symptoms: List<String>?,
        diseases: List<String>?,
        forms: List<String>?,
        forWhoms: List<String>?,
        locations: List<String>?,
    ): List<Medicine> {
        val symptomsQuery = symptoms.toFtsQuery()
        val diseasesQuery = diseases.toFtsQuery()
        val formsQuery = forms.toFtsQuery()
        val forWhomsQuery = forWhoms.toFtsQuery()
        val locationsQuery = locations.toFtsQuery()

        val medicineListData = dao.searchMedicine(
            verificationName = verificationName,
            symptoms = symptomsQuery,
            diseases = diseasesQuery,
            forms = formsQuery,
            forWhoms = forWhomsQuery,
            locations = locationsQuery,
        )
        return medicineListData.map{
            mapper.fromDataToDomain(it)
        }
    }

}

fun List<String>?.toFtsQuery(): String? {
    return this
        ?.filter { it.isNotBlank() }
        ?.joinToString(" ") { "+$it" }
        ?.takeIf { it.isNotBlank() }
}