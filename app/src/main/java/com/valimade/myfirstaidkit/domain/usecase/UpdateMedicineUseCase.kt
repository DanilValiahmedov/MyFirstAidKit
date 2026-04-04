package com.valimade.myfirstaidkit.domain.usecase

import com.valimade.myfirstaidkit.domain.model.CharacteristicItem
import com.valimade.myfirstaidkit.domain.repository.MedicineRepository

class UpdateMedicineUseCase(private val repository: MedicineRepository){
    suspend operator fun invoke(medicineItem: CharacteristicItem) = repository.updateMedicine(medicineItem)
}