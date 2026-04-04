package com.valimade.myfirstaidkit.medicine.domain.usecase

import com.valimade.myfirstaidkit.medicine.domain.model.Characteristic
import com.valimade.myfirstaidkit.medicine.domain.repository.MedicineRepository

class DeleteItemUseCase(private val repository: MedicineRepository){
    suspend operator fun invoke(characteristic: Characteristic, id: Int) {
        return repository.deleteItemById(characteristic, id)
    }
}