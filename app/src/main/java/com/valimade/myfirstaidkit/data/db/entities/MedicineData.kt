package com.valimade.myfirstaidkit.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MedicineData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val verificationName: String,
    val symptoms: String? = null,
    val symptomsVerification: String? = null,
    val diseases: String? = null,
    val diseasesVerification: String? = null,
    val forms: String? = null,
    val formsVerification: String? = null,
    val whoms: String? = null,
    val whomsVerification: String? = null,
    val locations: String? = null,
    val locationsVerification: String? = null,
    val expirationDateVisually: String? = null,
    val expirationDate: Long? = null,
    val comment: String? = null,
)