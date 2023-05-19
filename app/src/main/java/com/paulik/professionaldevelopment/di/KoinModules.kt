package com.paulik.professionaldevelopment.di

import androidx.room.Room
import com.paulik.professionaldevelopment.data.HistoryWordTranslationInteractorImpl
import com.paulik.professionaldevelopment.data.RepositoryImpl
import com.paulik.professionaldevelopment.data.WordTranslationInteractorImpl
import com.paulik.professionaldevelopment.data.retrofit.RetrofitImpl
import com.paulik.professionaldevelopment.data.room.WordDataBase
import com.paulik.professionaldevelopment.data.room.favorite.FavoriteDataBaseImpl
import com.paulik.professionaldevelopment.data.room.history.HistoryDataBaseImpl
import com.paulik.professionaldevelopment.data.room.history.HistoryLocalRepoImpl
import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.repo.HistoryRepo
import com.paulik.professionaldevelopment.domain.repo.Repository
import com.paulik.professionaldevelopment.ui.favorite.FavoriteWordViewModel
import com.paulik.professionaldevelopment.ui.history.HistoryWordTranslationViewModel
import com.paulik.professionaldevelopment.ui.translation.WordTranslationViewModel
import com.paulik.professionaldevelopment.ui.translation.descriptios.DescriptionWordTranslationViewModel
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
            WordDataBase::class.java, "wordDB"
        )
            .build()
    }

    single {
        get<WordDataBase>()
            .historyDao()
    }

    single {
        get<WordDataBase>()
            .favoriteDao()
    }

    single<Repository<List<DataEntity>>> {
        RepositoryImpl(RetrofitImpl())
    }

    single<HistoryRepo<List<DataEntity>>> {
        HistoryLocalRepoImpl(
            HistoryDataBaseImpl(get())
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
        FavoriteDataBaseImpl(
            get()
        )
    }

    factory {
        WordTranslationViewModel(
            get<WordTranslationInteractorImpl>(),
            get<FavoriteDataBaseImpl>()
        )
    }
}

val historyScreen = module {
    factory {
        HistoryWordTranslationInteractorImpl(
            get()
        )
    }

    factory {
        HistoryLocalRepoImpl(
            HistoryDataBaseImpl(get())
        )
    }

    factory {
        HistoryWordTranslationViewModel(
            get<HistoryWordTranslationInteractorImpl>(),
            get<HistoryLocalRepoImpl>()
        )
    }
}

val wordDetailsScreen = module {
    factory {
        DescriptionWordTranslationViewModel(
            get()
        )
    }
}

val favoriteScreen = module {
    factory {
        FavoriteWordViewModel(
            get<FavoriteDataBaseImpl>()
        )
    }
}
