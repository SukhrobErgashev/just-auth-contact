package uz.gita.justcontact.contact.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.gita.justcontact.BuildConfig.BASE_URL
import uz.gita.justcontact.contact.data.source.remote.api.AuthApi
import uz.gita.justcontact.contact.data.source.remote.api.ContactApi
import javax.inject.Singleton

/*
   Author: Zukhriddin Kamolov
   Created: 09.05.2022 at 15:04
   Project: JustContact
*/

@[Module InstallIn(SingletonComponent::class)]
class NetworkModule {

    fun getInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    fun getClient(): OkHttpClient = OkHttpClient.Builder().addInterceptor(getInterceptor()).build()

    @[Provides Singleton]
    fun getRetrofit(): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(getClient())
        .build()

    @[Provides Singleton]
    fun getContactApi(retrofit: Retrofit): ContactApi = retrofit.create(ContactApi::class.java)

    @[Provides Singleton]
    fun getAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)
}