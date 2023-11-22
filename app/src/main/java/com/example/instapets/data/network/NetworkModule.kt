package com.example.instapets.data.network

import com.example.instapets.BuildConfig
import com.example.instapets.data.RepositoryImplementation
import com.example.instapets.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val CAT_API_NAME = "cat_api"
    private const val DOG_API_NAME = "dog_api"

    const val CAT_SERVICE_NAME = "cat_service"
    const val DOG_SERVICE_NAME = "dog_service"

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor:HeaderInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(interceptor).build()

    @Provides
    @Singleton
    @Named(CAT_API_NAME)
    fun provideCatRetrofit(client: OkHttpClient):Retrofit{
        return Retrofit
            .Builder()
            .baseUrl(BuildConfig.CAT_API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named(DOG_API_NAME)
    fun provideDogRetrofit(client: OkHttpClient):Retrofit{
        return Retrofit
            .Builder()
            .baseUrl(BuildConfig.DOG_API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Named(CAT_SERVICE_NAME)
    fun provideCatAPIService(@Named(CAT_API_NAME) retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }

    @Provides
    @Named(DOG_SERVICE_NAME)
    fun provideDogAPIService(@Named(DOG_API_NAME) retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }

    @Provides
    fun provideRepository(apiClient: APIClient): Repository {
        return RepositoryImplementation(apiClient)
    }

}