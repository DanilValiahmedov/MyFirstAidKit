package com.valimade.myfirstaidkit.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.valimade.myfirstaidkit.domain.model.Characteristic
import com.valimade.myfirstaidkit.domain.model.Characteristic.DISEASES
import com.valimade.myfirstaidkit.domain.model.Characteristic.FORM
import com.valimade.myfirstaidkit.domain.model.Characteristic.LOCATION
import com.valimade.myfirstaidkit.domain.model.Characteristic.MEDICINE
import com.valimade.myfirstaidkit.domain.model.Characteristic.SYMPTOM
import com.valimade.myfirstaidkit.domain.model.Characteristic.WHOM
import com.valimade.myfirstaidkit.domain.model.CharacteristicItem.DiseaseItem
import com.valimade.myfirstaidkit.domain.model.CharacteristicItem.FormItem
import com.valimade.myfirstaidkit.domain.model.CharacteristicItem.LocationItem
import com.valimade.myfirstaidkit.domain.model.CharacteristicItem.MedicineItem
import com.valimade.myfirstaidkit.domain.model.CharacteristicItem.SymptomItem
import com.valimade.myfirstaidkit.domain.model.CharacteristicItem.WhomItem
import com.valimade.myfirstaidkit.domain.usecase.GetAllItemUseCase
import com.valimade.myfirstaidkit.domain.usecase.GetItemUseCase
import com.valimade.myfirstaidkit.ui.model.Operation
import com.valimade.myfirstaidkit.ui.model.Operation.CREATE
import com.valimade.myfirstaidkit.ui.model.Operation.UPDATE
import com.valimade.myfirstaidkit.ui.state.MedicineState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MedicineViewModel(
    private val getAllItemUseCase: GetAllItemUseCase,
    private val getItemUseCase: GetItemUseCase,
): ViewModel() {
    private val _medicineState = MutableStateFlow(MedicineState())
    val medicineState = _medicineState.asStateFlow()

    suspend fun startInitialization(operation: Operation, id: Int = 0) {
        val symptoms = getAllItemUseCase(SYMPTOM) as List<SymptomItem>
        val diseases = getAllItemUseCase(DISEASES) as List<DiseaseItem>
        val forms = getAllItemUseCase(FORM) as List<FormItem>
        val forWhoms = getAllItemUseCase(WHOM) as List<WhomItem>
        val locations = getAllItemUseCase(LOCATION) as List<LocationItem>

        when(operation){
            CREATE -> {
                val symptomsState = symptoms.map { item -> Pair(item.data, false) }
                val diseasesState = diseases.map { item -> Pair(item.data, false) }
                val formsState = forms.map { item -> Pair(item.data, false) }
                val forWhomsState = forWhoms.map { item -> Pair(item.data, false) }
                val locationsState = locations.map { item -> Pair(item.data, false) }

                _medicineState.update {
                    it.copy(
                        isLoading = false,
                        symptoms = symptomsState,
                        diseases = diseasesState,
                        forms = formsState,
                        forWhoms = forWhomsState,
                        locations = locationsState,
                    )
                }
            }
            UPDATE -> {
                val medicine = getItemUseCase(MEDICINE, id) as? MedicineItem
                if (medicine != null) {
                    val symptomsState = symptoms.map { item ->
                        Pair(item.data,
                            medicine.data.symptomsVerification?.contains(item.data.verificationName) == true
                        )
                    }
                    val diseasesState = diseases.map { item ->
                        Pair(item.data,
                            medicine.data.diseasesVerification?.contains(item.data.verificationName) == true
                        )
                    }
                    val formsState = forms.map { item ->
                        Pair(item.data,
                            medicine.data.formsVerification?.contains(item.data.verificationName) == true
                        )
                    }
                    val forWhomsState = forWhoms.map { item ->
                        Pair(item.data,
                            medicine.data.forWhomsVerification?.contains(item.data.verificationName) == true
                        )
                    }
                    val locationsState = locations.map { item ->
                        Pair(item.data,
                            medicine.data.locationsVerification?.contains(item.data.verificationName) == true
                        )
                    }

                    _medicineState.update {
                        it.copy(
                            isLoading = false,
                            medicine = medicine.data,
                            symptoms = symptomsState,
                            diseases = diseasesState,
                            forms = formsState,
                            forWhoms = forWhomsState,
                            locations = locationsState,
                        )
                    }
                } else {
                    _medicineState.update {
                        it.copy(
                            isLoading = false,
                            error = "Произошла ошибка при изменении лекарственного препарата. \nПопробуйте позже!",
                        )
                    }
                }

            }
        }
    }

}