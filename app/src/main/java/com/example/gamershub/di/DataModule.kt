package com.example.gamershub.di

import android.app.Application
import android.content.Context
import com.example.gamershub.BuildConfig
import com.example.gamershub.data.network.GenresApi
import com.example.gamershub.data.network.GenreClient
import com.example.gamershub.data.network.GenreClientImpl
import com.example.gamershub.data.network.mapper.ApiMapper
import com.example.gamershub.data.network.mapper.ApiMapperImpl
import com.example.gamershub.data.preferences.DeviceSharedPreferences
import com.example.gamershub.data.preferences.DeviceSharedPreferencesImpl
import com.example.gamershub.presentation.sign_in.GoogleAuthUiClient
import com.example.gamershub.presentation.sign_in.GoogleAuthUiClientImpl
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGameService(retrofit: Retrofit): GenresApi {
        return retrofit.create(GenresApi::class.java)
    }

    @Provides
    @Singleton
    fun provideApiMapper(): ApiMapper = ApiMapperImpl()

    @Provides
    @Singleton
    fun provideClient(apiMapper: ApiMapper, genresApi: GenresApi): GenreClient {
        return GenreClientImpl(genresApi, apiMapper)
    }

    @Provides
    @Singleton
    fun provideAuthUiClient(app: Application, oneTapClient: SignInClient): GoogleAuthUiClient {
        return GoogleAuthUiClientImpl(app, oneTapClient)
    }

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): DeviceSharedPreferences {
        return DeviceSharedPreferencesImpl(context)
    }

}