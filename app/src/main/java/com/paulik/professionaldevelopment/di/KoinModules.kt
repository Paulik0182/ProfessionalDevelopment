package com.paulik.professionaldevelopment.di

import com.paulik.professionaldevelopment.data.RepositoryImpl
import com.paulik.professionaldevelopment.data.WordTranslationInteractorImpl
import com.paulik.professionaldevelopment.data.retrofit.RetrofitImpl
import com.paulik.professionaldevelopment.data.room.RoomDataBaseImpl
import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.repo.Repository
import com.paulik.professionaldevelopment.ui.translation.WordTranslationViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/** single - обозначает что это единственный экземпляр на всё приложение. Используется для ленивой
 * инициализации объекта при первом запросе и дальнейшей доступности его в любой части приложения
 * в течение всего времени жизни приложения.
 *
 *  factory - для создания нескольких экземпляров объекта с одинаковыми параметрами в разное время,
 *  или если объект не может быть переиспользован в других частях приложения.
 *  factory обеспечивает независимость исходного объекта от любой кешированной информации,
 *  что позволяет гибко управлять его жизненным циклом.
 *
 * get() - указываются зависимости.
 *  get(named(NAME_REMOTE)) - это именнованные зависимости
 * */

val application = module {
    single<Repository<List<DataEntity>>>(named(NAME_REMOTE)) {
        RepositoryImpl(RetrofitImpl())
    }
    single<Repository<List<DataEntity>>>(named(NAME_LOCAL)) {
        RepositoryImpl(RoomDataBaseImpl())
    }
}

val mainScreen = module {
    factory {
        WordTranslationInteractorImpl(
            get(named(NAME_REMOTE)),
            get(named(NAME_LOCAL))
        )
    }
    factory {
        WordTranslationViewModel(get())
    }
}
