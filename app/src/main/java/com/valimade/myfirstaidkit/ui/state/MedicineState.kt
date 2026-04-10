package com.valimade.myfirstaidkit.ui.state

import com.valimade.myfirstaidkit.domain.model.Characteristic
import com.valimade.myfirstaidkit.domain.model.CharacteristicMedicine
import com.valimade.myfirstaidkit.domain.model.Medicine

data class MedicineState(
    val isLoading: Boolean = true,
    val error: String? = null,

    val medicine: Medicine = Medicine(name = "", verificationName = ""),
    val characteristics: List<Pair<CharacteristicMedicine, Boolean>> = emptyList(), //Pair<Характеристика, Выбрали ли ее пользователь?>
    val deleteCharacteristics: List<Characteristic> = emptyList(), //Список удаляемых характеристик
    val newCharacteristicName: String = "",
    val newCharacteristic: Characteristic? = null, //Добавляемая характеристика

    val daysShown: List<Int> = (1..31).toList(),
    val yearsShown: List<Int> = (2026..2046).toList(),
)