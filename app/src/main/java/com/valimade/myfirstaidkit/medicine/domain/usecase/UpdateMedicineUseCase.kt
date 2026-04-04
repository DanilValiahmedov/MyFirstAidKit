package com.valimade.myfirstaidkit.medicine.domain.usecase

import com.valimade.myfirstaidkit.medicine.domain.model.CharacteristicItem
import com.valimade.myfirstaidkit.medicine.domain.repository.MedicineRepository

class UpdateMedicineUseCase(private val repository: MedicineRepository){
    suspend operator fun invoke(medicineItem: CharacteristicItem) = repository.updateMedicine(medicineItem)
}