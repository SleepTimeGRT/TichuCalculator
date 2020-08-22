package kr.minchulkim.tichucalculator.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.minchulkim.tichucalculator.domain.repository.GameRepository

class ClearGamesUseCase(private val gameRepository: GameRepository) : UseCase<Unit, Unit> {

    override suspend fun run(params: Unit) =
        withContext(Dispatchers.Default) {
            gameRepository.clearGames()
        }

}