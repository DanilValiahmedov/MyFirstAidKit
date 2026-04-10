package com.valimade.myfirstaidkit.domain.usecase.medicine

import com.valimade.myfirstaidkit.domain.repository.MedicineRepository

class DeleteMedicineUseCase(private val repository: MedicineRepository){
    suspend operator fun invoke(id: Int) = repository.deleteMedicineById(id)
}