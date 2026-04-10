package com.valimade.myfirstaidkit.data.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.valimade.myfirstaidkit.data.db.dao.CharacteristicDao
import com.valimade.myfirstaidkit.data.db.entities.Disease
import com.valimade.myfirstaidkit.data.db.entities.Form
import com.valimade.myfirstaidkit.data.db.entities.Location
import com.valimade.myfirstaidkit.data.db.entities.Symptom
import com.valimade.myfirstaidkit.data.db.entities.Whom

@Database(
    entities = [Symptom::class, Disease::class, Form::class, Whom::class, Location::class],
    version = 1,
)
abstract class CharacteristicDatabase : RoomDatabase() {
    abstract fun dao(): CharacteristicDao
}