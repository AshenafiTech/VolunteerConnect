package com.mobile.volunteerconnect.network

import com.mobile.volunteerconnect.data.api.ApiConstants
import com.mobile.volunteerconnect.data.api.userApplicationApi
import com.mobile.volunteerconnect.data.repository.userApplicationsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object userApplicationsModule {

    @Provides
    @Singleton
    @UserApplicationsRetrofit
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideUserApplicationApi(
        @UserApplicationsRetrofit retrofit: Retrofit
    ): userApplicationApi {
        return retrofit.create(userApplicationApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApplicationsRepo(userApplicationApi: userApplicationApi): userApplicationsRepo {
        return userApplicationsRepo(userApplicationApi)
    }
}
