package kr.minchulkim.tichucalculator.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kr.minchulkim.tichucalculator.domain.repository.GameRepository
import kr.minchulkim.tichucalculator.entity.Game
import javax.inject.Inject


@ExperimentalCoroutinesApi
class LoadGamesUseCase @Inject constructor(private val gameRepository: GameRepository) :
    UseCase<Unit, Flow<List<Game>>> {

    override suspend fun run(params: Unit) = gameRepository.getGameListFlow()
}