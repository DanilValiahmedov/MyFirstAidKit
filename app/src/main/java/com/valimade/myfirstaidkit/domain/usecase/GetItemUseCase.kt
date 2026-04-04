package com.valimade.myfirstaidkit.domain.usecase

import com.valimade.myfirstaidkit.domain.model.Characteristic
import com.valimade.myfirstaidkit.domain.model.CharacteristicItem
import com.valimade.myfirstaidkit.domain.repository.MedicineRepository

class GetItemUseCase(private val repository: MedicineRepository){
    suspend operator fun invoke(characteristic: Characteristic, id: Int): CharacteristicItem? {
        return repository.getItemById(characteristic, id)
    }
}