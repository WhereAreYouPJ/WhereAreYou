package com.whereareyou.di.usecase

import com.whereareyou.domain.repository.ScheduleRepository
import com.whereareyou.domain.repository.SearchLocationRepository
import com.whereareyou.domain.usecase.schedule.GetDailyBriefScheduleUseCase
import com.whereareyou.domain.usecase.schedule.GetDetailScheduleUseCase
import com.whereareyou.domain.usecase.location.GetLocationAddressUseCase
import com.whereareyou.domain.usecase.schedule.GetMonthlyScheduleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ScheduleUseCaseModule {

    @Provides
    fun provideGetMonthlyScheduleUseCase(
        repository: ScheduleRepository
    ): GetMonthlyScheduleUseCase {
        return GetMonthlyScheduleUseCase(repository)
    }

    @Provides
    fun provideGetDailyBriefScheduleUseCase(
        repository: ScheduleRepository
    ): GetDailyBriefScheduleUseCase {
        return GetDailyBriefScheduleUseCase(repository)
    }

    @Provides
    fun provideDetailScheduleUseCase(
        repository: ScheduleRepository
    ): GetDetailScheduleUseCase {
        return GetDetailScheduleUseCase(repository)
    }

    @Provides
    fun getLocationAddressUseCase(
        repository: SearchLocationRepository
    ): GetLocationAddressUseCase {
        return GetLocationAddressUseCase(repository)
    }
}