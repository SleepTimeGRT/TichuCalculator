package kr.minchulkim.tichucounter.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kr.minchulkim.tichucounter.data.GameRepoImpl
import kr.minchulkim.tichucounter.data.room.AppDatabase
import kr.minchulkim.tichucounter.data.room.GameDao
import kr.minchulkim.tichucounter.domain.repository.GameRepository
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RoomModule {

    @Singleton
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