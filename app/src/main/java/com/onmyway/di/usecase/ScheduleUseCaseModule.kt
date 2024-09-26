package com.onmyway.di.usecase

import com.onmyway.domain.repository.ScheduleRepository
import com.onmyway.domain.usecase.schedule.AcceptScheduleRequestUseCase
import com.onmyway.domain.usecase.schedule.CreateNewScheduleUseCase
import com.onmyway.domain.usecase.schedule.DeleteScheduleByCreatorUseCase
import com.onmyway.domain.usecase.schedule.DeleteScheduleByInvitorUseCase
import com.onmyway.domain.usecase.schedule.GetDailyScheduleUseCase
import com.onmyway.domain.usecase.schedule.GetDetailScheduleUseCase
import com.onmyway.domain.usecase.schedule.GetMonthlyScheduleUseCase
import com.onmyway.domain.usecase.schedule.GetScheduleDDayUseCase
import com.onmyway.domain.usecase.schedule.GetScheduleListUseCase
import com.onmyway.domain.usecase.schedule.ModifyScheduleInfoUseCase
import com.onmyway.domain.usecase.schedule.RefuseScheduleInvitation
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
    fun provideRefuseScheduleInvitation(
        repository: ScheduleRepository
    ): RefuseScheduleInvitation {
        return RefuseScheduleInvitation(repository)
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