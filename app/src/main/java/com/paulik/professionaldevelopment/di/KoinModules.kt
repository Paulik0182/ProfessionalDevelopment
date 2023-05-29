package com.paulik.professionaldevelopment.di

import androidx.room.Room
import com.paulik.historyscreen.HistoryWordTranslationFragment
import com.paulik.models.entity.DataEntity
import com.paulik.professionaldevelopment.ui.favorite.FAVOR_SCOPE_NAME
import com.paulik.professionaldevelopment.ui.favorite.FavoriteWordViewModel
import com.paulik.professionaldevelopment.ui.translation.WordTranslationFragment
import com.paulik.professionaldevelopment.ui.translation.WordTranslationViewModel
import com.paulik.professionaldevelopment.ui.translation.descriptios.DescriptionWordTranslationViewModel
import com.paulik.repository.data.RepositoryImpl
import com.paulik.repository.data.WordTranslationInteractorImpl
import com.paulik.repository.data.retrofit.RetrofitImpl
import com.paulik.repository.data.room.WordDataBase
import com.paulik.repository.data.room.favorite.FavoriteDataBaseImpl
import com.paulik.repository.data.room.history.HistoryDataBaseImpl
import com.paulik.repository.data.room.history.HistoryLocalRepoImpl
import com.paulik.repository.domain.repo.HistoryRepo
import com.paulik.repository.domain.repo.Repository
import org.koin.androidx.viewmodel.dsl.viewModel
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
    /** применяем scoped вместо single, factory тем самым привязываес к жизненому цыклу scope
     * Если указать factory - объект не будет закэширован, если несколько раз создать объект с помощью
     * factory то этот объект будет в нескольких экземплярах (физически эти объеты
     * будут занимать разные ячейки в памяти.*/

    scope(named<WordTranslationFragment>()) {
        scoped {
            WordTranslationInteractorImpl(
                get(),
                get()
            )
        }
        scoped {
            FavoriteDataBaseImpl(
                get()
            )
        }

        viewModel {
            WordTranslationViewModel(
                get<WordTranslationInteractorImpl>(),
                get<FavoriteDataBaseImpl>()
            )
        }
    }
}

val historyScreen = module {
    scope(named<HistoryWordTranslationFragment>()) {
        scoped {
            HistoryLocalRepoImpl(
                HistoryDataBaseImpl(get())
            )
        }
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
    /** При данном определении scopa мы сами определяем когда уничтожаем scope */
    scope(named(FAVOR_SCOPE_NAME)) {
        scoped {
            FavoriteDataBaseImpl(
                get()
            )
        }
        viewModel {
            FavoriteWordViewModel(
                get<FavoriteDataBaseImpl>()
            )
        }
    }
}
