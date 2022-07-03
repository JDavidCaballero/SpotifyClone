package com.jdcm.spotifyclone.di

import com.jdcm.spotifyclone.ui.home.data.network.ChannelsApiClient
import com.jdcm.spotifyclone.ui.home.ui.data.network.ChannelDetailApiClient
import com.jdcm.spotifyclone.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
/*This will Provide a single instance of retrofit for each one of the Apis we want to consume*/
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit():Retrofit{
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .retryOnConnectionFailure(true)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideRecommendedChannelsApiClient(retrofit: Retrofit): ChannelsApiClient {
        return retrofit.create(ChannelsApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideChannelDetailApiClient(retrofit: Retrofit): ChannelDetailApiClient {
        return retrofit.create(ChannelDetailApiClient::class.java)
    }



}