package kr.minchulkim.tichucalculator.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kr.minchulkim.tichucalculator.domain.repository.GameRepository
import kr.minchulkim.tichucalculator.entity.Game


class LoadGamesUseCase(private val gameRepository: GameRepository) :
    UseCase<Unit, Flow<List<Game>>> {

    override suspend fun run(params: Unit) = withContext(Dispatchers.Default) {
        gameRepository.getGameListFlow()
    }
}