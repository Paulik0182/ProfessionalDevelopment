package com.paulik.professionaldevelopment.domain.entity

import com.google.gson.annotations.SerializedName

data class TranslationEntity(

    @field:SerializedName("text")
    val translation: String?
)