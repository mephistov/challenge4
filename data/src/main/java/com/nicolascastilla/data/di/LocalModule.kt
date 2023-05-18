package com.nicolascastilla.data.di

import com.nicolascastilla.data.ReminderRepositoryImpl
import com.nicolascastilla.domain.repositories.ReminderRepository
import com.nicolascastilla.domain.usecases.ReminderUseCaseImpl
import com.nicolascastilla.domain.usecases.interfaces.ReminderUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AplicationModule {

    @Binds
    abstract fun bindsReminderRepository(repoImpl: ReminderRepositoryImpl): ReminderRepository

    @Binds
    abstract fun bindsAddReminderUseCase(addReminderUseCaseImpl: ReminderUseCaseImpl): ReminderUseCase



}