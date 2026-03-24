package com.valimade.myfirstaidkit.medicine.data.repository

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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
                        fillInitialData()
                    }
                }
            })
            .build()
    }

    private val dao by lazy { database.dao() }
    private val medicineDao by lazy { database.medicineDao() }

    private suspend fun fillInitialData () {

        val symptoms = listOf(
            Symptom(1, "Кашель", "КАШЕЛЬ"),
            Symptom(2, "Насморк", "НАСМОРК"),
            Symptom(3, "Головная боль", "ГОЛОВНАЯБОЛЬ"),
            Symptom(4, "Температура", "ТЕМПЕРАТУРА"),
            Symptom(5, "Боль в горле", "БОЛЬВГОРЛЕ"),
            Symptom(6, "Диарея", "ДИАРЕЯ"),
            Symptom(7, "Тошнота", "ТОШНОТА"),
            Symptom(8, "Боли в мышцах и суставах", "БОЛИВМЫШЦАХИСУСТАВАХ"),
            Symptom(9, "Зубная боль", "ЗУБНАЯБОЛЬ"),
            Symptom(10, "Аллергические реакции", "АЛЛЕРГИЧЕСКИЕРЕАКЦИИ"),
            Symptom(11, "Профилактика простуды", "ПРОФИЛАКТИКАПРОСТУДЫ"),
            Symptom(12, "Укрепление иммунитета", "УКРЕПЛЕНИЕИММУНИТЕТА"),
            Symptom(13, "Отравление", "ОТРАВЛЕНИЕ"),
            Symptom(14, "Сонливость", "СОНЛИВОСТЬ")
        )
        symptoms.forEach { dao.insertSymptom(it) }

        val diseases = listOf(
            Disease(id = 1, name = "Простуда", verificationName = "ПРОСТУДА"),
            Disease(id = 2, name = "ОРВИ", verificationName = "ОРВИ"),
            Disease(id = 3, name = "Грипп", verificationName = "ГРИПП"),
            Disease(id = 4, name = "Аллергия", verificationName = "АЛЛЕРГИЯ"),
            Disease(id = 5, name = "Похмелье", verificationName = "ПОХМЕЛЬЕ"),
            Disease(id = 6, name = "Гастрит", verificationName = "ГАСТРИТ"),
            Disease(id = 7, name = "Мигрень", verificationName = "МИГРЕНЬ")
        )
        diseases.forEach { dao.insertDisease(it) }

        val forms = listOf(
            Form(id = 1, name = "Таблетки", verificationName = "ТАБЛЕТКИ"),
            Form(id = 2, name = "Капсулы", verificationName = "КАПСУЛЫ"),
            Form(id = 3, name = "Сироп", verificationName = "СИРОП"),
            Form(id = 4, name = "Капли", verificationName = "КАПЛИ"),
            Form(id = 5, name = "Мазь", verificationName = "МАЗЬ"),
            Form(id = 6, name = "Свечи", verificationName = "СВЕЧИ"),
            Form(id = 7, name = "Порошок", verificationName = "ПОРОШОК"),
            Form(id = 8, name = "Гель", verificationName = "ГЕЛЬ"),
            Form(id = 9, name = "Ампула", verificationName = "АМПУЛА")
        )
        forms.forEach { dao.insertForm(it) }

        val whoms = listOf(
            Whom(id = 1, name = "Дети", verificationName = "ДЕТИ"),
            Whom(id = 2, name = "Взрослые", verificationName = "ВЗРОСЛЫЕ"),
            Whom(id = 3, name = "Мужчины", verificationName = "МУЖЧИНЫ"),
            Whom(id = 4, name = "Женщины", verificationName = "ЖЕНЩИНЫ")
        )
        whoms.forEach { dao.insertWhom(it) }
    }

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

    override suspend fun insertMedicine(medicine: MedicineData) = medicineDao.insertMedicine(medicine)

    override suspend fun getMedicineById(id: Int): MedicineData? = medicineDao.getMedicineById(id)

    override suspend fun getByVerificationName(verificationName: String): MedicineData? = medicineDao.getByVerificationName(verificationName)

    override suspend fun getBySymptom(symptom: String): List<MedicineData> = medicineDao.getBySymptom(symptom)

    override suspend fun getByDisease(disease: String): List<MedicineData> = medicineDao.getByDisease(disease)

    override suspend fun getByForm(form: String): List<MedicineData> = medicineDao.getByForm(form)

    override suspend fun getByWhom(whom: String): List<MedicineData> = medicineDao.getByWhom(whom)

}