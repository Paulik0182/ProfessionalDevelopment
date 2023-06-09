package com.paulik.models.entity

import com.google.gson.annotations.SerializedName

data class DataEntity(

    @field:SerializedName("text")
    val text: String?,

    @field:SerializedName("meanings")
    val meanings: List<MeaningsEntity>?
)