package com.valimade.myfirstaidkit.medicine.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.valimade.myfirstaidkit.medicine.data.db.entities.MedicineData

@Dao
interface MedicineDao {

    @Insert
    suspend fun insertMedicine(medicine: MedicineData)

    @Query("SELECT * FROM MedicineData")
    suspend fun getAllMedicine(): List<MedicineData>

    @Query("SELECT * FROM MedicineData WHERE id = :id")
    suspend fun getMedicineById(id: Int): MedicineData?

    @Query("SELECT * FROM MedicineData WHERE verificationName = :verificationName")
    suspend fun getMedicineByVerificationName(verificationName: String): List<MedicineData>

    @Query("SELECT * FROM MedicineData WHERE symptoms LIKE '%' || :symptom || '%'")
    suspend fun getMedicineBySymptom(symptom: String): List<MedicineData>

    @Query("SELECT * FROM MedicineData WHERE diseases LIKE '%' || :disease || '%'")
    suspend fun getMedicineByDisease(disease: String): List<MedicineData>

    @Query("SELECT * FROM MedicineData WHERE forms LIKE '%' || :form || '%'")
    suspend fun getMedicineByForm(form: String): List<MedicineData>

    @Query("SELECT * FROM MedicineData WHERE forWhoms LIKE '%' || :whom || '%'")
    suspend fun getMedicineByWhom(whom: String): List<MedicineData>

    @Query("DELETE FROM MedicineData WHERE id = :id")
    suspend fun deleteMedicineDataById(id: Int)
}
