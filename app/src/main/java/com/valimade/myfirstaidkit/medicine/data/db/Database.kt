package com.valimade.myfirstaidkit.medicine.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.valimade.myfirstaidkit.medicine.data.db.entities.Disease
import com.valimade.myfirstaidkit.medicine.data.db.entities.Form
import com.valimade.myfirstaidkit.medicine.data.db.entities.Symptom
import com.valimade.myfirstaidkit.medicine.data.db.entities.Whom

@Database(
    entities = [Symptom::class, Disease::class, Form::class, Whom::class],
    version = 1
)
abstract class Database : RoomDatabase() {
    abstract fun dao(): Dao
}