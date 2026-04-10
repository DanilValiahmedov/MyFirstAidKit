package com.valimade.myfirstaidkit.data.repository

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.valimade.myfirstaidkit.data.assets.InitialData
import com.valimade.myfirstaidkit.data.db.database.CharacteristicDatabase
import com.valimade.myfirstaidkit.data.mapper.CharacteristicDataMapper
import com.valimade.myfirstaidkit.domain.model.Characteristic
import com.valimade.myfirstaidkit.domain.model.CharacteristicMedicine
import com.valimade.myfirstaidkit.domain.repository.CharacteristicRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacteristicRepositoryImpl(
    private val context: Context,
    private val initialData: InitialData,
    private val mapper: CharacteristicDataMapper,
): CharacteristicRepository {

    private val database: CharacteristicDatabase by lazy {
        Room.databaseBuilder(
            context,
            CharacteristicDatabase::class.java,
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

    override suspend fun insertCharacteristic(characteristicMedicine: CharacteristicMedicine) {
        when (characteristicMedicine.characteristic) {
            Characteristic.SYMPTOM -> {
                val symptom = mapper.symptomFromDomainToData(characteristicMedicine)
                dao.insertSymptom(symptom)
            }
            Characteristic.DISEASES -> {
                val disease = mapper.diseaseFromDomainToData(characteristicMedicine)
                dao.insertDisease(disease)
            }
            Characteristic.FORM -> {
                val form =  mapper.formFromDomainToData(characteristicMedicine)
                dao.insertForm(form)
            }
            Characteristic.WHOM -> {
                val whom =  mapper.whomFromDomainToData(characteristicMedicine)
                dao.insertWhom(whom)
            }
            Characteristic.LOCATION -> {
                val location =  mapper.locationFromDomainToData(characteristicMedicine)
                dao.insertLocation(location)
            }
        }
    }

    override suspend fun getAllCharacteristics(characteristic: Characteristic): List<CharacteristicMedicine> {
        return when(characteristic) {
            Characteristic.SYMPTOM -> dao.getAllSymptoms().map { mapper.symptomFromDataToDomain(it) }
            Characteristic.DISEASES -> dao.getAllDiseases().map { mapper.diseaseFromDataToDomain(it) }
            Characteristic.FORM -> dao.getAllForms().map { mapper.formFromDataToDomain(it) }
            Characteristic.WHOM -> dao.getAllWhoms().map { mapper.whomFromDataToDomain(it) }
            Characteristic.LOCATION -> dao.getAllLocations().map { mapper.locationFromDataToDomain(it) }
        }
    }

    override suspend fun existsCharacteristicByVerificationName(
        characteristic: Characteristic, verificationName: String
    ): Boolean {
        return when (characteristic) {
            Characteristic.SYMPTOM -> dao.existsSymptomByVerificationName(verificationName)
            Characteristic.DISEASES -> dao.existsDiseaseByVerificationName(verificationName)
            Characteristic.FORM -> dao.existsFormByVerificationName(verificationName)
            Characteristic.WHOM -> dao.existsWhomByVerificationName(verificationName)
            Characteristic.LOCATION -> dao.existsLocationByVerificationName(verificationName)
        }
    }


    override suspend fun deleteCharacteristicByVerificationName(
        characteristicMedicine: CharacteristicMedicine
    ) {
        val characteristic = characteristicMedicine.characteristic
        val verificationName = characteristicMedicine.verificationName
        when (characteristic) {
            Characteristic.SYMPTOM -> dao.deleteSymptomByVerificationName(verificationName)
            Characteristic.DISEASES -> dao.deleteDiseaseByVerificationName(verificationName)
            Characteristic.FORM -> dao.deleteFormByVerificationName(verificationName)
            Characteristic.WHOM -> dao.deleteWhomByVerificationName(verificationName)
            Characteristic.LOCATION -> dao.deleteLocationByVerificationName(verificationName)
        }
    }


}
