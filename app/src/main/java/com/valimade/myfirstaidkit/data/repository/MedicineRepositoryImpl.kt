package com.valimade.myfirstaidkit.data.repository

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.valimade.myfirstaidkit.data.assets.InitialData
import com.valimade.myfirstaidkit.data.db.Database
import com.valimade.myfirstaidkit.data.mapper.MedicineDataMapper
import com.valimade.myfirstaidkit.domain.model.Characteristic
import com.valimade.myfirstaidkit.domain.model.CharacteristicItem
import com.valimade.myfirstaidkit.domain.model.Medicine
import com.valimade.myfirstaidkit.domain.repository.MedicineRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MedicineRepositoryImpl(
    private val context: Context,
    private val initialData: InitialData,
    private val medicineMapper: MedicineDataMapper,
): MedicineRepository {

    private val database: Database by lazy {
        Room.databaseBuilder(
            context,
            Database::class.java,
            "app-database"
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        //Внедрение первоначальных (дефолтных данных)
                        initialData.symptoms.forEach { dao.insertSymptom(it) }
                        initialData.diseases.forEach { dao.insertDisease(it) }
                        initialData.forms.forEach { dao.insertForm(it) }
                        initialData.whoms.forEach { dao.insertWhom(it) }
                        initialData.locations.forEach { dao.insertLocation(it) }
                    }
                }
            })
            .build()
    }

    private val dao by lazy { database.dao() }
    private val medicineDao by lazy { database.medicineDao() }

    override suspend fun insertItem(item: CharacteristicItem) {
        when (item) {
            is CharacteristicItem.MedicineItem -> {
                val medicineData = medicineMapper.fromDomainToData(item.data)
                medicineDao.insertMedicine(medicineData)
            }
            is CharacteristicItem.SymptomItem -> dao.insertSymptom(item.data)
            is CharacteristicItem.DiseaseItem -> dao.insertDisease(item.data)
            is CharacteristicItem.FormItem -> dao.insertForm(item.data)
            is CharacteristicItem.WhomItem -> dao.insertWhom(item.data)
            is CharacteristicItem.LocationItem -> dao.insertLocation(item.data)
        }
    }

    override suspend fun getAllItem(characteristic: Characteristic): List<CharacteristicItem> {
        return when(characteristic) {
            Characteristic.MEDICINE -> medicineDao.getAllMedicine().map {
                val medicineDomain = medicineMapper.fromDataToDomain(it)
                CharacteristicItem.MedicineItem(medicineDomain)
            }
            Characteristic.SYMPTOM -> dao.getAllSymptoms().map { CharacteristicItem.SymptomItem(it) }
            Characteristic.DISEASES -> dao.getAllDiseases().map { CharacteristicItem.DiseaseItem(it) }
            Characteristic.FORM -> dao.getAllForms().map { CharacteristicItem.FormItem(it) }
            Characteristic.WHOM -> dao.getAllWhoms().map { CharacteristicItem.WhomItem(it) }
            Characteristic.LOCATION -> dao.getAllLocations().map { CharacteristicItem.LocationItem(it) }
        }
    }

    override suspend fun getItemById(characteristic: Characteristic, id: Int): CharacteristicItem? {
        return when (characteristic) {
            Characteristic.MEDICINE -> medicineDao.getMedicineById(id)?.let {
                val medicineDomain = medicineMapper.fromDataToDomain(it)
                CharacteristicItem.MedicineItem(medicineDomain)
            }
            Characteristic.SYMPTOM -> dao.getSymptomById(id)?.let { CharacteristicItem.SymptomItem(it) }
            Characteristic.DISEASES -> dao.getDiseaseById(id)?.let { CharacteristicItem.DiseaseItem(it) }
            Characteristic.FORM -> dao.getFormById(id)?.let { CharacteristicItem.FormItem(it) }
            Characteristic.WHOM -> dao.getWhomById(id)?.let { CharacteristicItem.WhomItem(it) }
            Characteristic.LOCATION -> dao.getLocationById(id)?.let { CharacteristicItem.LocationItem(it) }
        }
    }

    override suspend fun existsCharacteristicByVerificationName(characteristic: Characteristic, verificationName: String): Boolean {
        return when (characteristic) {
            Characteristic.MEDICINE -> false
            Characteristic.SYMPTOM -> dao.existsSymptomByVerificationName(verificationName)
            Characteristic.DISEASES -> dao.existsDiseaseByVerificationName(verificationName)
            Characteristic.FORM -> dao.existsFormByVerificationName(verificationName)
            Characteristic.WHOM -> dao.existsWhomByVerificationName(verificationName)
            Characteristic.LOCATION -> dao.existsLocationByVerificationName(verificationName)
        }
    }

    override suspend fun updateMedicine(medicineItem: CharacteristicItem) {
        if(medicineItem is CharacteristicItem.MedicineItem ) {
            val medicineData = medicineMapper.fromDomainToData(medicineItem.data)
            medicineDao.insertMedicine(medicineData)
        }
    }

    override suspend fun deleteItemById(characteristic: Characteristic, id: Int) {
         when (characteristic) {
            Characteristic.MEDICINE -> medicineDao.deleteMedicineDataById(id)
            Characteristic.SYMPTOM -> dao.deleteSymptomById(id)
            Characteristic.DISEASES -> dao.deleteDiseaseById(id)
            Characteristic.FORM -> dao.deleteFormById(id)
            Characteristic.WHOM -> dao.deleteWhomById(id)
            Characteristic.LOCATION -> dao.deleteLocationById(id)
        }
    }

    override suspend fun getMedicineByCharacteristic(
        verificationName: String?,
        symptoms: List<String>?,
        diseases: List<String>?,
        forms: List<String>?,
        forWhoms: List<String>?,
        locations: List<String>?,
    ): List<Medicine> {
        val symptomsQuery = symptoms.toFtsQuery()
        val diseasesQuery = diseases.toFtsQuery()
        val formsQuery = forms.toFtsQuery()
        val forWhomsQuery = forWhoms.toFtsQuery()
        val locationsQuery = locations.toFtsQuery()

        val medicineListData = medicineDao.searchMedicine(
            verificationName = verificationName,
            symptoms = symptomsQuery,
            diseases = diseasesQuery,
            forms = formsQuery,
            forWhoms = forWhomsQuery,
            locations = locationsQuery,
        )
        return medicineListData.map{
            medicineMapper.fromDataToDomain(it)
        }
    }

}

fun List<String>?.toFtsQuery(): String? {
    return this
        ?.filter { it.isNotBlank() }
        ?.joinToString(" ") { "+$it" }
        ?.takeIf { it.isNotBlank() }
}