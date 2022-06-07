package uz.gita.justcontact.contact.data.model.request.contact
/*
   Author: Zukhriddin Kamolov
   Created: 25.04.2022 at 6:48
   Project: JustContact
*/
data class AddContactRequest(
	val firstName: String,
	val lastName: String,
	val phone: String
)
