package com.valimade.myfirstaidkit.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.valimade.myfirstaidkit.data.db.entities.MedicineData

@Dao
interface MedicineDao {

    @Insert
    suspend fun insertMedicine(medicine: MedicineData)

    @Query("SELECT * FROM MedicineData")
    suspend fun getAllMedicine(): List<MedicineData>

    @Query("SELECT * FROM MedicineData WHERE id = :id")
    suspend fun getMedicineById(id: Int): MedicineData?

    @Query("""
    SELECT m.*
    FROM MedicineData m
    JOIN MedicineFts fts ON m.id = fts.rowid
    WHERE (:verificationName IS NULL OR fts.verificationName MATCH :verificationName)
      AND (:symptoms IS NULL OR fts.symptomsVerification MATCH :symptoms)
      AND (:diseases IS NULL OR fts.diseasesVerification MATCH :diseases)
      AND (:forms IS NULL OR fts.formsVerification MATCH :forms)
      AND (:forWhoms IS NULL OR fts.forWhomsVerification MATCH :forWhoms)
      AND (:locations IS NULL OR fts.locationsVerification MATCH :locations)
""")
    suspend fun searchMedicine(
        verificationName: String?,
        symptoms: String?,
        diseases: String?,
        forms: String?,
        forWhoms: String?,
        locations: String?
    ): List<MedicineData>

    @Update
    suspend fun updateMedicineData(medicine: MedicineData)

    @Query("DELETE FROM MedicineData WHERE id = :id")
    suspend fun deleteMedicineDataById(id: Int)
}
