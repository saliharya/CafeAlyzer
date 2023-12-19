package com.cafealyzer.cafealyzer.di

import com.cafealyzer.cafealyzer.remote.service.AuthService
import com.cafealyzer.cafealyzer.remote.service.HistoryService
import com.cafealyzer.cafealyzer.remote.service.MapService
import com.cafealyzer.cafealyzer.remote.service.UserService
import com.cafealyzer.cafealyzer.remote.util.Constants
import com.cafealyzer.cafealyzer.remote.util.HttpInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Singleton
    @Provides
    fun provideClient(headerInterceptor: HttpInterceptor): OkHttpClient {
        val builder =
            OkHttpClient.Builder().readTimeout(Constants.NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .connectTimeout(Constants.NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(Constants.NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .addInterceptor(headerInterceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl("https://cafeanalyzer-api-wzq7rgzyta-et.a.run.app/")
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideMapService(retrofit: Retrofit): MapService {
        return retrofit.create(MapService::class.java)
    }

    @Provides
    @Singleton
    fun provideHistoryService(retrofit: Retrofit): HistoryService {
        return retrofit.create(HistoryService::class.java)
    }
}