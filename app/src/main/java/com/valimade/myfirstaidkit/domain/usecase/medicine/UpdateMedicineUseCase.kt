package com.valimade.myfirstaidkit.domain.usecase.medicine

import com.valimade.myfirstaidkit.domain.model.Medicine
import com.valimade.myfirstaidkit.domain.repository.MedicineRepository

class UpdateMedicineUseCase(private val repository: MedicineRepository){
    suspend operator fun invoke(medicine: Medicine) = repository.updateMedicine(medicine)
}