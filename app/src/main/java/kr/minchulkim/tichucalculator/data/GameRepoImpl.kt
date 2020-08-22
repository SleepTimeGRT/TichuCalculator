package kr.minchulkim.tichucalculator.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kr.minchulkim.tichucalculator.data.room.GameDao
import kr.minchulkim.tichucalculator.domain.repository.GameRepository
import kr.minchulkim.tichucalculator.entity.Game

class GameRepoImpl(private val gameDao: GameDao) : GameRepository {

    override suspend fun addGame(game: Game) = withContext(Dispatchers.IO) {
        gameDao.insertGame(game)
    }

    override suspend fun deleteGame(gameId: Long) = withContext(Dispatchers.IO) {
        gameDao.deleteGame(gameId)
    }

    override suspend fun clearGames() = withContext(Dispatchers.IO) {
        gameDao.deleteAll()
    }

    override suspend fun getGameListFlow(): Flow<List<Game>> = withContext(Dispatchers.IO) {
        gameDao.loadAllGames()
    }
}