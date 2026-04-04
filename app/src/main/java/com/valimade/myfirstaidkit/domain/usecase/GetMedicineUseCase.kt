package com.valimade.myfirstaidkit.domain.usecase

import com.valimade.myfirstaidkit.domain.model.Medicine
import com.valimade.myfirstaidkit.domain.repository.MedicineRepository

class GetMedicineUseCase(private val repository: MedicineRepository){
    suspend operator fun invoke(
        verificationName: String?,
        symptoms: List<String>?,
        diseases: List<String>?,
        forms: List<String>?,
        forWhoms: List<String>?,
        locations: List<String>?,
    ): List<Medicine> {
        return repository.getMedicineByCharacteristic(
            verificationName, symptoms, diseases, forms, forWhoms, locations,
        )
    }
}