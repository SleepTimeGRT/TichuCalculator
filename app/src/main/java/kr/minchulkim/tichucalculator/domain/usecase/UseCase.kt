package kr.minchulkim.tichucalculator.domain.usecase

import io.reactivex.Flowable

interface UseCase<PARAMS, RESULT> {

    suspend fun run(params: PARAMS): RESULT

}