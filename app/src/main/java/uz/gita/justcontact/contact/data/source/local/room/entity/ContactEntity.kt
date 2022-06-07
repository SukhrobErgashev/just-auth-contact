package uz.gita.justcontact.contact.data.source.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.gita.justcontact.contact.data.model.response.contact.GetAllContactResponse

/*
   Author: Zukhriddin Kamolov
   Created: 09.05.2022 at 14:39
   Project: JustContact
*/

@Entity(tableName = "contacts")
data class ContactEntity(
    val firstName: String,
    val lastName: String,
    val phone: String,
    @PrimaryKey
    val id: Int ) {

    fun toGetAllContactResponse(): GetAllContactResponse = GetAllContactResponse(firstName, lastName, phone, id)
}
