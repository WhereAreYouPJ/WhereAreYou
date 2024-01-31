package com.whereareyounow.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.whereareyounow.datasource.RemoteDataSource
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
import com.whereareyounow.domain.repository.SignInRepository
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.util.NetworkResultHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class SignInRepositoryImpl(
    private val dataSource: RemoteDataSource,
    private val preferencesDataStore: DataStore<Preferences>
) : SignInRepository, NetworkResultHandler {

    /**
     * 로그인
     * implements [SignInRepository.signIn]
     */
    override suspend fun signIn(
        body: SignInRequest
    ): NetworkResult<SignInResponse> {
        return handleResult { dataSource.signIn(body) }
    }

    /**
     * 액세스 토큰 저장
     * implements [SignInRepository.saveAccessToken]
     */
    override suspend fun saveAccessToken(accessToken: String) {
        val key = stringPreferencesKey("accessToken")
        preferencesDataStore.edit {
            it[key] = accessToken
        }
    }

    /**
     * 액세스 토큰 가져오기
     * implements [SignInRepository.getAccessToken]
     */
    override suspend fun getAccessToken(): Flow<String> {
        val key = stringPreferencesKey("accessToken")
        return preferencesDataStore.data.map {
            it[key] ?: ""
        }
    }

    /**
     * memberId 저장
     * implements [SignInRepository.saveMemberId]
     */
    override suspend fun saveMemberId(id: String) {
        val key = stringPreferencesKey("memberId")
        preferencesDataStore.edit {
            it[key] = id
        }
    }

    /**
     * memberId 가져오기
     * implements [SignInRepository.getMemberId]
     */
    override suspend fun getMemberId(): Flow<String> {
        val key = stringPreferencesKey("memberId")
        return preferencesDataStore.data.map {
            it[key] ?: ""
        }
    }


    /**
     * 리프레시 토큰 저장
     * implements [SignInRepository.saveRefreshToken]
     */
    override suspend fun saveRefreshToken(refreshToken: String) {
        val key = stringPreferencesKey("refreshToken")
        preferencesDataStore.edit {
            it[key] = refreshToken
        }
    }

    /**
     * 리프레시 토큰 가져오기
     * implements [SignInRepository.getRefreshToken]
     */
    override suspend fun getRefreshToken(): Flow<String> {
        val key = stringPreferencesKey("refreshToken")
        return preferencesDataStore.data.map {
            it[key] ?: ""
        }
    }

    /**
     * 토큰 재발급
     * implements [SignInRepository.reissueToken]
     */
    override suspend fun reissueToken(
        body: ReissueTokenRequest
    ): NetworkResult<ReissueTokenResponse> {
        return handleResult { dataSource.reissueToken(body) }
    }

    /**
     * 아이디 찾기
     * implements [SignInRepository.findId]
     */
    override suspend fun findId(
        body: FindIdRequest
    ): NetworkResult<FindIdResponse> {
        return handleResult { dataSource.findId(body) }
    }

    /**
     * 비밀번호 재설정 코드 입력
     * implements [SignInRepository.verifyPasswordResetCode]
     */
    override suspend fun verifyPasswordResetCode(
        body: VerifyPasswordResetCodeRequest
    ): NetworkResult<VerifyPasswordResetCodeResponse> {
        return handleResult { dataSource.verifyPasswordResetCode(body) }
    }

    /**
     * 비밀번호 재설정
     * implements [SignInRepository.resetPassword]
     */
    override suspend fun resetPassword(
        body: ResetPasswordRequest
    ): NetworkResult<Unit> {
        return handleResult { dataSource.resetPassword(body) }
    }


    /**
     * 회원 상세 정보
     * implements [SignInRepository.getMemberDetails]
     */
    override suspend fun getMemberDetails(
        token: String,
        memberId: String
    ): NetworkResult<GetMemberDetailsResponse> {
        return handleResult { dataSource.getMemberDetails(token, memberId) }
    }

    /**
     * 회원 상세 정보
     * implements [SignInRepository.getMemberIdByUserId]
     */
    override suspend fun getMemberIdByUserId(
        token: String,
        userId: String
    ): NetworkResult<GetMemberIdByUserIdResponse> {
        return handleResult { dataSource.getMemberIdByUserId(token, userId) }
    }

    /**
     * 마이페이지 수정
     * implements [SignInRepository.modifyMyInfo]
     */
    override suspend fun modifyMyInfo(
        token: String,
        memberId: String,
        image: File?,
        userId: String
    ): NetworkResult<Unit> {
        val map = hashMapOf<String, RequestBody>()
        val memberIdBody = RequestBody.create(MediaType.parse("text/plain"), memberId)
//        val multiPartMemberId = MultipartBody.Part.createFormData("memberId", memberIdBody.toString(), memberIdBody)
        map["memberId"] = memberIdBody

        var multipartImage: MultipartBody.Part? = null
        image?.let {
            val imageBody = RequestBody.create(MediaType.parse("image/*"), image)
            multipartImage = MultipartBody.Part.createFormData("images", imageBody.toString(), imageBody)
        }

        if (userId != "") {
            val userIdBody = RequestBody.create(MediaType.parse("text/plain"), userId)
//            val multiPartUserId = MultipartBody.Part.createFormData("newId", userIdBody.toString(), userIdBody)
            map["newId"] = userIdBody
        }
//        val newIdBody = RequestBody.create(MediaType.parse("text/plain"), userId)
        return handleResult { dataSource.modifyMyInfo(token, map, multipartImage) }
    }

    /**
     * 회원정보 삭제
     * implements [SignInRepository.deleteMember]
     * */
    override suspend fun deleteMember(
        token: String,
        body: DeleteMemberRequest
    ): NetworkResult<Unit> {
        return handleResult { dataSource.deleteMember(token, body) }
    }
}