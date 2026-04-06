package com.valimade.myfirstaidkit.ui.state

import com.valimade.myfirstaidkit.data.db.entities.Disease
import com.valimade.myfirstaidkit.data.db.entities.Form
import com.valimade.myfirstaidkit.data.db.entities.Location
import com.valimade.myfirstaidkit.data.db.entities.Symptom
import com.valimade.myfirstaidkit.data.db.entities.Whom
import com.valimade.myfirstaidkit.domain.model.Characteristic
import com.valimade.myfirstaidkit.domain.model.Medicine

data class MedicineState(
    val isLoading: Boolean = true,
    val error: String? = null,

    val medicine: Medicine = Medicine(name = "", verificationName = ""),
    //Pair - ячейка из таблицы и ее состояние активности (для отображения)
    val symptoms: List<Pair<Symptom, Boolean>> = emptyList(),
    val newSymptom: String = "",
    val isNewSymptom: Boolean = false,
    val isDeleteSymptoms: Boolean = false,

    val diseases: List<Pair<Disease, Boolean>> = emptyList(),
    val newDisease: String = "",
    val isNewDisease: Boolean = false,
    val isDeleteDiseases: Boolean = false,

    val forms: List<Pair<Form, Boolean>> = emptyList(),
    val newForm: String = "",
    val isNewForm: Boolean = false,
    val isDeleteForms: Boolean = false,

    val forWhoms: List<Pair<Whom, Boolean>> = emptyList(),
    val newWhom: String = "",
    val isNewWhom: Boolean = false,
    val isDeleteWhoms: Boolean = false,

    val locations: List<Pair<Location, Boolean>> = emptyList(),
    val newLocation: String = "",
    val isNewLocation: Boolean = false,
    val isDeleteLocations: Boolean = false,

    val daysShown: List<Int> = (1..31).toList(),
    val yearsShown: List<Int> = (2026..2046).toList(),
)