package com.valimade.myfirstaidkit.medicine.domain.usecase

import com.valimade.myfirstaidkit.medicine.domain.model.Characteristic
import com.valimade.myfirstaidkit.medicine.domain.model.CharacteristicItem
import com.valimade.myfirstaidkit.medicine.domain.repository.MedicineRepository

class GetAllItemUseCase(private val repository: MedicineRepository){
    suspend operator fun invoke(characteristic: Characteristic): List<CharacteristicItem> {
        return repository.getAllItem(characteristic)
    }
}