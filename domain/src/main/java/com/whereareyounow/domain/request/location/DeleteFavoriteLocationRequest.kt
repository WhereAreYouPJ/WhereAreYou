package com.whereareyounow.domain.request.location

import com.google.gson.annotations.SerializedName

data class DeleteFavoriteLocationRequest (
    @SerializedName("memberSeq")
    val memberSeq : Int,
    @SerializedName("locationSeqs")
    val locationSeqs : List<Int>,

)