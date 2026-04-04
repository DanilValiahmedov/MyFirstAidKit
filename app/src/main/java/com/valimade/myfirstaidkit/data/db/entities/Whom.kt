package com.valimade.myfirstaidkit.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Whom(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val verificationName: String, //Название без пробелов и большом регистре
)