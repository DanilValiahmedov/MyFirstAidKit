package com.valimade.myfirstaidkit.medicine.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MedicineData(
    @PrimaryKey val id: Int,
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
    val expirationDate: Int,
    val comment: String,
)