package kr.minchulkim.tichucalculator.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kr.minchulkim.tichucalculator.data.GameRepoImpl
import kr.minchulkim.tichucalculator.data.room.AppDatabase
import kr.minchulkim.tichucalculator.data.room.GameDao
import kr.minchulkim.tichucalculator.domain.repository.GameRepository

@Module
@InstallIn(ApplicationComponent::class)
object RoomModule {
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return AppDatabase.newInstance(context.applicationContext)
    }

    @Provides
    fun provideGameDao(appDatabase: AppDatabase):GameDao{
        return appDatabase.gameDao()
    }
}

@Module
@InstallIn(ApplicationComponent::class)
abstract class GameRepositoryModule {

    @Binds
    abstract fun bindGameRepository(
        gameRepoImpl: GameRepoImpl
    ): GameRepository
}