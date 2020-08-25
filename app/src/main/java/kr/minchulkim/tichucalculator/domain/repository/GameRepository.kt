package kr.minchulkim.tichucalculator.domain.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import kr.minchulkim.tichucalculator.entity.Game

interface GameRepository {
    suspend fun addGame(game: Game)
    fun addGame2(game: Game):Completable
    suspend fun deleteGame(gameId: Long)
    suspend fun clearGames()
    fun getGameListFlow(): Flow<List<Game>>
    fun getGameListObservable(): Observable<List<Game>>
}