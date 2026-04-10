package com.valimade.myfirstaidkit.domain.usecase.characteristic

import com.valimade.myfirstaidkit.domain.model.Characteristic
import com.valimade.myfirstaidkit.domain.model.CharacteristicMedicine
import com.valimade.myfirstaidkit.domain.repository.CharacteristicRepository

class GetAllCharacteristicsUseCase(private val repository: CharacteristicRepository){
    suspend operator fun invoke(characteristic: Characteristic): List<CharacteristicMedicine> {
        return repository.getAllCharacteristics(characteristic)
    }
}