package com.paulik.models.entity

import com.google.gson.annotations.SerializedName

data class MeaningsEntity(

    @field:SerializedName("translation")
    val translation: TranslationEntity?,

    @field:SerializedName("imageUrl")
    val imageUrl: String?
)