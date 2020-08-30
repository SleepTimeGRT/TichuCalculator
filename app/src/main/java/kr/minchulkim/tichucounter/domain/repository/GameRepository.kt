package kr.minchulkim.tichucounter.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.minchulkim.tichucounter.entity.Game

interface GameRepository {
    suspend fun addGame(game: Game)
    suspend fun deleteGame(gameId: Long)
    suspend fun clearGames()
    fun getGameListFlow(): Flow<List<Game>>
}