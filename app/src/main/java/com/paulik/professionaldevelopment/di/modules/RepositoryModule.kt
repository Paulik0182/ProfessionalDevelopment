package com.paulik.professionaldevelopment.di.modules

import com.paulik.professionaldevelopment.data.RepositoryImpl
import com.paulik.professionaldevelopment.data.retrofit.RetrofitImpl
import com.paulik.professionaldevelopment.data.room.RoomDataBaseImpl
import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.repo.Repository
import com.paulik.professionaldevelopment.domain.source.DataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideRepositoryRemote(
        @Named(NAME_REMOTE) dataSourceRemote: DataSource<List<DataEntity>>
    ): Repository<List<DataEntity>> =
        RepositoryImpl(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideRepositoryLocal(
        @Named(NAME_LOCAL) dataSourceLocal: DataSource<List<DataEntity>>
    ): Repository<List<DataEntity>> =
        RepositoryImpl(dataSourceLocal)

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideDataSourceRemote(): DataSource<List<DataEntity>> =
        RetrofitImpl()

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideDataSourceLocal(): DataSource<List<DataEntity>> = RoomDataBaseImpl()
}