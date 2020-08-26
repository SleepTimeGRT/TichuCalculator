package kr.minchulkim.tichucalculator.data

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kr.minchulkim.tichucalculator.data.room.GameDao
import kr.minchulkim.tichucalculator.domain.repository.GameRepository
import kr.minchulkim.tichucalculator.entity.Game
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