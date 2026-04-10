package com.valimade.myfirstaidkit.data.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.valimade.myfirstaidkit.data.db.dao.MedicineDao
import com.valimade.myfirstaidkit.data.db.entities.MedicineData
import com.valimade.myfirstaidkit.data.db.entities.MedicineFts

@Database(
    entities = [MedicineData::class, MedicineFts::class],
    version = 1,
)
abstract class MedicineDatabase : RoomDatabase() {
    abstract fun dao(): MedicineDao
}