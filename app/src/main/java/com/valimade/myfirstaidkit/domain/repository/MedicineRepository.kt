package com.valimade.myfirstaidkit.domain.repository

import com.valimade.myfirstaidkit.domain.model.Characteristic
import com.valimade.myfirstaidkit.domain.model.CharacteristicItem
import com.valimade.myfirstaidkit.domain.model.Medicine

interface MedicineRepository {
    suspend fun insertItem(item: CharacteristicItem)
    suspend fun getAllItem(characteristic: Characteristic): List<CharacteristicItem>
    suspend fun getItemById(characteristic: Characteristic, id: Int): CharacteristicItem?
    suspend fun existsCharacteristicByVerificationName(
        characteristic: Characteristic, verificationName: String
    ): Boolean
    suspend fun updateMedicine(medicineItem: CharacteristicItem)
    suspend fun deleteItemByVerificationName(characteristic: Characteristic, verificationName: String)
    suspend fun getMedicineByCharacteristic(
        verificationName: String?,
        symptoms: List<String>?,
        diseases: List<String>?,
        forms: List<String>?,
        forWhoms: List<String>?,
        locations: List<String>?,
    ): List<Medicine>
}