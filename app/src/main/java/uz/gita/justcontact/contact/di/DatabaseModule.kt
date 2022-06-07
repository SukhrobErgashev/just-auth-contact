package uz.gita.justcontact.contact.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.justcontact.contact.data.source.local.room.ContactDataBase
import uz.gita.justcontact.contact.data.source.local.room.dao.ContactDao
import javax.inject.Singleton

/*
   Author: Zukhriddin Kamolov
   Created: 09.05.2022 at 14:51
   Project: JustContact
*/

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @[Provides Singleton]
    fun getDatabase(@ApplicationContext context: Context):ContactDataBase =
        Room.databaseBuilder(context, ContactDataBase::class.java, "context.db")
            .build()

    @[Provides Singleton]
    fun getDao(roomDatabase: ContactDataBase): ContactDao = roomDatabase.getDao()

}