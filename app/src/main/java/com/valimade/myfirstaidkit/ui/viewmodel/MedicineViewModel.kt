package com.valimade.myfirstaidkit.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.valimade.myfirstaidkit.domain.model.Characteristic
import com.valimade.myfirstaidkit.domain.model.Characteristic.DISEASES
import com.valimade.myfirstaidkit.domain.model.Characteristic.FORM
import com.valimade.myfirstaidkit.domain.model.Characteristic.LOCATION
import com.valimade.myfirstaidkit.domain.model.Characteristic.SYMPTOM
import com.valimade.myfirstaidkit.domain.model.Characteristic.WHOM
import com.valimade.myfirstaidkit.domain.model.CharacteristicMedicine
import com.valimade.myfirstaidkit.domain.usecase.characteristic.DeleteCharacteristicUseCase
import com.valimade.myfirstaidkit.domain.usecase.characteristic.ExistsCharacteristicUseCase
import com.valimade.myfirstaidkit.domain.usecase.characteristic.GetAllCharacteristicsUseCase
import com.valimade.myfirstaidkit.domain.usecase.characteristic.InsertCharacteristicUseCase
import com.valimade.myfirstaidkit.domain.usecase.medicine.GetMedicineUseCase
import com.valimade.myfirstaidkit.domain.usecase.medicine.InsertMedicineUseCase
import com.valimade.myfirstaidkit.domain.utils.StringNormalizer
import com.valimade.myfirstaidkit.ui.model.Months
import com.valimade.myfirstaidkit.ui.state.MedicineState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.ZoneId
import kotlin.String

