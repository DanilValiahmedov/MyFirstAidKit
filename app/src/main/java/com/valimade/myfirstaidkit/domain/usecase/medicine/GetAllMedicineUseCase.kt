package com.valimade.myfirstaidkit.domain.usecase.medicine

import com.valimade.myfirstaidkit.domain.model.Medicine
import com.valimade.myfirstaidkit.domain.repository.MedicineRepository

class GetAllMedicineUseCase(private val repository: MedicineRepository){
    suspend operator fun invoke(): List<Medicine> = repository.getAllMedicine()
}