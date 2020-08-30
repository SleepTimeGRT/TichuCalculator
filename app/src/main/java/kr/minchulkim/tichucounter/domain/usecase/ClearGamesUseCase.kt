package kr.minchulkim.tichucounter.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.minchulkim.tichucounter.domain.repository.GameRepository
import javax.inject.Inject

class ClearGamesUseCase @Inject constructor(private val gameRepository: GameRepository) : UseCase<Unit, Unit> {

    override suspend fun run(params: Unit) =
        withContext(Dispatchers.Default) {
            gameRepository.clearGames()
        }

}