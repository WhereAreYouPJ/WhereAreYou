package com.whereareyounow.di.usecase

import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.usecase.schedule.AcceptScheduleRequestUseCase
import com.whereareyounow.domain.usecase.schedule.CreateNewScheduleUseCase
import com.whereareyounow.domain.usecase.schedule.DeleteScheduleByCreatorUseCase
import com.whereareyounow.domain.usecase.schedule.DeleteScheduleByInvitorUseCase
import com.whereareyounow.domain.usecase.schedule.GetDailyScheduleUseCase
import com.whereareyounow.domain.usecase.schedule.GetDetailScheduleUseCase
import com.whereareyounow.domain.usecase.schedule.GetMonthlyScheduleUseCase
import com.whereareyounow.domain.usecase.schedule.GetScheduleDDayUseCase
import com.whereareyounow.domain.usecase.schedule.GetScheduleListUseCase
import com.whereareyounow.domain.usecase.schedule.ModifyScheduleInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ScheduleUseCaseModule {

    @Provides
    fun provideGetDetailScheduleUseCase(
        repository: ScheduleRepository
    ): GetDetailScheduleUseCase {
        return GetDetailScheduleUseCase(repository)
    }

    @Provides
    fun provideModifyScheduleInfoUseCase(
        repository: ScheduleRepository
    ): ModifyScheduleInfoUseCase {
        return ModifyScheduleInfoUseCase(repository)
    }

    @Provides
    fun provideCreateNewScheduleUseCase(
        repository: ScheduleRepository
    ): CreateNewScheduleUseCase {
        return CreateNewScheduleUseCase(repository)
    }

    @Provides
    fun provideAcceptScheduleRequest(
        repository: ScheduleRepository
    ): AcceptScheduleRequestUseCase {
        return AcceptScheduleRequestUseCase(repository)
    }

    @Provides
    fun provideGetMonthlyScheduleUseCase(
        repository: ScheduleRepository
    ): GetMonthlyScheduleUseCase {
        return GetMonthlyScheduleUseCase(repository)
    }

    @Provides
    fun provideGetScheduleListUseCase(
        repository: ScheduleRepository
    ): GetScheduleListUseCase {
        return GetScheduleListUseCase(repository)
    }

    @Provides
    fun provideGetScheduleDDay(
        repository: ScheduleRepository
    ): GetScheduleDDayUseCase {
        return GetScheduleDDayUseCase(repository)
    }

    @Provides
    fun provideGetDailyScheduleUseCase(
        repository: ScheduleRepository
    ): GetDailyScheduleUseCase {
        return GetDailyScheduleUseCase(repository)
    }

    @Provides
    fun provideDeleteScheduleByInvitor(
        repository: ScheduleRepository
    ): DeleteScheduleByInvitorUseCase {
        return DeleteScheduleByInvitorUseCase(repository)
    }

    @Provides
    fun provideDeleteScheduleByCreator(
        repository: ScheduleRepository
    ): DeleteScheduleByCreatorUseCase {
        return DeleteScheduleByCreatorUseCase(repository)
    }
}