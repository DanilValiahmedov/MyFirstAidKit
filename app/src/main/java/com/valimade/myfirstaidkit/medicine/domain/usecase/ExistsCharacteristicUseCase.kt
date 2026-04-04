package com.valimade.myfirstaidkit.medicine.domain.usecase

import com.valimade.myfirstaidkit.medicine.domain.model.Characteristic
import com.valimade.myfirstaidkit.medicine.domain.repository.MedicineRepository

class ExistsCharacteristicUseCase(private val repository: MedicineRepository){
    suspend operator fun invoke(characteristic: Characteristic, verificationName: String): Boolean {
        return repository.existsCharacteristicByVerificationName(characteristic, verificationName)
    }
}