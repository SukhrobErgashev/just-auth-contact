package uz.gita.justcontact.contact.data.model.request.auth
/*
   Author: Zukhriddin Kamolov
   Created: 25.04.2022 at 6:48
   Project: JustContact
*/
data class VerifyRegRequest(
	val code: String,
	val phone: String
)