class MedicineViewModel(
    private val getAllCharacteristicsUseCase: GetAllCharacteristicsUseCase,
    private val getMedicineUseCase: GetMedicineUseCase,
    private val existsCharacteristicUseCase: ExistsCharacteristicUseCase,
    private val insertCharacteristicUseCase: InsertCharacteristicUseCase,
    private val deleteCharacteristicUseCase: DeleteCharacteristicUseCase,
    private val insertMedicineUseCase: InsertMedicineUseCase,
    private val normalizer: StringNormalizer,
): ViewModel() {
    private val _medicineState = MutableStateFlow(MedicineState())
    val medicineState = _medicineState.asStateFlow()

    suspend fun startInitialization(id: Int = 0) {
        val symptoms = getAllCharacteristicsUseCase(SYMPTOM).map { item -> Pair(item, false) }
        val diseases = getAllCharacteristicsUseCase(DISEASES).map { item -> Pair(item, false) }
        val forms = getAllCharacteristicsUseCase(FORM).map { item -> Pair(item, false) }
        val whoms = getAllCharacteristicsUseCase(WHOM).map { item -> Pair(item, false) }
        val locations = getAllCharacteristicsUseCase(LOCATION).map { item -> Pair(item, false) }

        val characteristics = symptoms + diseases + forms + whoms + locations

        val medicine = getMedicineUseCase(id)
        if (medicine != null) {
            //Изменение существующей картчоки препарата
            val symptomsMedicine = medicine.symptoms.map {
                updateInitializationCharacteristic(SYMPTOM, it)
            }
            val diseasesMedicine = medicine.diseases.map {
                updateInitializationCharacteristic(DISEASES, it)
            }
            val formsMedicine = medicine.forms.map {
                updateInitializationCharacteristic(FORM, it)
            }
            val whomsMedicine = medicine.whoms.map {
                updateInitializationCharacteristic(WHOM, it)
            }
            val locationsMedicine = medicine.locations.map {
                updateInitializationCharacteristic(LOCATION, it)
            }
            val characteristicMedicine =
                symptomsMedicine + diseasesMedicine + formsMedicine + whomsMedicine + locationsMedicine

            val uniqueCharacteristic = characteristicMedicine.map { it.first }.toSet()
            val newCharacteristics = characteristics.filter { it.first !in uniqueCharacteristic }
            _medicineState.update {
                it.copy(
                    isLoading = false,
                    characteristics = newCharacteristics,
                )
            }
        } else {
            //Создание картчоки препарата
            _medicineState.update {
                it.copy(
                    isLoading = false,
                    characteristics = characteristics,
                )
            }
        }
    }

    private suspend fun updateInitializationCharacteristic(
        characteristic: Characteristic, name: String,
    ): Pair<CharacteristicMedicine, Boolean> {
        return Pair(
            CharacteristicMedicine(
                characteristic = characteristic,
                name = name,
                verificationName = normalizer.normalizeVerificationName(name)
            ), true
        )
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
                medicine = it.medicine.copy(comment = comment)
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

    fun onClickCharacteristic(item: CharacteristicMedicine, newValue: Boolean) {
        _medicineState.update {
            it.copy(
                characteristics = it.characteristics.map { characteristic ->
                    if(characteristic == Pair(item, !newValue)) Pair(item, newValue)
                    else characteristic
                }
            )
        }
    }

    fun createOrCloseCharacteristicField(characteristic: Characteristic?) {
        _medicineState.update {
            it.copy(
                newCharacteristicName = "",
                newCharacteristic = characteristic,
            )
        }
    }

    fun addCharacteristicName(name: String) {
        _medicineState.update {
            it.copy(
                newCharacteristicName = name,
            )
        }
    }

    suspend fun createCharacteristic(characteristic: Characteristic, name: String) {
        val verificationName = normalizer.normalizeVerificationName(name)
        val existsCharacteristic = existsCharacteristicUseCase(characteristic, verificationName)
        if(!existsCharacteristic) {
            val characteristicMedicine = CharacteristicMedicine(
                characteristic = characteristic,
                name = name,
                verificationName = verificationName
            )
            insertCharacteristicUseCase(characteristicMedicine)

        } else {
            _medicineState.update {
                it.copy(
                    error = "Данная характеристика уже присутсвует в списке",
                )
            }
        }
    }

    fun showDeleteCharacteristicButton(characteristic: Characteristic, isShow: Boolean) {
        val deleteCharacteristics = if(isShow)_medicineState.value.deleteCharacteristics + characteristic
            else _medicineState.value.deleteCharacteristics - characteristic
        _medicineState.update {
            it.copy(
                deleteCharacteristics = deleteCharacteristics
            )
        }
    }

    suspend fun deleteCharacteristic(item: CharacteristicMedicine) {
        deleteCharacteristicUseCase(item)
        _medicineState.update {
            it.copy(
                characteristics = it.characteristics.filter { characteristic -> characteristic.first != item},
            )
        }
    }

    suspend fun saveMedicine() {
        val symptoms: MutableList<Pair<String, String>> = mutableListOf()
        val diseases: MutableList<Pair<String, String>> = mutableListOf()
        val forms: MutableList<Pair<String, String>> = mutableListOf()
        val whoms: MutableList<Pair<String, String>> = mutableListOf()
        val locations: MutableList<Pair<String, String>> = mutableListOf()

        //Заполяняем теукщие характеристики
        _medicineState.value.characteristics.forEach { characteristic ->
            if(characteristic.second){
                when(characteristic.first.characteristic){
                    SYMPTOM -> {
                        symptoms += Pair(characteristic.first.name, characteristic.first.verificationName)
                    }
                    DISEASES -> {
                        diseases += Pair(characteristic.first.name, characteristic.first.verificationName)
                    }
                    FORM -> {
                        forms += Pair(characteristic.first.name, characteristic.first.verificationName)
                    }
                    WHOM -> {
                        whoms += Pair(characteristic.first.name, characteristic.first.verificationName)
                    }
                    LOCATION -> {
                        locations += Pair(characteristic.first.name, characteristic.first.verificationName)
                    }
                }
            }
        }

        _medicineState.update {
            it.copy(
                medicine = it.medicine.copy(
                    symptoms = symptoms.map {list -> list.first },
                    symptomsVerification = symptoms.map { list -> list.second },
                    diseases = diseases.map { list -> list.first },
                    diseasesVerification = diseases.map { list -> list.second },
                    forms = forms.map { list -> list.first },
                    formsVerification = forms.map { list -> list.second },
                    whoms = whoms.map { list -> list.first },
                    whomsVerification = whoms.map { list -> list.second },
                    locations = locations.map { list -> list.first },
                    locationsVerification = locations.map { list -> list.second },
                )
            )
        }

        val medicine = _medicineState.value.medicine
        insertMedicineUseCase(medicine)
    }
}