package com.valimade.myfirstaidkit.domain.model

data class CharacteristicMedicine(
    val id: Int = 0,
    val characteristic: Characteristic,
    val name: String,
    val verificationName: String,
)