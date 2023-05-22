package com.paulik.models.entity

import com.google.gson.annotations.SerializedName

data class TranslationEntity(

    @field:SerializedName("text")
    val translation: String?
)