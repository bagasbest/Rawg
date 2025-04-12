package com.project.core.di

import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.project.core.data.GamesRepository
import com.project.core.data.source.local.LocalDataSource
import com.project.core.data.source.local.room.GamesDatabase
import com.project.core.data.source.remote.RemoteDataSource
import com.project.core.data.source.remote.network.ApiService
import com.project.core.domain.repository.IGamesRepository
import com.project.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory {
        get<GamesDatabase>().gamesDao()
    }
    single {
        Room.databaseBuilder(
                androidContext(),
                GamesDatabase::class.java,
                "Games.db"
            ).fallbackToDestructiveMigration(false).build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(ChuckerInterceptor(androidContext()))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.rawg.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IGamesRepository> { GamesRepository(get(), get(), get()) }
}