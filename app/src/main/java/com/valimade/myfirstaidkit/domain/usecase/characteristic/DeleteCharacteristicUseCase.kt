package com.valimade.myfirstaidkit.domain.usecase.characteristic

import com.valimade.myfirstaidkit.domain.model.Characteristic
import com.valimade.myfirstaidkit.domain.repository.CharacteristicRepository

class DeleteCharacteristicUseCase(private val repository: CharacteristicRepository){
    suspend operator fun invoke(characteristic: Characteristic, verificationName: String) {
        repository.deleteCharacteristicByVerificationName(characteristic, verificationName)
    }
}