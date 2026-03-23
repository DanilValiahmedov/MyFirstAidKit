package com.valimade.myfirstaidkit.medicine.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MedicineData(
    @PrimaryKey val id: Int,
    val name: String,
    val verificationName: String, //Название без пробелов и большом регистре
    val symptoms: String,
    val diseases: String,
    val forms: String,
    val forWhoms: String,
    val expirationDate: Int,
    val comment: String,
)