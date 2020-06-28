package com.easycoder.simpletask.core.data.localDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.easycoder.simpletask.BuildConfig
import com.easycoder.simpletask.data.completed_event.CompletedEvent
import com.easycoder.simpletask.data.dataSources.EventModelDao
import com.easycoder.simpletask.data.dataSources.TaskModelDao
import com.easycoder.simpletask.data.event.EventModel
import com.easycoder.simpletask.data.main.model.TaskModel


@Database(
    entities = [TaskModel::class, EventModel::class, CompletedEvent::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskModelDao(): TaskModelDao
    abstract fun eventModelDao(): EventModelDao


    companion object {
        private const val DB_NAME = BuildConfig.DB_NAME


        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Since we didn't alter the table, there's nothing else to do here.
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // database.execSQL("ALTER TABLE Book ADD COLUMN pub_year INTEGER")
                //   database.execSQL("CREATE TABLE IF NOT EXISTS Completed_Event")
            }
        }
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null)
                instance =
                    create(
                        context.applicationContext
                    )

            return instance
        }

        private fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context, AppDatabase::class.java,
                DB_NAME
            )
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build()
        }
    }


}