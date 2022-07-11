package br.com.zup.desafiorickandmorty.data.datasource.local.dao

import androidx.room.*
import br.com.zup.desafiorickandmorty.data.model.Result

@Dao
interface RickAndMortyDAO {
    @Query("SELECT * FROM personageRickAndMorty ORDER BY name ASC")
    fun getAllRickAndMorty(): List<Result>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRickAndMorty(result: Result)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllRickAndMorty(listResult: List<Result>)

    @Query("SELECT * FROM personageRickAndMorty WHERE isFavorite = 1")
    fun getAllPersonageFavorited(): List<Result>

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updatePersonageFavorite(result: Result)
}