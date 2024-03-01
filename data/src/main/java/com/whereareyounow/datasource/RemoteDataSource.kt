package com.whereareyounow.datasource

import com.whereareyounow.api.FCMApi
import com.whereareyounow.api.FriendApi
import com.whereareyounow.api.LocationApi
import com.whereareyounow.api.ScheduleApi
import com.whereareyounow.api.SignInApi
import com.whereareyounow.api.SignUpApi
import com.whereareyounow.domain.entity.apimessage.fcm.DeleteFCMTokenRequest
import com.whereareyounow.domain.entity.apimessage.fcm.UpdateFCMTokenRequest
import com.whereareyounow.domain.entity.apimessage.friend.AcceptFriendRequestRequest
import com.whereareyounow.domain.entity.apimessage.friend.GetFriendIdsListRequest
import com.whereareyounow.domain.entity.apimessage.friend.GetFriendIdsListResponse
import com.whereareyounow.domain.entity.apimessage.friend.GetFriendListRequest
import com.whereareyounow.domain.entity.apimessage.friend.GetFriendListResponse
import com.whereareyounow.domain.entity.apimessage.friend.GetFriendRequestListResponse
import com.whereareyounow.domain.entity.apimessage.friend.RefuseFriendRequestRequest
import com.whereareyounow.domain.entity.apimessage.friend.SendFriendRequestRequest
import com.whereareyounow.domain.entity.apimessage.location.GetUserLocationRequest
import com.whereareyounow.domain.entity.apimessage.location.SendUserLocationRequest
import com.whereareyounow.domain.entity.apimessage.location.UserLocation
import com.whereareyounow.domain.entity.apimessage.schedule.AcceptScheduleRequest
import com.whereareyounow.domain.entity.apimessage.schedule.AddNewScheduleRequest
import com.whereareyounow.domain.entity.apimessage.schedule.AddNewScheduleResponse
import com.whereareyounow.domain.entity.apimessage.schedule.CheckArrivalRequest
import com.whereareyounow.domain.entity.apimessage.schedule.DeleteScheduleRequest
import com.whereareyounow.domain.entity.apimessage.schedule.GetDailyBriefScheduleResponse
import com.whereareyounow.domain.entity.apimessage.schedule.GetDetailScheduleResponse
import com.whereareyounow.domain.entity.apimessage.schedule.GetMonthlyScheduleResponse
import com.whereareyounow.domain.entity.apimessage.schedule.GetScheduleInvitationResponse
import com.whereareyounow.domain.entity.apimessage.schedule.GetTodayScheduleCountResponse
import com.whereareyounow.domain.entity.apimessage.schedule.ModifyScheduleDetailsRequest
import com.whereareyounow.domain.entity.apimessage.schedule.ModifyScheduleMemberRequest
import com.whereareyounow.domain.entity.apimessage.schedule.RefuseOrQuitScheduleRequest
import com.whereareyounow.domain.entity.apimessage.schedule.ResetCalendarRequest
import com.whereareyounow.domain.entity.apimessage.signin.DeleteMemberRequest
import com.whereareyounow.domain.entity.apimessage.signin.FindIdRequest
import com.whereareyounow.domain.entity.apimessage.signin.FindIdResponse
import com.whereareyounow.domain.entity.apimessage.signin.GetMemberDetailsResponse
import com.whereareyounow.domain.entity.apimessage.signin.GetMemberIdByUserIdResponse
import com.whereareyounow.domain.entity.apimessage.signin.ReissueTokenRequest
import com.whereareyounow.domain.entity.apimessage.signin.ReissueTokenResponse
import com.whereareyounow.domain.entity.apimessage.signin.ResetPasswordRequest
import com.whereareyounow.domain.entity.apimessage.signin.SignInRequest
import com.whereareyounow.domain.entity.apimessage.signin.SignInResponse
import com.whereareyounow.domain.entity.apimessage.signin.VerifyPasswordResetCodeRequest
import com.whereareyounow.domain.entity.apimessage.signin.VerifyPasswordResetCodeResponse
import com.whereareyounow.domain.entity.apimessage.signup.AuthenticateEmailCodeRequest
import com.whereareyounow.domain.entity.apimessage.signup.AuthenticateEmailRequest
import com.whereareyounow.domain.entity.apimessage.signup.CheckEmailDuplicateResponse
import com.whereareyounow.domain.entity.apimessage.signup.CheckIdDuplicateResponse
import com.whereareyounow.domain.entity.apimessage.signup.SignUpRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class RemoteDataSource(
    private val scheduleApi: ScheduleApi,
    private val signUpApi: SignUpApi,
    private val signInApi: SignInApi,
    private val friendApi: FriendApi,
//    private val groupApi: GroupApi,
    private val locationApi: LocationApi,
    private val fcmApi: FCMApi,
) {
    // 일정 관련
    // 월별 일정 정보
    suspend fun getMonthlySchedule(
        token: String,
        memberId: String,
        year: Int,
        month: Int
    ): Response<GetMonthlyScheduleResponse> {
        return scheduleApi.getMonthlySchedule(token, memberId, year, month)
    }

    // 일별 간략 정보
    suspend fun getDailyBriefSchedule(
        token: String,
        memberId: String,
        year: Int,
        month: Int,
        date: Int
    ): Response<GetDailyBriefScheduleResponse> {
        return scheduleApi.getDailyBriefSchedule(token, memberId, year, month, date)
    }

    // 일별 일정 상세 정보
    suspend fun getDetailSchedule(
        token: String,
        memberId: String,
        scheduleId: String
    ): Response<GetDetailScheduleResponse> {
        return scheduleApi.getDetailSchedule(token, memberId, scheduleId)
    }

    // 일정 추가
    suspend fun addNewSchedule(
        token: String,
        body: AddNewScheduleRequest
    ): Response<AddNewScheduleResponse> {
        return scheduleApi.addNewSchedule(token, body)
    }

    // 일정 내용 수정
    suspend fun modifyScheduleDetails(
        token: String,
        body: ModifyScheduleDetailsRequest
    ): Response<Unit> {
        return scheduleApi.modifyScheduleDetails(token, body)
    }

    // 일정 멤버 수정
    suspend fun modifyScheduleMember(
        token: String,
        body: ModifyScheduleMemberRequest
    ): Response<Unit> {
        return scheduleApi.modifyScheduleMember(token, body)
    }

    // 일정 삭제
    suspend fun deleteSchedule(
        token: String,
        body: DeleteScheduleRequest
    ): Response<Unit> {
        return scheduleApi.deleteSchedule(token, body)
    }

    // 일정 수락
    suspend fun acceptSchedule(
        token: String,
        body: AcceptScheduleRequest
    ): Response<Boolean> {
        return scheduleApi.acceptSchedule(token, body)
    }

    // 도착 여부
    suspend fun checkArrival(
        token: String,
        body: CheckArrivalRequest
    ): Response<Boolean> {
        return scheduleApi.checkArrival(token, body)
    }

    // 일정 거절 또는 나가기
    suspend fun refuseOrQuitSchedule(
        token: String,
        body: RefuseOrQuitScheduleRequest
    ): Response<Unit> {
        return scheduleApi.refuseOrQuitSchedule(token, body)
    }

    // 오늘 일정 개수
    suspend fun getTodayScheduleCount(
        token: String,
        memberId: String
    ): Response<GetTodayScheduleCountResponse> {
        return scheduleApi.getTodayScheduleCount(token, memberId)
    }

    // 일정 초대 목록
    suspend fun getScheduleInvitation(
        token: String,
        memberId: String
    ): Response<GetScheduleInvitationResponse> {
        return scheduleApi.getScheduleInvitation(token, memberId)
    }

    // 캘린더 삭제
    suspend fun resetCalendar(
        token: String,
        body: ResetCalendarRequest
    ): Response<Boolean> {
        return scheduleApi.resetCalendar(token, body)
    }


    // 회원가입 관련
    // 아이디 중복 검사
    suspend fun checkIdDuplicate(
        id: String
    ): Response<CheckIdDuplicateResponse> {
        return signUpApi.checkIdDuplicate(id)
    }

    // 이메일 중복 검사
    suspend fun checkEmailDuplicate(
        email: String
    ): Response<CheckEmailDuplicateResponse> {
        return signUpApi.checkEmailDuplicate(email)
    }

    // 이메일 인증
    suspend fun authenticateEmail(
        body: AuthenticateEmailRequest
    ): Response<Unit> {
        return signUpApi.authenticateEmail(body)
    }

    // 이메일 인증 코드 입력
    suspend fun authenticateEmailCode(
        body: AuthenticateEmailCodeRequest
    ): Response<Unit> {
        return signUpApi.authenticateEmailCode(body)
    }

    // 회원가입 성공
    suspend fun signUp(
        body: SignUpRequest
    ): Response<Unit> {
        return signUpApi.signUp(body)
    }

    // 로그인 관련
    // 로그인
    suspend fun signIn(
        body: SignInRequest
    ): Response<SignInResponse> {
        return signInApi.signIn(body)
    }

    // 토큰 재발급
    suspend fun reissueToken(
        body: ReissueTokenRequest
    ): Response<ReissueTokenResponse> {
        return signInApi.reissueToken(body)
    }

    // 아이디 찾기
    suspend fun findId(
        body: FindIdRequest
    ): Response<FindIdResponse> {
        return signInApi.findId(body)
    }

    // 비밀번호 재설정 코드 입력
    suspend fun verifyPasswordResetCode(
        body: VerifyPasswordResetCodeRequest
    ): Response<VerifyPasswordResetCodeResponse> {
        return signInApi.verifyPasswordResetCode(body)
    }

    // 비밀번호 재설정
    suspend fun resetPassword(
        body: ResetPasswordRequest
    ): Response<Unit> {
        return signInApi.resetPassword(body)
    }

    // 회원 상세 정보(memberId)
    suspend fun getMemberDetails(
        token: String,
        memberId: String
    ): Response<GetMemberDetailsResponse> {
        return signInApi.getMemberDetails(token, memberId)
    }

    // 회원 상세 정보(userId)
    suspend fun getMemberIdByUserId(
        token: String,
        userId: String
    ): Response<GetMemberIdByUserIdResponse> {
        return signInApi.getMemberIdByUserId(token, userId)
    }

    // 마이페이지 수정
    suspend fun modifyMyInfo(
        token: String,
        partMap: HashMap<String, RequestBody>,
        photo: MultipartBody.Part?
//        userId: RequestBody
    ): Response<Unit> {
        return signInApi.modifyMyInfo(token, partMap, photo)
    }

    // 회원정보 삭제
    suspend fun deleteMember(
        token: String,
        body: DeleteMemberRequest
    ): Response<Unit> {
        return signInApi.deleteMember(token, body)
    }

    // 친구 관련
    // 친구 MemberId 목록
    suspend fun getFriendIdsList(
        token: String,
        body: GetFriendIdsListRequest
    ): Response<GetFriendIdsListResponse> {
        return friendApi.getFriendIdsList(token, body)
    }

    // 친구 목록
    suspend fun getFriendList(
        token: String,
        body: GetFriendListRequest
    ): Response<GetFriendListResponse> {
        return friendApi.getFriendList(token, body)
    }

    // 친구 요청 조회(사이드바)
    suspend fun getFriendRequestList(
        token: String,
        memberId: String
    ): Response<GetFriendRequestListResponse> {
        return friendApi.getFriendRequestList(token, memberId)
    }

    // 친구 신청
    suspend fun sendFriendRequest(
        token: String,
        body: SendFriendRequestRequest
    ): Response<Unit> {
        return friendApi.sendFriendRequest(token, body)
    }

    // 친구 수락
    suspend fun acceptFriendRequest(
        token: String,
        body: AcceptFriendRequestRequest
    ): Response<Unit> {
        return friendApi.acceptFriendRequest(token, body)
    }

    // 친구 거절
    suspend fun refuseFriendRequest(
        token: String,
        body: RefuseFriendRequestRequest
    ): Response<Unit> {
        return friendApi.refuseFriendRequest(token, body)
    }

    // 친구 삭제


    // 그룹 관련


    // 위치 관련
    // 사용자 실시간 위도 경도
    suspend fun getUserLocation(
        token: String,
        body: GetUserLocationRequest
    ): Response<List<UserLocation>> {
        return locationApi.getUserLocation(token, body)
    }

    // 사용자 위도 경도
    suspend fun sendUserLocation(
        token: String,
        body: SendUserLocationRequest
    ): Response<Boolean> {
        return locationApi.sendUserLocation(token, body)
    }

    // FCM 관련
    // FCM 토큰 저장 및 갱신
    suspend fun updateFCMToken(
        token: String,
        body: UpdateFCMTokenRequest
    ): Response<Unit> {
        return fcmApi.updateFCMToken(token, body)
    }

    // FCM 토큰 삭제(로그아웃)
    suspend fun deleteFCMToken(
        token: String,
        body: DeleteFCMTokenRequest
    ): Response<Unit> {
        return fcmApi.deleteFCMToken(token, body)
    }
}