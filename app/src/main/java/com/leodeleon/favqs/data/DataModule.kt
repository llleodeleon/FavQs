package com.leodeleon.favqs.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun providerQuotesRepo(api: Api, prefsRepo: IPrefsRepo): IQuotesRepo = QuotesRepo(api, prefsRepo)

    @Provides
    fun providePrefsRepo(dataStore: DataStore<Preferences>): IPrefsRepo = PrefsRepo(dataStore)
}