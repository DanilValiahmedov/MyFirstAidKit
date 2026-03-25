package com.valimade.myfirstaidkit.medicine.domain.repository

import com.valimade.myfirstaidkit.medicine.data.db.entities.MedicineData
import com.valimade.myfirstaidkit.medicine.domain.model.Characteristic
import com.valimade.myfirstaidkit.medicine.domain.model.CharacteristicItem

interface MedicineRepository {
    suspend fun insertItem(item: CharacteristicItem)
    suspend fun getAllItem(characteristic: Characteristic): List<CharacteristicItem>
    suspend fun getItemById(characteristic: Characteristic, id: Int): CharacteristicItem?
    suspend fun existsCharacteristicByVerificationName(characteristic: Characteristic, verificationName: String): Boolean
    suspend fun deleteItemById(characteristic: Characteristic, id: Int)
    suspend fun getMedicineByCharacteristic(characteristic: Characteristic, name: String): List<MedicineData>
}