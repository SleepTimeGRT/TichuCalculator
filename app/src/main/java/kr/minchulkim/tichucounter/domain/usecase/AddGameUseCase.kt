package kr.minchulkim.tichucounter.domain.usecase

import kr.minchulkim.tichucounter.domain.repository.GameRepository
import kr.minchulkim.tichucounter.entity.Game
import javax.inject.Inject

class AddGameUseCase @Inject constructor(
    private val gameRepository: GameRepository
) : UseCase<Game, Unit> {

    override suspend fun run(params: Game) = gameRepository.addGame(params)

}