package com.mobile.volunteerconnect.network

import com.mobile.volunteerconnect.data.api.ApiConstants
import com.mobile.volunteerconnect.data.api.ApiService
import com.mobile.volunteerconnect.data.api.applicantApi
import com.mobile.volunteerconnect.data.api.userApplicationApi
import com.mobile.volunteerconnect.data.repository.OrgEventsRepository
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
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor) // <- Important: Adds token to all requests
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApplicationApi(retrofit: Retrofit): userApplicationApi {
        return retrofit.create(userApplicationApi::class.java)
    }

    @Provides
    @Singleton
    fun provideApplicantApi(retrofit: Retrofit): applicantApi {
        return retrofit.create(applicantApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOrgEventsRepository(apiService: ApiService): OrgEventsRepository {
        return OrgEventsRepository(apiService)
    }

}
