package com.valimade.myfirstaidkit.medicine.domain.usecase

import com.valimade.myfirstaidkit.medicine.domain.model.CharacteristicItem
import com.valimade.myfirstaidkit.medicine.domain.repository.MedicineRepository

class InsertItemUseCase(private val repository: MedicineRepository){
    suspend operator fun invoke(item: CharacteristicItem) = repository.insertItem(item)
}