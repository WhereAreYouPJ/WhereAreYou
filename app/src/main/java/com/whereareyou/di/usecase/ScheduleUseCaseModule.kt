package com.whereareyou.di.usecase

import com.whereareyou.domain.repository.ScheduleRepository
import com.whereareyou.domain.repository.SearchLocationRepository
import com.whereareyou.domain.usecase.location.GetLocationAddressUseCase
import com.whereareyou.domain.usecase.schedule.AcceptScheduleUseCase
import com.whereareyou.domain.usecase.schedule.AddNewScheduleUseCase
import com.whereareyou.domain.usecase.schedule.CheckArrivalUseCase
import com.whereareyou.domain.usecase.schedule.DeleteScheduleUseCase
import com.whereareyou.domain.usecase.schedule.EndScheduleUseCase
import com.whereareyou.domain.usecase.schedule.GetDailyBriefScheduleUseCase
import com.whereareyou.domain.usecase.schedule.GetDetailScheduleUseCase
import com.whereareyou.domain.usecase.schedule.GetMonthlyScheduleUseCase
import com.whereareyou.domain.usecase.schedule.ModifyScheduleMemberUseCase
import com.whereareyou.domain.usecase.schedule.ModifyScheduleUseCase
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
    fun provideAddNewScheduleUseCase(
        repository: ScheduleRepository
    ): AddNewScheduleUseCase {
        return AddNewScheduleUseCase(repository)
    }

    @Provides
    fun provideAcceptScheduleUseCase(
        repository: ScheduleRepository
    ): AcceptScheduleUseCase {
        return AcceptScheduleUseCase(repository)
    }

    @Provides
    fun provideCheckArrivalUseCase(
        repository: ScheduleRepository
    ): CheckArrivalUseCase {
        return CheckArrivalUseCase(repository)
    }

    @Provides
    fun provideDeleteScheduleUseCase(
        repository: ScheduleRepository
    ): DeleteScheduleUseCase {
        return DeleteScheduleUseCase(repository)
    }

    @Provides
    fun provideEndScheduleUseCase(
        repository: ScheduleRepository
    ): EndScheduleUseCase {
        return EndScheduleUseCase(repository)
    }

    @Provides
    fun provideModifyScheduleUseCase(
        repository: ScheduleRepository
    ): ModifyScheduleUseCase {
        return ModifyScheduleUseCase(repository)
    }

    @Provides
    fun provideModifyScheduleMemberUseCase(
        repository: ScheduleRepository
    ): ModifyScheduleMemberUseCase {
        return ModifyScheduleMemberUseCase(repository)
    }
}