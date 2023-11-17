package com.whereareyou.datasource

import com.whereareyou.api.FriendApi
import com.whereareyou.api.GroupApi
import com.whereareyou.api.LocationApi
import com.whereareyou.api.ScheduleApi
import com.whereareyou.api.SignInApi
import com.whereareyou.api.SignUpApi
import com.whereareyou.domain.entity.apimessage.friend.AcceptFriendRequestRequest
import com.whereareyou.domain.entity.apimessage.friend.GetFriendIdsListRequest
import com.whereareyou.domain.entity.apimessage.friend.GetFriendIdsListResponse
import com.whereareyou.domain.entity.apimessage.friend.GetFriendListRequest
import com.whereareyou.domain.entity.apimessage.friend.GetFriendListResponse
import com.whereareyou.domain.entity.apimessage.friend.GetFriendRequestListResponse
import com.whereareyou.domain.entity.apimessage.friend.RefuseFriendRequestRequest
import com.whereareyou.domain.entity.apimessage.friend.SendFriendRequestRequest
import com.whereareyou.domain.entity.apimessage.location.GetUserLocationResponse
import com.whereareyou.domain.entity.apimessage.location.SendUserLocationRequest
import com.whereareyou.domain.entity.apimessage.schedule.AcceptScheduleRequest
import com.whereareyou.domain.entity.apimessage.schedule.AddNewScheduleRequest
import com.whereareyou.domain.entity.apimessage.schedule.AddNewScheduleResponse
import com.whereareyou.domain.entity.apimessage.schedule.CheckArrivalRequest
import com.whereareyou.domain.entity.apimessage.schedule.DeleteScheduleRequest
import com.whereareyou.domain.entity.apimessage.schedule.EndScheduleRequest
import com.whereareyou.domain.entity.apimessage.schedule.GetDailyBriefScheduleResponse
import com.whereareyou.domain.entity.apimessage.schedule.GetDetailScheduleResponse
import com.whereareyou.domain.entity.apimessage.schedule.GetMonthlyScheduleResponse
import com.whereareyou.domain.entity.apimessage.schedule.ModifyScheduleMemberRequest
import com.whereareyou.domain.entity.apimessage.schedule.ModifyScheduleRequest
import com.whereareyou.domain.entity.apimessage.signin.DeleteMemberRequest
import com.whereareyou.domain.entity.apimessage.signin.FindIdRequest
import com.whereareyou.domain.entity.apimessage.signin.FindIdResponse
import com.whereareyou.domain.entity.apimessage.signin.GetMemberDetailsResponse
import com.whereareyou.domain.entity.apimessage.signin.ModifyMyInfoRequest
import com.whereareyou.domain.entity.apimessage.signin.ReissueTokenRequest
import com.whereareyou.domain.entity.apimessage.signin.ReissueTokenResponse
import com.whereareyou.domain.entity.apimessage.signin.ResetPasswordRequest
import com.whereareyou.domain.entity.apimessage.signin.SignInRequest
import com.whereareyou.domain.entity.apimessage.signin.SignInResponse
import com.whereareyou.domain.entity.apimessage.signin.VerifyPasswordResetCodeRequest
import com.whereareyou.domain.entity.apimessage.signin.VerifyPasswordResetCodeResponse
import com.whereareyou.domain.entity.apimessage.signup.AuthenticateEmailCodeRequest
import com.whereareyou.domain.entity.apimessage.signup.AuthenticateEmailCodeResponse
import com.whereareyou.domain.entity.apimessage.signup.AuthenticateEmailRequest
import com.whereareyou.domain.entity.apimessage.signup.AuthenticateEmailResponse
import com.whereareyou.domain.entity.apimessage.signup.CheckEmailDuplicateResponse
import com.whereareyou.domain.entity.apimessage.signup.CheckIdDuplicateResponse
import com.whereareyou.domain.entity.apimessage.signup.SignUpRequest
import com.whereareyou.domain.entity.apimessage.signup.SignUpResponse
import retrofit2.Response

