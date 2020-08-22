package kr.minchulkim.tichucalculator.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kr.minchulkim.tichucalculator.entity.Game

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(vararg games: Game)

    @Delete
    suspend fun deleteGames(vararg games: Game)

    @Query("DELETE FROM game WHERE id = :id")
    suspend fun deleteGame(id: Long)

    @Query("DELETE FROM game")
    suspend fun deleteAll()

    @Query("SELECT * FROM game")
    fun loadAllGames(): Flow<List<Game>>
}