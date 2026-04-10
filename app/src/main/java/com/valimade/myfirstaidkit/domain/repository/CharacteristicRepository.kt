package com.valimade.myfirstaidkit.domain.repository

import com.valimade.myfirstaidkit.domain.model.Characteristic
import com.valimade.myfirstaidkit.domain.model.CharacteristicMedicine

interface CharacteristicRepository {
    suspend fun insertCharacteristic(characteristicMedicine: CharacteristicMedicine)
    suspend fun getAllCharacteristics(characteristic: Characteristic): List<CharacteristicMedicine>
    suspend fun existsCharacteristicByVerificationName(
        characteristic: Characteristic, verificationName: String
    ): Boolean
    suspend fun deleteCharacteristicByVerificationName(characteristicMedicine: CharacteristicMedicine)
}