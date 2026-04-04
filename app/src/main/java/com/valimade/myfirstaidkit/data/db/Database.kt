package com.valimade.myfirstaidkit.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.valimade.myfirstaidkit.data.db.entities.Disease
import com.valimade.myfirstaidkit.data.db.entities.Form
import com.valimade.myfirstaidkit.data.db.entities.Location
import com.valimade.myfirstaidkit.data.db.entities.MedicineData
import com.valimade.myfirstaidkit.data.db.entities.MedicineFts
import com.valimade.myfirstaidkit.data.db.entities.Symptom
import com.valimade.myfirstaidkit.data.db.entities.Whom

@Database(
    entities = [Symptom::class, Disease::class, Form::class, Whom::class, Location::class, MedicineData::class, MedicineFts::class],
    version = 1,
)
abstract class Database : RoomDatabase() {
    abstract fun dao(): Dao
    abstract fun medicineDao(): MedicineDao
}