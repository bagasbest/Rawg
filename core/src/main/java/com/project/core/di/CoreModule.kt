package com.project.core.di

import androidx.room.Room
import com.project.core.data.GamesRepository
import com.project.core.data.source.local.LocalDataSource
import com.project.core.data.source.local.room.GamesDatabase
import com.project.core.data.source.remote.RemoteDataSource
import com.project.core.data.source.remote.network.ApiService
import com.project.core.domain.repository.IGamesRepository
import com.project.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
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
        val passPhrase: ByteArray = SQLiteDatabase.getBytes("dicoding".toCharArray())
        val factory = SupportFactory(passPhrase)
        Room.databaseBuilder(
                androidContext(),
                GamesDatabase::class.java,
                "Games.db"
            )
            .openHelperFactory(factory)
            .fallbackToDestructiveMigration(false).build()
    }
}

val networkModule = module {
    single {
        val hostname = "api.rawg.io"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/VCiou7vzQkwaSffpM2uFEBocMesgB7AlNEBvXoCBd1I=")
            .add(hostname, "sha256/kIdp6NNEd8wsugYyyIYFsi1ylMCED3hZbSR8ZFsa/A4=")
            .add(hostname, "sha256/mEflZT5enoR1FuXLgYYGqnVEoZvmf9c2bVBpiOjYQ0c=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
           // .addInterceptor(ChuckerInterceptor(androidContext()))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
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