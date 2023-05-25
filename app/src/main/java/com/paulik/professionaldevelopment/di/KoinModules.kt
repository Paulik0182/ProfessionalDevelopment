package com.paulik.professionaldevelopment.di

import androidx.room.Room
import com.paulik.models.entity.DataEntity
import com.paulik.professionaldevelopment.ui.favorite.FavoriteWordViewModel
import com.paulik.professionaldevelopment.ui.translation.WordTranslationViewModel
import com.paulik.professionaldevelopment.ui.translation.descriptios.DescriptionWordTranslationViewModel
import com.paulik.repository.data.HistoryWordTranslationInteractorImpl
import com.paulik.repository.data.RepositoryImpl
import com.paulik.repository.data.WordTranslationInteractorImpl
import com.paulik.repository.data.retrofit.RetrofitImpl
import com.paulik.repository.data.room.WordDataBase
import com.paulik.repository.domain.repo.HistoryRepo
import com.paulik.repository.domain.repo.Repository
import com.paulik.repository.room.favorite.FavoriteDataBaseImpl
import com.paulik.repository.room.history.HistoryDataBaseImpl
import com.paulik.repository.room.history.HistoryLocalRepoImpl
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
