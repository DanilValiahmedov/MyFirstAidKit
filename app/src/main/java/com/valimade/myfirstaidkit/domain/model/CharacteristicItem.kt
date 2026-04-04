package com.valimade.myfirstaidkit.domain.model

import com.valimade.myfirstaidkit.data.db.entities.Disease
import com.valimade.myfirstaidkit.data.db.entities.Form
import com.valimade.myfirstaidkit.data.db.entities.Location
import com.valimade.myfirstaidkit.data.db.entities.Symptom
import com.valimade.myfirstaidkit.data.db.entities.Whom

sealed class CharacteristicItem {
    data class MedicineItem(val data: Medicine) : CharacteristicItem()
    data class SymptomItem(val data: Symptom) : CharacteristicItem()
    data class DiseaseItem(val data: Disease) : CharacteristicItem()
    data class FormItem(val data: Form) : CharacteristicItem()
    data class WhomItem(val data: Whom) : CharacteristicItem()
    data class LocationItem(val data: Location) : CharacteristicItem()
}