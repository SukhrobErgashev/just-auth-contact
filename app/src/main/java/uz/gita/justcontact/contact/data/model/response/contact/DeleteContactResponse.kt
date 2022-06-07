package uz.gita.justcontact.contact.data.model.response.contact
/*
   Author: Zukhriddin Kamolov
   Created: 25.04.2022 at 6:48
   Project: JustContact
*/
data class DeleteContactResponse(
	val firstName: String,
	val lastName: String,
	val phone: String,
	val id: Int
)
