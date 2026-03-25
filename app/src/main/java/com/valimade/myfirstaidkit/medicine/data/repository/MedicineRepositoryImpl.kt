package com.valimade.myfirstaidkit.medicine.data.repository

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.valimade.myfirstaidkit.medicine.data.assets.InitialData
import com.valimade.myfirstaidkit.medicine.data.db.Database
import com.valimade.myfirstaidkit.medicine.data.db.entities.Disease
import com.valimade.myfirstaidkit.medicine.data.db.entities.Form
import com.valimade.myfirstaidkit.medicine.data.db.entities.MedicineData
import com.valimade.myfirstaidkit.medicine.data.db.entities.Symptom
import com.valimade.myfirstaidkit.medicine.data.db.entities.Whom
import com.valimade.myfirstaidkit.medicine.domain.model.Characteristic
import com.valimade.myfirstaidkit.medicine.domain.model.CharacteristicItem
import com.valimade.myfirstaidkit.medicine.domain.repository.MedicineRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MedicineRepositoryImpl(
    private val context: Context,
    private val initialData: InitialData,
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
                    }
                }
            })
            .build()
    }

    private val dao by lazy { database.dao() }
    private val medicineDao by lazy { database.medicineDao() }

    override suspend fun insertCharacteristic(item: CharacteristicItem) {
        when (item) {
            is CharacteristicItem.SymptomItem -> dao.insertSymptom(item.data)
            is CharacteristicItem.DiseaseItem -> dao.insertDisease(item.data)
            is CharacteristicItem.FormItem -> dao.insertForm(item.data)
            is CharacteristicItem.WhomItem -> dao.insertWhom(item.data)
        }
    }

    override suspend fun getAllCharacteristic(characteristic: Characteristic): List<CharacteristicItem> {
        return when(characteristic) {
            Characteristic.SYMPTOM -> dao.getAllSymptoms().map { CharacteristicItem.SymptomItem(it) }
            Characteristic.DISEASES -> dao.getAllDiseases().map { CharacteristicItem.DiseaseItem(it) }
            Characteristic.FORM -> dao.getAllForms().map { CharacteristicItem.FormItem(it) }
            Characteristic.WHOM -> dao.getAllWhoms().map { CharacteristicItem.WhomItem(it) }
        }
    }

    override suspend fun getCharacteristicById(characteristic: Characteristic, id: Int): CharacteristicItem? {
        return when (characteristic) {
            Characteristic.SYMPTOM -> dao.getSymptomById(id)?.let { CharacteristicItem.SymptomItem(it) }
            Characteristic.DISEASES -> dao.getDiseaseById(id)?.let { CharacteristicItem.DiseaseItem(it) }
            Characteristic.FORM -> dao.getFormById(id)?.let { CharacteristicItem.FormItem(it) }
            Characteristic.WHOM -> dao.getWhomById(id)?.let { CharacteristicItem.WhomItem(it) }
        }
    }

    override suspend fun existsCharacteristicByVerificationName(characteristic: Characteristic, verificationName: String): Boolean {
        return when (characteristic) {
            Characteristic.SYMPTOM -> dao.existsSymptomByVerificationName(verificationName)
            Characteristic.DISEASES -> dao.existsDiseaseByVerificationName(verificationName)
            Characteristic.FORM -> dao.existsFormByVerificationName(verificationName)
            Characteristic.WHOM -> dao.existsWhomByVerificationName(verificationName)
        }
    }

    override suspend fun deleteCharacteristicById(characteristic: Characteristic, id: Int) {
         when (characteristic) {
            Characteristic.SYMPTOM -> dao.deleteSymptomById(id)
            Characteristic.DISEASES -> dao.deleteDiseaseById(id)
            Characteristic.FORM -> dao.deleteFormById(id)
            Characteristic.WHOM -> dao.deleteWhomById(id)
        }
    }

    override suspend fun insertMedicine(medicine: MedicineData) = medicineDao.insertMedicine(medicine)

    override suspend fun getMedicineById(id: Int): MedicineData? = medicineDao.getMedicineById(id)

    override suspend fun getByVerificationName(verificationName: String): MedicineData? = medicineDao.getByVerificationName(verificationName)

    override suspend fun getBySymptom(symptom: String): List<MedicineData> = medicineDao.getBySymptom(symptom)

    override suspend fun getByDisease(disease: String): List<MedicineData> = medicineDao.getByDisease(disease)

    override suspend fun getByForm(form: String): List<MedicineData> = medicineDao.getByForm(form)

    override suspend fun getByWhom(whom: String): List<MedicineData> = medicineDao.getByWhom(whom)

    override suspend fun deleteMedicineDataById(id: Int) = medicineDao.deleteMedicineDataById(id)

}