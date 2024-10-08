package com.whereareyounow.di.usecase

import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.usecase.schedule.AcceptScheduleInvitationUseCase
import com.whereareyounow.domain.usecase.schedule.CreateNewScheduleUseCase
import com.whereareyounow.domain.usecase.schedule.DeleteScheduleByCreatorUseCase
import com.whereareyounow.domain.usecase.schedule.DeleteScheduleByInvitorUseCase
import com.whereareyounow.domain.usecase.schedule.GetDailyScheduleUseCase
import com.whereareyounow.domain.usecase.schedule.GetDetailScheduleUseCase
import com.whereareyounow.domain.usecase.schedule.GetInvitedScheduleUseCase
import com.whereareyounow.domain.usecase.schedule.GetMonthlyScheduleUseCase
import com.whereareyounow.domain.usecase.schedule.GetScheduleDDayUseCase
import com.whereareyounow.domain.usecase.schedule.GetScheduleListUseCase
import com.whereareyounow.domain.usecase.schedule.ModifyScheduleInfoUseCase
import com.whereareyounow.domain.usecase.schedule.RefuseScheduleInvitationUseCase
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
    ): AcceptScheduleInvitationUseCase {
        return AcceptScheduleInvitationUseCase(repository)
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
    fun provideGetInvitedScheduleUseCase(
        repository: ScheduleRepository
    ) = GetInvitedScheduleUseCase(repository)

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
    ): RefuseScheduleInvitationUseCase {
        return RefuseScheduleInvitationUseCase(repository)
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