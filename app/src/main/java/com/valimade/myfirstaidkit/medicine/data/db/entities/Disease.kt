package com.valimade.myfirstaidkit.medicine.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Disease(
    @PrimaryKey val id: Int,
    val name: String,
    val verificationName: String, //Название без пробелов и большом регистре
)