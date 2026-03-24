package com.valimade.myfirstaidkit.medicine.domain.repository

import com.valimade.myfirstaidkit.medicine.data.db.entities.MedicineData
import com.valimade.myfirstaidkit.medicine.domain.model.Characteristic
import com.valimade.myfirstaidkit.medicine.domain.model.CharacteristicItem

interface MedicineRepository {
    suspend fun insertCharacteristic(item: CharacteristicItem)
    suspend fun getAllCharacteristic(characteristic: Characteristic): List<CharacteristicItem>
    suspend fun getCharacteristicById(characteristic: Characteristic, id: Int): CharacteristicItem?
    suspend fun existsCharacteristicByVerificationName(characteristic: Characteristic, verificationName: String): Boolean

    suspend fun insertMedicine(medicine: MedicineData)
    suspend fun getMedicineById(id: Int): MedicineData?
    suspend fun getByVerificationName(verificationName: String): MedicineData?
    suspend fun getBySymptom(symptom: String): List<MedicineData>
    suspend fun getByDisease(disease: String): List<MedicineData>
    suspend fun getByForm(form: String): List<MedicineData>
    suspend fun getByWhom(whom: String): List<MedicineData>
}