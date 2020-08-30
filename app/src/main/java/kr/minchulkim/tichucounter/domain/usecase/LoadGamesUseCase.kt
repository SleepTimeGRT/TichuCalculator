package kr.minchulkim.tichucounter.domain.usecase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kr.minchulkim.tichucounter.domain.repository.GameRepository
import kr.minchulkim.tichucounter.entity.Game
import javax.inject.Inject


@ExperimentalCoroutinesApi
class LoadGamesUseCase @Inject constructor(private val gameRepository: GameRepository) :
    UseCase<Unit, Flow<List<Game>>> {

    override suspend fun run(params: Unit) = gameRepository.getGameListFlow()
}