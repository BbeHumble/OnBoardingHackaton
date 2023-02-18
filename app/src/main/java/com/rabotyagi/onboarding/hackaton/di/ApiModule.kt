package com.rabotyagi.onboarding.hackaton.di

import android.content.Context
import com.rabotyagi.onboarding.hackaton.data.api.ApiService
import com.rabotyagi.onboarding.hackaton.data.repository.Repository
import com.rabotyagi.onboarding.hackaton.data.settings.UserSettings
import com.rabotyagi.onboarding.hackaton.schedule.SchedulersProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesUserSettings(@ApplicationContext context: Context) = UserSettings(
        context.getSharedPreferences(
            UserSettings.AUTH_TOKEN_KEY,
            Context.MODE_PRIVATE
        )
    )

    @Singleton
    @Provides
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        userSettings: UserSettings
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader(
                        "Cookie",
                        userSettings.getUserToken() ?: ""
                    )
                    .build()
                chain.proceed(newRequest)
            }
            .addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor(ReceivedCookiesInterceptor(userSettings))
            .build()


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun providesRepository(apiService: ApiService, schedulersProvider: SchedulersProvider) =
        Repository(apiService, schedulersProvider)

    companion object {
        private const val BASE_URL = "http://83.220.169.145:8080/"
    }
}