package com.paulik.professionaldevelopment.di.modules

import com.paulik.professionaldevelopment.data.WordTranslationInteractorImpl
import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.repo.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class InteractorModule {

    @Provides
    internal fun provideInteractor(
        @Named(NAME_REMOTE) repositoryRemote: Repository<List<DataEntity>>,
        @Named(NAME_LOCAL) repositoryLocal: Repository<List<DataEntity>>
    ) = WordTranslationInteractorImpl(repositoryRemote, repositoryLocal)
}