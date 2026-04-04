package com.valimade.myfirstaidkit.domain.usecase

import com.valimade.myfirstaidkit.domain.model.CharacteristicItem
import com.valimade.myfirstaidkit.domain.repository.MedicineRepository

class InsertItemUseCase(private val repository: MedicineRepository){
    suspend operator fun invoke(item: CharacteristicItem) = repository.insertItem(item)
}