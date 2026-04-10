package com.valimade.myfirstaidkit.domain.usecase.medicine

import com.valimade.myfirstaidkit.domain.model.Medicine
import com.valimade.myfirstaidkit.domain.repository.MedicineRepository

class SearchMedicineUseCase(private val repository: MedicineRepository){
    suspend operator fun invoke(
        verificationName: String?, symptoms: List<String>?, diseases: List<String>?,
        forms: List<String>?, forWhoms: List<String>?, locations: List<String>?
    ): List<Medicine> {

        return repository.searchMedicine(
            verificationName, symptoms, diseases, forms, forWhoms, locations
        )

    }
}