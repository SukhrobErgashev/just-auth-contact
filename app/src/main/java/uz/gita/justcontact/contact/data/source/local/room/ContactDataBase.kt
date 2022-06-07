package uz.gita.justcontact.contact.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.gita.justcontact.contact.data.source.local.room.dao.ContactDao
import uz.gita.justcontact.contact.data.source.local.room.entity.ContactEntity

/*
   Author: Zukhriddin Kamolov
   Created: 09.05.2022 at 14:44
   Project: JustContact
*/

@Database(entities = [ContactEntity::class], version = 1)
abstract class ContactDataBase: RoomDatabase(){
    abstract fun getDao(): ContactDao
}