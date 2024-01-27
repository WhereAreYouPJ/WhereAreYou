package com.whereareyounow.di.usecase

import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.usecase.schedule.AcceptScheduleUseCase
import com.whereareyounow.domain.usecase.schedule.AddNewScheduleUseCase
import com.whereareyounow.domain.usecase.schedule.CheckArrivalUseCase
import com.whereareyounow.domain.usecase.schedule.DeleteScheduleUseCase
import com.whereareyounow.domain.usecase.schedule.GetDailyBriefScheduleUseCase
import com.whereareyounow.domain.usecase.schedule.GetDetailScheduleUseCase
import com.whereareyounow.domain.usecase.schedule.GetMonthlyScheduleUseCase
import com.whereareyounow.domain.usecase.schedule.GetScheduleInvitationUseCase
import com.whereareyounow.domain.usecase.schedule.ModifyScheduleDetailsUseCase
import com.whereareyounow.domain.usecase.schedule.ModifyScheduleMemberUseCase
import com.whereareyounow.domain.usecase.schedule.RefuseOrQuitScheduleUseCase
import com.whereareyounow.domain.usecase.schedule.ResetCalendarUseCase
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
    fun provideRefuseOrQuitScheduleUseCase(
        repository: ScheduleRepository
    ): RefuseOrQuitScheduleUseCase {
        return RefuseOrQuitScheduleUseCase(repository)
    }

    @Provides
    fun provideModifyScheduleUseCase(
        repository: ScheduleRepository
    ): ModifyScheduleDetailsUseCase {
        return ModifyScheduleDetailsUseCase(repository)
    }

    @Provides
    fun provideModifyScheduleMemberUseCase(
        repository: ScheduleRepository
    ): ModifyScheduleMemberUseCase {
        return ModifyScheduleMemberUseCase(repository)
    }

    @Provides
    fun provideGetScheduleInvitationUseCase(
        repository: ScheduleRepository
    ): GetScheduleInvitationUseCase {
        return GetScheduleInvitationUseCase(repository)
    }

    @Provides
    fun resetCalendarUseCase(
        repository: ScheduleRepository
    ): ResetCalendarUseCase {
        return ResetCalendarUseCase(repository)
    }
}