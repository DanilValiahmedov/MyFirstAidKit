package com.valimade.myfirstaidkit.medicine.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MedicineData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val verificationName: String,
    val symptoms: String,
    val symptomsVerification: String,
    val diseases: String,
    val diseasesVerification: String,
    val forms: String,
    val formsVerification: String,
    val forWhoms: String,
    val forWhomsVerification: String,
    val locations: String,
    val locationsVerification: String,
    val expirationDate: Int,
    val comment: String,
)