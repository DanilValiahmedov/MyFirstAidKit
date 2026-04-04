package com.valimade.myfirstaidkit.medicine.domain.usecase

import com.valimade.myfirstaidkit.medicine.domain.model.Characteristic
import com.valimade.myfirstaidkit.medicine.domain.model.CharacteristicItem
import com.valimade.myfirstaidkit.medicine.domain.repository.MedicineRepository

class GetItemByIdUseCase(private val repository: MedicineRepository){
    suspend operator fun invoke(characteristic: Characteristic, id: Int): CharacteristicItem? {
        return repository.getItemById(characteristic, id)
    }
}