class RemoteDataSource(
    private val scheduleApi: ScheduleApi,
    private val signUpApi: SignUpApi,
    private val signInApi: SignInApi,
//    private val friendApi: FriendApi,
//    private val groupApi: GroupApi,
    private val locationApi: LocationApi
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
    suspend fun modifySchedule(
        token: String,
        body: ModifyScheduleRequest
    ): Response<Nothing> {
        return scheduleApi.modifySchedule(token, body)
    }

    // 일정 멤버 수정
    suspend fun modifyScheduleMember(
        token: String,
        body: ModifyScheduleMemberRequest
    ): Response<Nothing> {
        return scheduleApi.modifyScheduleMember(token, body)
    }

    // 일정 삭제
    suspend fun deleteSchedule(
        token: String,
        body: DeleteScheduleRequest
    ): Response<Nothing> {
        return scheduleApi.deleteSchedule(token, body)
    }

    // 일정 수락
    suspend fun acceptSchedule(
        token: String,
        body: AcceptScheduleRequest
    ): Response<Boolean> {
        return scheduleApi.acceptSchedule(token, body)
    }

    // 일정 종료
    suspend fun endSchedule(
        token: String,
        body: EndScheduleRequest
    ): Response<Boolean> {
        return scheduleApi.endSchedule(token, body)
    }

    // 도착 여부
    suspend fun checkArrival(
        token: String,
        body: CheckArrivalRequest
    ): Response<Boolean> {
        return scheduleApi.checkArrival(token, body)
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
    ): Response<AuthenticateEmailResponse> {
        return signUpApi.authenticateEmail(body)
    }

    // 이메일 인증 코드 입력
    suspend fun authenticateEmailCode(
        body: AuthenticateEmailCodeRequest
    ): Response<AuthenticateEmailCodeResponse> {
        return signUpApi.authenticateEmailCode(body)
    }

    // 회원가입 성공
    suspend fun signUp(
        body: SignUpRequest
    ): Response<SignUpResponse> {
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
    ): Response<Nothing> {
        return signInApi.resetPassword(body)
    }

    // 회원 상세 정보
    suspend fun getMemberDetails(
        token: String,
        memberId: String
    ): Response<GetMemberDetailsResponse> {
        return signInApi.getMemberDetails(token, memberId)
    }

    // 마이페이지 수정
    suspend fun modifyMyInfo(
        token: String,
        body: ModifyMyInfoRequest
    ): Response<Nothing> {
        return signInApi.modifyMyInfo(token, body)
    }

    // 회원정보 삭제
    suspend fun deleteMember(
        token: String,
        body: DeleteMemberRequest
    ): Response<Nothing> {
        return signInApi.deleteMember(token, body)
    }
/*
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
    ): Response<Nothing> {
        return friendApi.sendFriendRequest(token, body)
    }

    // 친구 수락
    suspend fun acceptFriendRequest(
        token: String,
        body: AcceptFriendRequestRequest
    ): Response<Nothing> {
        return friendApi.acceptFriendRequest(token, body)
    }

    // 친구 거절
    suspend fun refuseFriendRequest(
        token: String,
        body: RefuseFriendRequestRequest
    ): Response<Nothing> {
        return friendApi.refuseFriendRequest(token, body)
    }
*/
    // 친구 삭제


    // 그룹 관련


    // 위치 관련
    // 사용자 실시간 위도 경도
    suspend fun getUserLocation(
        token: String,
        memberId: String
    ): Response<GetUserLocationResponse> {
        return locationApi.getUserLocation(token, memberId)
    }

    // 사용자 위도 경도
    suspend fun sendUserLocation(
        token: String,
        body: SendUserLocationRequest
    ): Response<Boolean> {
        return locationApi.sendUserLocation(token, body)
    }
}