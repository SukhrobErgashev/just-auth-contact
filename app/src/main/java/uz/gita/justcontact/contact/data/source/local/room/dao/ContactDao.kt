package uz.gita.justcontact.contact.data.source.local.room.dao

import androidx.room.*
import uz.gita.justcontact.contact.data.source.local.room.entity.ContactEntity

/*
   Author: Zukhriddin Kamolov
   Created: 09.05.2022 at 14:39
   Project: JustContact
*/

@Dao
interface ContactDao {

    @Query("Select * from contacts")
    fun getContacts(): List<ContactEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addContactList(list: List<ContactEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addContact(list: List<ContactEntity>)

    @Query("UPDATE contacts set firstName = :firstName, lastName = :lastname, phone = :phone where id = :id")
    fun editContact(id: Int, firstName: String, lastname: String, phone: String)

    @Query("DELETE from contacts where id = :id")
    fun deleteContact(id: Int)

}