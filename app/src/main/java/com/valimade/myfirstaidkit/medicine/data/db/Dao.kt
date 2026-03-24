package com.valimade.myfirstaidkit.medicine.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.valimade.myfirstaidkit.medicine.data.db.entities.Disease
import com.valimade.myfirstaidkit.medicine.data.db.entities.Form
import com.valimade.myfirstaidkit.medicine.data.db.entities.Symptom
import com.valimade.myfirstaidkit.medicine.data.db.entities.Whom

@Dao
interface Dao {

    // Symptom
    @Insert
    suspend fun insertSymptom(symptom: Symptom)

    @Query("SELECT * FROM Symptom")
    suspend fun getAllSymptoms(): List<Symptom>

    @Query("SELECT * FROM Symptom WHERE id = :id")
    suspend fun getSymptomById(id: Int): Symptom?

    @Query("SELECT EXISTS(SELECT 1 FROM Symptom WHERE verificationName = :verificationName)")
    suspend fun existsSymptomByVerificationName(verificationName: String): Boolean

    @Query("DELETE FROM Symptom WHERE id = :id")
    suspend fun deleteSymptomById(id: Int)

    // Disease
    @Insert
    suspend fun insertDisease(disease: Disease)

    @Query("SELECT * FROM Disease")
    suspend fun getAllDiseases(): List<Disease>

    @Query("SELECT * FROM Disease WHERE id = :id")
    suspend fun getDiseaseById(id: Int): Disease?

    @Query("SELECT EXISTS(SELECT 1 FROM Disease WHERE verificationName = :verificationName)")
    suspend fun existsDiseaseByVerificationName(verificationName: String): Boolean

    @Query("DELETE FROM Disease WHERE id = :id")
    suspend fun deleteDiseaseById(id: Int)

    // Form
    @Insert
    suspend fun insertForm(form: Form)

    @Query("SELECT * FROM Form")
    suspend fun getAllForms(): List<Form>

    @Query("SELECT * FROM Form WHERE id = :id")
    suspend fun getFormById(id: Int): Form?

    @Query("SELECT EXISTS(SELECT 1 FROM Form WHERE verificationName = :verificationName)")
    suspend fun existsFormByVerificationName(verificationName: String): Boolean

    @Query("DELETE FROM Form WHERE id = :id")
    suspend fun deleteFormById(id: Int)

    // Whom
    @Insert
    suspend fun insertWhom(whom: Whom)

    @Query("SELECT * FROM Whom")
    suspend fun getAllWhoms(): List<Whom>

    @Query("SELECT * FROM Whom WHERE id = :id")
    suspend fun getWhomById(id: Int): Whom?

    @Query("SELECT EXISTS(SELECT 1 FROM Whom WHERE verificationName = :verificationName)")
    suspend fun existsWhomByVerificationName(verificationName: String): Boolean

    @Query("DELETE FROM Whom WHERE id = :id")
    suspend fun deleteWhomById(id: Int)

}