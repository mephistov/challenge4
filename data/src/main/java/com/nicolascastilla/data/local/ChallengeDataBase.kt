package com.nicolascastilla.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.nicolascastilla.data.local.dao.ChallengeDao
import com.nicolascastilla.data.local.entities.ReminderModel

val MIGRATION_1_2 = object : Migration(1,2){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE remainder_table ADD COLUMN status TEXT NOT NULL DEFAULT 'saved'")
    }
}
@Database(entities = [
    ReminderModel::class,
], version = 2,
)
abstract class ChallengeDataBase():RoomDatabase() {
    abstract fun getChalengeDao(): ChallengeDao


}