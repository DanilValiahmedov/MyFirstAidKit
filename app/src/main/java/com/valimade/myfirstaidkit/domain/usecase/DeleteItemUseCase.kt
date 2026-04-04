package com.valimade.myfirstaidkit.domain.usecase

import com.valimade.myfirstaidkit.domain.model.Characteristic
import com.valimade.myfirstaidkit.domain.repository.MedicineRepository

class DeleteItemUseCase(private val repository: MedicineRepository){
    suspend operator fun invoke(characteristic: Characteristic, id: Int) {
        return repository.deleteItemById(characteristic, id)
    }
}