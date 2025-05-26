package com.example.apptvseries.di

// TOUT LE CONTENU EST TEMPORAIREMENT COMMENTÉ CAR HILT EST DÉSACTIVÉ

/*
import com.example.apptvseries.network.TvShowApiService
import com.example.apptvseries.repository.TvShowRepository
import com.example.apptvseries.repository.TvShowRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.episodate.com/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTvShowApiService(retrofit: Retrofit): TvShowApiService {
        return retrofit.create(TvShowApiService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTvShowRepository(apiService: TvShowApiService): TvShowRepository {
        return TvShowRepositoryImpl(apiService)
    }
}
*/