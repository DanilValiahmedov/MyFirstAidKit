package com.valimade.myfirstaidkit.domain.repository

import com.valimade.myfirstaidkit.domain.model.Medicine

interface MedicineRepository {
    suspend fun insertMedicine(medicine: Medicine)
    suspend fun getAllMedicine(): List<Medicine>
    suspend fun getMedicineById(id: Int): Medicine?
    suspend fun updateMedicine(medicine: Medicine)
    suspend fun deleteMedicineById(id: Int)
    suspend fun searchMedicine(
        verificationName: String?,
        symptoms: List<String>?,
        diseases: List<String>?,
        forms: List<String>?,
        forWhoms: List<String>?,
        locations: List<String>?,
    ): List<Medicine>
}