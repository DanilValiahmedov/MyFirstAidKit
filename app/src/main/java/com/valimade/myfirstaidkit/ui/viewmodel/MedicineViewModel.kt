package com.valimade.myfirstaidkit.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.valimade.myfirstaidkit.data.db.entities.Disease
import com.valimade.myfirstaidkit.data.db.entities.Form
import com.valimade.myfirstaidkit.data.db.entities.Location
import com.valimade.myfirstaidkit.data.db.entities.Symptom
import com.valimade.myfirstaidkit.data.db.entities.Whom
import com.valimade.myfirstaidkit.domain.model.Characteristic.DISEASES
import com.valimade.myfirstaidkit.domain.model.Characteristic.FORM
import com.valimade.myfirstaidkit.domain.model.Characteristic.LOCATION
import com.valimade.myfirstaidkit.domain.model.Characteristic.MEDICINE
import com.valimade.myfirstaidkit.domain.model.Characteristic.SYMPTOM
import com.valimade.myfirstaidkit.domain.model.Characteristic.WHOM
import com.valimade.myfirstaidkit.domain.model.CharacteristicItem
import com.valimade.myfirstaidkit.domain.model.CharacteristicItem.DiseaseItem
import com.valimade.myfirstaidkit.domain.model.CharacteristicItem.FormItem
import com.valimade.myfirstaidkit.domain.model.CharacteristicItem.LocationItem
import com.valimade.myfirstaidkit.domain.model.CharacteristicItem.MedicineItem
import com.valimade.myfirstaidkit.domain.model.CharacteristicItem.SymptomItem
import com.valimade.myfirstaidkit.domain.model.CharacteristicItem.WhomItem
import com.valimade.myfirstaidkit.domain.usecase.GetAllItemUseCase
import com.valimade.myfirstaidkit.domain.usecase.GetItemUseCase
import com.valimade.myfirstaidkit.domain.utils.StringNormalizer
import com.valimade.myfirstaidkit.ui.model.Months
import com.valimade.myfirstaidkit.ui.model.Operation
import com.valimade.myfirstaidkit.ui.model.Operation.CREATE
import com.valimade.myfirstaidkit.ui.model.Operation.UPDATE
import com.valimade.myfirstaidkit.ui.state.MedicineState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.ZoneId

class MedicineViewModel(
    private val getAllItemUseCase: GetAllItemUseCase,
    private val getItemUseCase: GetItemUseCase,
    private val normalizer: StringNormalizer,
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
                    //Береме значеия сначала из карточки лекарства, потом из БД
                    val symptomsState = mergeWithPriority(
                        allItems = symptoms.map { it.data },
                        selectedItems = medicine.data.symptoms?.map{
                            Symptom(name = it, verificationName = normalizer.normalizeVerificationName(it) )
                        },
                        selectedNames = medicine.data.symptomsVerification,
                        getName = { it.verificationName },
                    )

                    val diseasesState = mergeWithPriority(
                        allItems = diseases.map { it.data },
                        selectedItems = medicine.data.diseases?.map{
                            Disease(name = it, verificationName = normalizer.normalizeVerificationName(it) )
                        },
                        selectedNames = medicine.data.diseasesVerification,
                        getName = { it.verificationName },
                    )

                    val formsState = mergeWithPriority(
                        allItems = forms.map { it.data },
                        selectedItems = medicine.data.forms?.map{
                            Form(name = it, verificationName = normalizer.normalizeVerificationName(it) )
                        },
                        selectedNames = medicine.data.formsVerification,
                        getName = { it.verificationName },
                    )

                    val forWhomsState = mergeWithPriority(
                        allItems = forWhoms.map { it.data },
                        selectedItems = medicine.data.forWhoms?.map{
                            Whom(name = it, verificationName = normalizer.normalizeVerificationName(it) )
                        },
                        selectedNames = medicine.data.forWhomsVerification,
                        getName = { it.verificationName },
                    )

                    val locationsState = mergeWithPriority(
                        allItems = locations.map { it.data },
                        selectedItems = medicine.data.locations?.map{
                            Location(name = it, verificationName = normalizer.normalizeVerificationName(it) )
                        },
                        selectedNames = medicine.data.locationsVerification,
                        getName = { it.verificationName },
                    )

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

    private fun <T> mergeWithPriority(
        allItems: List<T>,
        selectedItems: List<T>?,
        selectedNames: List<String>?,
        getName: (T) -> String,
    ): List<Pair<T, Boolean>> {

        val selectedSet = selectedNames?.toSet() ?: emptySet()

        // Построенные данные из medicine
        val selectedItemsPair = selectedItems?.map { Pair(it, true) } ?: emptyList()

        // Не повторяющиеся с medicine, данные из БД
        val restItems = allItems.filter { getName(it) !in selectedSet }.map { it to false }

        return selectedItemsPair + restItems

    }

    fun addName(name: String) {
        _medicineState.update {
            it.copy(
                medicine = it.medicine.copy(
                    name = name,
                    verificationName = normalizer.normalizeVerificationName(name)
                )
            )
        }
    }

    fun addComment(comment: String) {
        _medicineState.update {
            it.copy(
                medicine = it.medicine.copy(
                    comment = comment,
                )
            )
        }
    }

    fun addExpirationDate(day: Int? = null, month: Months, years: Int) {
        val expirationDateVisually = if(day != null) "$day ${month.monthName} $years"
            else "${month.monthName} $years"

        val localDate = if(day != null) LocalDate.of(years, month.number, day)
            else LocalDate.of(years, month.number, 1)

        val unixTime = localDate.atStartOfDay(ZoneId.of("UTC")).toEpochSecond()
        _medicineState.update {
            it.copy(
                medicine = it.medicine.copy(
                    expirationDateVisually = expirationDateVisually,
                    expirationDate = unixTime,
                )
            )
        }
    }

    fun addOrRemoveCharacteristic(item: CharacteristicItem, newValue: Boolean) {
        when (item) {
            is SymptomItem -> {
                _medicineState.update {
                    it.copy(
                        symptoms = it.symptoms.map { symptom ->
                            if(symptom == Pair(item.data, !newValue)) {
                                Pair(item.data, newValue)
                            } else symptom
                        }
                    )
                }
            }
            is DiseaseItem -> {
                _medicineState.update {
                    it.copy(
                        diseases = it.diseases.map { disease ->
                            if(disease == Pair(item.data, !newValue)) {
                                Pair(item.data, newValue)
                            } else disease
                        }
                    )
                }
            }
            is FormItem -> {
                _medicineState.update {
                    it.copy(
                        forms = it.forms.map { form ->
                            if(form == Pair(item.data, !newValue)) {
                                Pair(item.data, newValue)
                            } else form
                        }
                    )
                }
            }
            is WhomItem -> {
                _medicineState.update {
                    it.copy(
                        forWhoms = it.forWhoms.map { forWhom ->
                            if(forWhom == Pair(item.data, !newValue)) {
                                Pair(item.data, newValue)
                            } else forWhom
                        }
                    )
                }
            }
            is LocationItem -> {
                _medicineState.update {
                    it.copy(
                        locations = it.locations.map { location ->
                            if(location == Pair(item.data, !newValue)) {
                                Pair(item.data, newValue)
                            } else location
                        }
                    )
                }
            }
            else -> {}
        }
    }

}