package com.valimade.myfirstaidkit.medicine.domain.model

import com.valimade.myfirstaidkit.medicine.data.db.entities.Disease
import com.valimade.myfirstaidkit.medicine.data.db.entities.Form
import com.valimade.myfirstaidkit.medicine.data.db.entities.MedicineData
import com.valimade.myfirstaidkit.medicine.data.db.entities.Symptom
import com.valimade.myfirstaidkit.medicine.data.db.entities.Whom

sealed class CharacteristicItem {
    data class MedicineItem(val data: MedicineData) : CharacteristicItem()
    data class SymptomItem(val data: Symptom) : CharacteristicItem()
    data class DiseaseItem(val data: Disease) : CharacteristicItem()
    data class FormItem(val data: Form) : CharacteristicItem()
    data class WhomItem(val data: Whom) : CharacteristicItem()
}