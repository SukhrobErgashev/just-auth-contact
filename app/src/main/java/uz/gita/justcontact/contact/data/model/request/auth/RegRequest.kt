package uz.gita.justcontact.contact.data.model.request.auth

import java.io.Serializable

/*
   Author: Zukhriddin Kamolov
   Created: 25.04.2022 at 6:48
   Project: JustContact
*/
data class RegRequest(
	val firstName: String,
	val lastName: String,
	val password: String,
	val phone: String
): Serializable
