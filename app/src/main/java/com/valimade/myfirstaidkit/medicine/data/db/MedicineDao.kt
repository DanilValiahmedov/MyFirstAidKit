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

    @Query("""
    SELECT * FROM MedicineData 
    WHERE 
        (:verificationName IS NULL OR verificationName LIKE '%' || :verificationName || '%') AND
        (:symptom IS NULL OR symptomsVerification LIKE '%' || :symptom || '%') AND
        (:disease IS NULL OR diseasesVerification LIKE '%' || :disease || '%') AND
        (:form IS NULL OR formsVerification LIKE '%' || :form || '%') AND
        (:forWhom IS NULL OR forWhomsVerification LIKE '%' || :forWhom || '%') AND
        (:location IS NULL OR locationsVerification LIKE '%' || :location || '%')
""")
    suspend fun searchMedicine(
        verificationName: String?,
        symptom: String?,
        disease: String?,
        form: String?,
        forWhom: String?,
        location: String?,
    ): List<MedicineData>

    @Query("DELETE FROM MedicineData WHERE id = :id")
    suspend fun deleteMedicineDataById(id: Int)
}
