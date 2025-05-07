package com.mobile.volunteerconnect.network

import com.mobile.volunteerconnect.data.api.ApiConstants
import com.mobile.volunteerconnect.data.api.applicantApi
import com.mobile.volunteerconnect.data.repository.applicantsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object applicantModule {

    @Provides
    @Singleton
    @ApplicantRetrofit
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApplicantApi(
        @ApplicantRetrofit retrofit: Retrofit
    ): applicantApi {
        return retrofit.create(applicantApi::class.java)
    }

    @Provides
    @Singleton
    fun provideApplicantsRepo(applicantApi: applicantApi): applicantsRepo {
        return applicantsRepo(applicantApi)
    }
}
