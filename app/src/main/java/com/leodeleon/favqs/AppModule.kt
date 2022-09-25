package com.leodeleon.favqs

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.leodeleon.favqs.data.Api
import com.leodeleon.favqs.data.AuthInterceptor
import com.leodeleon.favqs.data.IPrefsRepo
import com.leodeleon.favqs.data.PrefsRepo
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File

private const val BASE_URL = "https://favqs.com/api/"
private const val PREFS_NAME = "app_prefs"

private val Context.dataStore by preferencesDataStore(
    name = PREFS_NAME,
)

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideAuthInterceptor(dataRepo: IPrefsRepo) =
        AuthInterceptor(dataRepo)

    @Provides
    fun provideApi(authInterceptor: AuthInterceptor, @ApplicationContext context: Context): Api {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpBuilder = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)

        val moshi = Moshi.Builder()
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(okHttpBuilder.build())
            .build()
            .create(Api::class.java)
    }

    @Provides
    fun provideDataStore(@ApplicationContext context: Context) = context.dataStore
}