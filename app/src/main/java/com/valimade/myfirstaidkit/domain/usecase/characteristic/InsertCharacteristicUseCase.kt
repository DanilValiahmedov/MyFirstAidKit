package com.valimade.myfirstaidkit.domain.usecase.characteristic

import com.valimade.myfirstaidkit.domain.model.CharacteristicMedicine
import com.valimade.myfirstaidkit.domain.repository.CharacteristicRepository

class InsertCharacteristicUseCase(private val repository: CharacteristicRepository){
    suspend operator fun invoke(characteristicMedicine: CharacteristicMedicine) {
        repository.insertCharacteristic(characteristicMedicine)
    }
}