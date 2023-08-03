package sso.hyeon.caffeinbody.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Drinks::class], version = 1)
abstract class DrinksDatabase: RoomDatabase() {
    abstract fun drinksDao(): DrinksDao

    companion object {
        private var instance: DrinksDatabase? = null

        @Synchronized
        fun getInstance(context: Context): DrinksDatabase? {
            if (instance == null){
                synchronized(DrinksDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DrinksDatabase::class.java,
                        "drink-database"
                    ).build()
                }
            }
            return instance
        }
    }
}