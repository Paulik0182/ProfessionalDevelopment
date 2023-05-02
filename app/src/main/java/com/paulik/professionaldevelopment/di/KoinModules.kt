package com.paulik.professionaldevelopment.di

import androidx.room.Room
import com.paulik.professionaldevelopment.data.HistoryWordTranslationInteractorImpl
import com.paulik.professionaldevelopment.data.RepositoryImpl
import com.paulik.professionaldevelopment.data.WordTranslationInteractorImpl
import com.paulik.professionaldevelopment.data.retrofit.RetrofitImpl
import com.paulik.professionaldevelopment.data.room.HistoryDataBase
import com.paulik.professionaldevelopment.data.room.RepositoryLocalImpl
import com.paulik.professionaldevelopment.data.room.RoomDataBaseImpl
import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.repo.Repository
import com.paulik.professionaldevelopment.domain.repo.RepositoryLocal
import com.paulik.professionaldevelopment.ui.history.HistoryWordTranslationViewModel
import com.paulik.professionaldevelopment.ui.translation.WordTranslationViewModel
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
    single {
        Room.databaseBuilder(
            get(),
            HistoryDataBase::class.java, "HistoryDB"
        )
            .build()
    }

    single {
        get<HistoryDataBase>()
            .historyDao()
    }

    single<Repository<List<DataEntity>>> {
        RepositoryImpl(RetrofitImpl())
    }

    single<RepositoryLocal<List<DataEntity>>> {
        RepositoryLocalImpl(
            RoomDataBaseImpl(get())
        )
    }
}

val mainScreen = module {
    factory {
        WordTranslationInteractorImpl(
            get(),
            get()
        )
    }
    factory {
        WordTranslationViewModel(get())
    }
}

val historyScreen = module {
    factory {
        HistoryWordTranslationViewModel(get())
    }
    factory {
        HistoryWordTranslationInteractorImpl(
            get(), get()
        )
    }
}
