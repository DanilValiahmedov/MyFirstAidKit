package com.valimade.myfirstaidkit.data.db.entities

import androidx.room.Entity
import androidx.room.Fts4

@Fts4(contentEntity = MedicineData::class)
@Entity(tableName = "MedicineFts")
data class MedicineFts(
    val verificationName: String,
    val symptomsVerification: String,
    val diseasesVerification: String,
    val formsVerification: String,
    val forWhomsVerification: String,
    val locationsVerification: String,
)