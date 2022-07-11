package br.com.zup.desafiorickandmorty.data.datasource.local

import android.content.Context
import androidx.room.*
import br.com.zup.desafiorickandmorty.data.datasource.local.dao.RickAndMortyDAO
import br.com.zup.desafiorickandmorty.data.model.Result

@Database(entities = [Result::class], version = 3)
@TypeConverters(Converters::class)
abstract class RickAndMortyDatabase : RoomDatabase() {
    abstract fun rickAndMorty(): RickAndMortyDAO

    companion object {
        @Volatile
        private var INSTANCE: RickAndMortyDatabase? = null

        fun getDatabase(context: Context): RickAndMortyDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RickAndMortyDatabase::class.java,
                    "filme_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}