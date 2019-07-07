package com.aditya.vplayer.repo.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalDatabaseSchema.VideoMetaEntity::class], version = 1)
abstract class VPlayerDatabase : RoomDatabase() {
    abstract fun videoDetailsDao(): LocalDatabaseSchema.VideoMetaListDao

}