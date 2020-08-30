package kr.minchulkim.tichucounter.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kr.minchulkim.tichucounter.data.room.GameDao
import kr.minchulkim.tichucounter.domain.repository.GameRepository
import kr.minchulkim.tichucounter.entity.Game
import javax.inject.Inject

class GameRepoImpl @Inject constructor(private val gameDao: GameDao) : GameRepository {

    override suspend fun addGame(game: Game) =
        withContext(Dispatchers.IO) { gameDao.insertGame(game) }

    override suspend fun deleteGame(gameId: Long) = withContext(Dispatchers.IO) {
        gameDao.deleteGame(gameId)
    }

    override suspend fun clearGames() = withContext(Dispatchers.IO) { gameDao.deleteAll() }

    override fun getGameListFlow(): Flow<List<Game>> = gameDao.loadAllGames().flowOn(Dispatchers.IO)
}