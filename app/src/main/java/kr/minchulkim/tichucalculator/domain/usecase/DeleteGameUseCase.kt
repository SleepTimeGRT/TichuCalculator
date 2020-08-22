package kr.minchulkim.tichucalculator.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.minchulkim.tichucalculator.domain.repository.GameRepository

class DeleteGameUseCase(private val gameRepository: GameRepository) : UseCase<Long, Unit> {

    override suspend fun run(params: Long) = withContext(Dispatchers.Default) {
        gameRepository.deleteGame(params)
    }

}