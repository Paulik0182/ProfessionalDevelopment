package com.paulik.professionaldevelopment.domain.entity

import com.google.gson.annotations.SerializedName

data class MeaningsEntity(

    @field:SerializedName("translation")
    val translation: TranslationEntity?,

    @field:SerializedName("image_url")
    val imageUrl: String?
)