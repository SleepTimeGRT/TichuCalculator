package kr.minchulkim.tichucounter.domain.usecase

interface UseCase<PARAMS, RESULT> {

    suspend fun run(params: PARAMS): RESULT

}