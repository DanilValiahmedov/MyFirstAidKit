package com.valimade.myfirstaidkit.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.valimade.myfirstaidkit.data.db.entities.Disease
import com.valimade.myfirstaidkit.data.db.entities.Form
import com.valimade.myfirstaidkit.data.db.entities.Location
import com.valimade.myfirstaidkit.data.db.entities.Symptom
import com.valimade.myfirstaidkit.data.db.entities.Whom
import com.valimade.myfirstaidkit.domain.model.Characteristic
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
import com.valimade.myfirstaidkit.domain.usecase.medicine.DeleteItemUseCase
import com.valimade.myfirstaidkit.domain.usecase.characteristic.ExistsCharacteristicUseCase
import com.valimade.myfirstaidkit.domain.usecase.medicine.GetAllMedicineUseCase
import com.valimade.myfirstaidkit.domain.usecase.medicine.GetMedicineUseCase
import com.valimade.myfirstaidkit.domain.usecase.medicine.InsertItemUseCase
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
import kotlin.String
import kotlin.collections.List

class MedicineViewModel(
    private val getAllMedicineUseCase: GetAllMedicineUseCase,
    private val getItemUseCase: GetMedicineUseCase,
    private val existsCharacteristicUseCase: ExistsCharacteristicUseCase,
    private val insertItemUseCase: InsertItemUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val normalizer: StringNormalizer,
): ViewModel() {
    private val _medicineState = MutableStateFlow(MedicineState())
    val medicineState = _medicineState.asStateFlow()

    suspend fun startInitialization(operation: Operation, id: Int = 0) {
        val symptoms = getAllMedicineUseCase(SYMPTOM) as List<SymptomItem>
        val diseases = getAllMedicineUseCase(DISEASES) as List<DiseaseItem>
        val forms = getAllMedicineUseCase(FORM) as List<FormItem>
        val forWhoms = getAllMedicineUseCase(WHOM) as List<WhomItem>
        val locations = getAllMedicineUseCase(LOCATION) as List<LocationItem>

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
                        selectedItems = medicine.data.symptoms.map{
                            Symptom(name = it, verificationName = normalizer.normalizeVerificationName(it) )
                        },
                        selectedNames = medicine.data.symptomsVerification,
                        getName = { it.verificationName },
                    )

                    val diseasesState = mergeWithPriority(
                        allItems = diseases.map { it.data },
                        selectedItems = medicine.data.diseases.map{
                            Disease(name = it, verificationName = normalizer.normalizeVerificationName(it) )
                        },
                        selectedNames = medicine.data.diseasesVerification,
                        getName = { it.verificationName },
                    )

                    val formsState = mergeWithPriority(
                        allItems = forms.map { it.data },
                        selectedItems = medicine.data.forms.map{
                            Form(name = it, verificationName = normalizer.normalizeVerificationName(it) )
                        },
                        selectedNames = medicine.data.formsVerification,
                        getName = { it.verificationName },
                    )

                    val forWhomsState = mergeWithPriority(
                        allItems = forWhoms.map { it.data },
                        selectedItems = medicine.data.forWhoms.map{
                            Whom(name = it, verificationName = normalizer.normalizeVerificationName(it) )
                        },
                        selectedNames = medicine.data.forWhomsVerification,
                        getName = { it.verificationName },
                    )

                    val locationsState = mergeWithPriority(
                        allItems = locations.map { it.data },
                        selectedItems = medicine.data.locations.map{
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

    fun createOrCloseCharacteristicField(characteristic: Characteristic, value: Boolean) {
        when(characteristic) {
            MEDICINE -> {
                _medicineState.update {
                    it.copy(
                        error = "Произошла ошибка при создании данной характеристики препарата",
                    )
                }
            }
            SYMPTOM -> {
                _medicineState.update {
                    it.copy(
                        isNewSymptom = value,
                        newSymptom = "",
                    )
                }
            }
            DISEASES -> {
                _medicineState.update {
                    it.copy(
                        isNewDisease = value,
                        newDisease = "",
                    )
                }
            }
            FORM -> {
                _medicineState.update {
                    it.copy(
                        isNewForm = value,
                        newForm = "",
                    )
                }
            }
            WHOM -> {
                _medicineState.update {
                    it.copy(
                        isNewWhom = value,
                        newWhom = "",
                    )
                }
            }
            LOCATION -> {
                _medicineState.update {
                    it.copy(
                        isNewLocation = value,
                        newLocation = "",
                    )
                }
            }
        }
    }

    fun addCharacteristicName(characteristic: Characteristic, name: String) {
        when(characteristic) {
            MEDICINE -> {
                _medicineState.update {
                    it.copy(
                        error = "Произошла ошибка при создании данной характеристики препарата",
                    )
                }
            }
            SYMPTOM -> {
                _medicineState.update {
                    it.copy(
                        newSymptom = name,
                    )
                }
            }
            DISEASES -> {
                _medicineState.update {
                    it.copy(
                        newDisease = name,
                    )
                }
            }
            FORM -> {
                _medicineState.update {
                    it.copy(
                        newForm = name,
                    )
                }
            }
            WHOM -> {
                _medicineState.update {
                    it.copy(
                        newWhom = name,
                    )
                }
            }
            LOCATION -> {
                _medicineState.update {
                    it.copy(
                        newLocation = name,
                    )
                }
            }
        }
    }

    suspend fun createCharacteristic(characteristic: Characteristic, name: String) {
        val verificationName = normalizer.normalizeVerificationName(name)
        if(!existsCharacteristicUseCase(characteristic, verificationName)) {
            when(characteristic) {
                MEDICINE -> {
                    _medicineState.update {
                        it.copy(
                            error = "Произошла ошибка при создании данной характеристики препарата",
                        )
                    }
                }
                SYMPTOM -> {
                    val symptom = SymptomItem(
                        Symptom(name = name, verificationName = verificationName)
                    )
                    insertItemUseCase(symptom)

                    _medicineState.update {
                        it.copy(
                            symptoms = it.symptoms + Pair(symptom.data, true),
                        )
                    }
                }
                DISEASES -> {
                    val disease = DiseaseItem(
                        Disease(name = name, verificationName = verificationName)
                    )
                    insertItemUseCase(disease)

                    _medicineState.update {
                        it.copy(
                            diseases = it.diseases + Pair(disease.data, true),
                        )
                    }
                }
                FORM -> {
                    val form = FormItem(
                        Form(name = name, verificationName = verificationName)
                    )
                    insertItemUseCase(form)

                    _medicineState.update {
                        it.copy(
                            forms = it.forms + Pair(form.data, true),
                        )
                    }
                }
                WHOM -> {
                    val whom = WhomItem(
                        Whom(name = name, verificationName = verificationName)
                    )
                    insertItemUseCase(whom)

                    _medicineState.update {
                        it.copy(
                            forWhoms = it.forWhoms + Pair(whom.data, true),
                        )
                    }
                }
                LOCATION -> {
                    val location = LocationItem(
                        Location(name = name, verificationName = verificationName)
                    )
                    insertItemUseCase(location)

                    _medicineState.update {
                        it.copy(
                            locations = it.locations + Pair(location.data, true),
                        )
                    }
                }
            }
        } else {
            _medicineState.update {
                it.copy(
                    error = "Данная характеристика уже присутсвует в списке",
                )
            }
        }
    }

    fun showDeleteCharacteristicButton(characteristic: Characteristic) {
        when(characteristic) {
            MEDICINE -> {
                _medicineState.update {
                    it.copy(
                        error = "Произошла ошибка при удалении данной характеристики препарата",
                    )
                }
            }
            SYMPTOM -> {
                _medicineState.update {
                    it.copy(
                        isDeleteSymptoms = !it.isDeleteSymptoms,
                    )
                }
            }
            DISEASES -> {
                _medicineState.update {
                    it.copy(
                        isDeleteSymptoms = !it.isDeleteDiseases,
                    )
                }
            }
            FORM -> {
                _medicineState.update {
                    it.copy(
                        isDeleteSymptoms = !it.isDeleteForms,
                    )
                }
            }
            WHOM -> {
                _medicineState.update {
                    it.copy(
                        isDeleteSymptoms = !it.isDeleteWhoms,
                    )
                }
            }
            LOCATION -> {
                _medicineState.update {
                    it.copy(
                        isDeleteSymptoms = !it.isDeleteLocations,
                    )
                }
            }
        }
    }

    suspend fun deleteCharacteristic(item: CharacteristicItem) {
        when(item) {
            is MedicineItem -> {
                _medicineState.update {
                    it.copy(
                        error = "Произошла ошибка при удалении данной характеристики препарата",
                    )
                }
            }
            is SymptomItem -> {
                deleteItemUseCase(SYMPTOM, item.data.verificationName)

                _medicineState.update {
                    it.copy(
                        symptoms = it.symptoms.filter { symptom -> symptom.first != item.data},
                    )
                }
            }
            is DiseaseItem -> {
                deleteItemUseCase(DISEASES, item.data.verificationName)

                _medicineState.update {
                    it.copy(
                        diseases = it.diseases.filter { disease -> disease.first != item.data},
                    )
                }
            }
            is FormItem -> {
                deleteItemUseCase(FORM, item.data.verificationName)

                _medicineState.update {
                    it.copy(
                        forms = it.forms.filter { form -> form.first != item.data},
                    )
                }
            }
            is WhomItem -> {
                deleteItemUseCase(WHOM, item.data.verificationName)

                _medicineState.update {
                    it.copy(
                        forWhoms = it.forWhoms.filter { whom -> whom.first != item.data},
                    )
                }
            }
            is LocationItem -> {
                deleteItemUseCase(LOCATION, item.data.verificationName)

                _medicineState.update {
                    it.copy(
                        locations = it.locations.filter { location -> location.first != item.data},
                    )
                }
            }
        }
    }

    suspend fun saveMedicine(operation: Operation) {
        //Обнуляем характеристики
        if(operation == UPDATE) {
            _medicineState.update {
                it.copy(
                    medicine = it.medicine.copy(
                        symptoms = emptyList(),
                        symptomsVerification = emptyList(),
                        diseases = emptyList(),
                        diseasesVerification = emptyList(),
                        forms = emptyList(),
                        formsVerification = emptyList(),
                        forWhoms = emptyList(),
                        forWhomsVerification = emptyList(),
                        locations = emptyList(),
                    )
                )
            }
        }

        //Заполяняем теукщие характеристики
        _medicineState.value.symptoms.forEach { symptom ->
            if(symptom.second){
                _medicineState.update {
                    it.copy(
                        medicine = it.medicine.copy(
                            symptoms = it.medicine.symptoms + symptom.first.name,
                            symptomsVerification = it.medicine.symptomsVerification + symptom.first.verificationName,
                        )
                    )
                }
            }
        }
        _medicineState.value.diseases.forEach { disease ->
            if(disease.second){
                _medicineState.update {
                    it.copy(
                        medicine = it.medicine.copy(
                            diseases = it.medicine.diseases + disease.first.name,
                            diseasesVerification = it.medicine.diseasesVerification + disease.first.verificationName,
                        )
                    )
                }
            }
        }
        _medicineState.value.forms.forEach { form ->
            if(form.second){
                _medicineState.update {
                    it.copy(
                        medicine = it.medicine.copy(
                            forms = it.medicine.forms + form.first.name,
                            formsVerification = it.medicine.formsVerification + form.first.verificationName,
                        )
                    )
                }
            }
        }
        _medicineState.value.forWhoms.forEach { whom ->
            if(whom.second){
                _medicineState.update {
                    it.copy(
                        medicine = it.medicine.copy(
                            forWhoms = it.medicine.forms + whom.first.name,
                            forWhomsVerification = it.medicine.forWhomsVerification + whom.first.verificationName,
                        )
                    )
                }
            }
        }
        _medicineState.value.locations.forEach { location ->
            if(location.second){
                _medicineState.update {
                    it.copy(
                        medicine = it.medicine.copy(
                            locations = it.medicine.locations + location.first.name,
                            locationsVerification = it.medicine.locationsVerification + location.first.verificationName,
                        )
                    )
                }
            }
        }

        val item = MedicineItem(_medicineState.value.medicine)
        insertItemUseCase(item)
    }
}