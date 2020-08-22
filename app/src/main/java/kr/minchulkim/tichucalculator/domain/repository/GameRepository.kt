package kr.minchulkim.tichucalculator.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.minchulkim.tichucalculator.entity.Game

interface GameRepository {
    suspend fun addGame(game: Game)
    suspend fun deleteGame(gameId: Long)
    suspend fun clearGames()
    suspend fun getGameListFlow(): Flow<List<Game>>
}