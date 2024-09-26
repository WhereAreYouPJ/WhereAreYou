package com.onmyway.domain.request.member

import com.google.gson.annotations.SerializedName

data class GetUserInfoByMemberCodeRequest(
    @SerializedName("memberCode")
    val memberCode: String
)
