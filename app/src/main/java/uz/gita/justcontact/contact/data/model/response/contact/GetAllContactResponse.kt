package uz.gita.justcontact.contact.data.model.response.contact

import uz.gita.justcontact.contact.data.source.local.room.entity.ContactEntity

/*
   Author: Zukhriddin Kamolov
   Created: 25.04.2022 at 6:48
   Project: JustContact
*/
data class GetAllContactResponse(
	val firstName: String,
	val lastName: String,
	val phone: String,
	val id: Int) {

	fun toContactEntity(): ContactEntity = ContactEntity(firstName, lastName, phone, id)
}
