package com.whereareyounow.data.notification

data class ScheduleInvitationInfo(
    val scheduleId: String,
    val title: String,
    val inviterUserName: String,
    val year: String,
    val month: String,
    val date: String,
    val hour: String,
    val minute: String,
    val invitedTime: String
)