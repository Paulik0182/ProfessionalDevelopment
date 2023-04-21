package com.paulik.professionaldevelopment.di.modules

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
ViewModelKeys помогает сопоставить классы ViewModel, чтобы ViewModelFactory мог правильно
предоставлять / внедрять их.
KClass и Class - это одно и тоже, KClass - Kotlin синтаксис.
 */

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)