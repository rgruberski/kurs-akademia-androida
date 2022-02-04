package pl.gruberski.kursakademiaandroida.core.di

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.dsl.module
import pl.gruberski.kursakademiaandroida.features.data.RickAndMortyApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.sin

val networkModule = module {

    single<Interceptor> {
        HttpLoggingInterceptor()
            .apply {
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                }
                else {
                    HttpLoggingInterceptor.Level.NONE
                }
            }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
    }
    single {
        get<Retrofit>().create(RickAndMortyApi::class.java)
    }
}
