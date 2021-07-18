package com.taghda.workingtestapplication.di

import android.content.Context
import androidx.room.Room
import com.taghda.workingtestapplication.data.local.ShoppingDao
import com.taghda.workingtestapplication.data.remote.PixabayAPI
import com.taghda.workingtestapplication.repositories.ShoppingRepository
import com.taghda.workingtestapplication.data.local.ShoppingItemDatabase
import com.taghda.workingtestapplication.repositories.DefaultNfcHelperReppository
import com.taghda.workingtestapplication.repositories.DefaultShoppingRepository
import com.taghda.workingtestapplication.repositories.NfcHelperRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

val BASE_URL = "http://api.exchangeratesapi.io/"

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ShoppingItemDatabase::class.java, "DATABASE_NAME").build()

    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        dao: ShoppingDao,
        api: PixabayAPI
    ) = DefaultShoppingRepository(dao, api) as ShoppingRepository

    @Singleton
    @Provides
    fun provideDefaultNfcHelperRepository() =
        DefaultNfcHelperReppository() as NfcHelperRepository

    @Singleton
    @Provides
    fun provideShoppingDao(
        database: ShoppingItemDatabase
    ) = database.shoppingDao()

    @Singleton
    @Provides
    fun providePixabayApi(): PixabayAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixabayAPI::class.java)
    }
}

















