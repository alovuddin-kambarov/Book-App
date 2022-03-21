package uz.coder.mvpexample.room.db

import android.content.Context
import androidx.room.*
import uz.coder.mvpexample.models.books.books.BookX
import uz.coder.mvpexample.room.db.convertors.DataConvertor

@Database(entities = [BookX::class], version = 1)
@TypeConverters(DataConvertor::class, DataConvertor::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dao(): DatabaseService

    companion object {
        private var instens: AppDatabase? = null

        @Synchronized
        fun getInstants(context: Context): AppDatabase {
            if (instens == null) {
                instens = Room.databaseBuilder(context, AppDatabase::class.java, "room")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration().allowMainThreadQueries()
                    .build()
            }

            return instens!!

        }

    }



}