package com.example.square.di.square

import com.example.square.api.SquareRepository
import com.example.square.api.SquareRepositoryImpl
import com.example.square.api.services.SquareService
import com.example.square.common.Config
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
internal class SquareRepositoriesModule {

    @Provides
    @Singleton
    fun provideSquareRepositoriesService(retrofit: Retrofit): SquareService =
        retrofit.create(SquareService::class.java)

    // Provide retrofit instance for square services
    @Provides
    @Singleton
    fun provideRetrofit(converterFactory: Converter.Factory, client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(Config.SQUARE_BASE_URL)
            .client(client)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()

    @Provides
    @Singleton
    fun provideGsonConvectorFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptors: Set<@JvmSuppressWildcards Interceptor>): OkHttpClient =
        OkHttpClient.Builder().apply {
            interceptors.onEach { interceptor -> addInterceptor(interceptor) }
        }
            .build()

    @Provides
    @Singleton
    @IntoSet
    fun provideHttpLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideSquareRepository(squareRepositoryImpl: SquareRepositoryImpl): SquareRepository =
        squareRepositoryImpl

}

