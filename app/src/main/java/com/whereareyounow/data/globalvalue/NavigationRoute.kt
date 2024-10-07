package com.whereareyounow.data.globalvalue

import com.whereareyounow.domain.entity.schedule.Friend
import kotlinx.serialization.Serializable

const val ROUTE_MODIFY_INFO = "ROUTE_MODIFY_INFO"
const val ROUTE_SPLASH = "ROUTE_SPLASH"
const val ROUTE_SIGN_IN_METHOD_SELECTION = "ROUTE_SIGN_IN_METHOD_SELECTION"
const val ROUTE_SIGN_IN_WITH_ACCOUNT = "ROUTE_SIGN_IN_WITH_ACCOUNT"
const val ROUTE_POLICY_AGREE = "ROUTE_POLICY_AGREE"
const val ROUTE_TERMS_OF_SERVICE = "ROUTE_TERMS_OF_SERVICE"
const val ROUTE_PRIVACY_POLICY = "ROUTE_PRIVACY_POLICY"
const val ROUTE_SIGN_UP = "ROUTE_SIGN_UP"
const val ROUTE_SIGN_UP_SUCCESS = "ROUTE_SIGN_UP_SUCCESS"
const val ROUTE_MAIN = "ROUTE_MAIN"
const val ROUTE_NEW_SCHEDULE = "ROUTE_NEW_SCHEDULE"
const val ROUTE_DETAIL_SCHEDULE = "ROUTE_DETAIL_SCHEDULE"
const val ROUTE_ADD_FRIEND = "ROUTE_ADD_FRIEND"
const val ROUTE_FIND_ACCOUNT = "ROUTE_FIND_ACCOUNT"
const val ROUTE_FIND_ID = "ROUTE_FIND_ID"
const val ROUTE_FIND_ID_RESULT = "ROUTE_FIND_ID_RESULT"
const val ROUTE_RESET_PASSWORD = "ROUTE_RESET_PASSWORD"
const val ROUTE_FIND_PASSWORD_RESULT = "ROUTE_FIND_PASSWORD_RESULT"
const val ROUTE_FIND_PASSWORD_SUCCESS = "ROUTE_FIND_PASSWORD_SUCCESS"
const val ROUTE_SELECT_FRIENDS = "ROUTE_SELECT_FRIENDS"
const val ROUTE_SEARCH_LOCATION = "ROUTE_SEARCH_LOCATION"
const val ROUTE_SEARCH_LOCATION_MAP = "ROUTE_SEARCH_LOCATION_MAP"
const val ROUTE_MODIFY_SCHEDULE = "ROUTE_MODIFY_SCHEDULE"
const val ROUTE_DETAIL_SCHEDULE_MAP = "ROUTE_DETAIL_SCHEDULE_MAP"
const val ROUTE_DETAIL_PROFILE = "ROUTE_DETAIL_PROFILE"


// 마이페이지
const val ROUTE_MY_INFO = "ROUTE_MY_INFO"

sealed class ROUTE {
    @Serializable
    data object Developer {
        @Serializable
        data object Screen1

        @Serializable
        data object Screen2

        @Serializable
        data object Screen3

        @Serializable
        data object Screen4
    }

    @Serializable
    data object Notification

    @Serializable
    data object Splash

    @Serializable
    data object SignInMethodSelection

    @Serializable
    data object PolicyAgree {
        @Serializable
        data object Main

        @Serializable
        data object TermsOfService

        @Serializable
        data object Privacy

        @Serializable
        data object Location
    }

    @Serializable
    data object SignUp

    @Serializable
    data class KakaoSignUp(
        val name: String,
        val email: String,
        val userId: String
    )

    @Serializable
    data class AccountDuplicate(
        val accountType: String,
        val email: String,
        val type: List<String>,
        val userName: String,
        val password: String
    )

    @Serializable
    data object SignUpSuccess

    @Serializable
    data object SignInWithAccount

    @Serializable
    data object FindAccountEmailVerification

    @Serializable
    data object Main {
        @Serializable
        data object Home

        @Serializable
        data object Schedule

        @Serializable
        data object Social

        @Serializable
        data object MyPage
    }

    @Serializable
    data class AddSchedule(
        val year: Int,
        val month: Int,
        val date: Int
    )

    @Serializable
    data class DetailSchedule(
        val scheduleSeq: Int
    )

    @Serializable
    data class ScheduleModification(
        val scheduleSeq: Int
    )

    @Serializable
    data class SearchLocation(
        val location: String
    )

    @Serializable
    data class LocationMap(
        val name: String,
        val address: String,
        val lat: String,
        val lng: String
    )

    @Serializable
    data class AddFriend(
        val friendList: List<Int>
    )

    @Serializable
    data object AddFeed

    @Serializable
    data object Announcement
    @Serializable
    data object Ask

    // 회원 탈퇴
    @Serializable
    data object Bye1
    @Serializable
    data object Bye2
    @Serializable
    data object Bye3
    @Serializable
    data object Bye4
    @Serializable
    data object Bye5

    // 내 정보 수정
    @Serializable
    data object EditMyInfo

    // 어드민 페이지
    @Serializable
    data object AdminImageScreen

    // 위치 즐겨찾기
    @Serializable
    data object LocationFaborite

    // 위치 즐겨찾기 편집화면
    @Serializable
    data object EditLocationFaborite

    // 피드 책갈피
    @Serializable
    data object FeedBookMark

    // 피드 보관함
    @Serializable
    data object FeedStore

}
