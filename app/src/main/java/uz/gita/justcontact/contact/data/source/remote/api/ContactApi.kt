package uz.gita.justcontact.contact.data.source.remote.api

import retrofit2.Response
import retrofit2.http.*
import uz.gita.justcontact.contact.data.model.request.contact.AddContactRequest
import uz.gita.justcontact.contact.data.model.request.contact.EditContactRequest
import uz.gita.justcontact.contact.data.model.response.contact.AddContactResponse
import uz.gita.justcontact.contact.data.model.response.contact.DeleteContactResponse
import uz.gita.justcontact.contact.data.model.response.contact.EditContactResponse
import uz.gita.justcontact.contact.data.model.response.contact.GetAllContactResponse

interface ContactApi {

    @GET("/api/v1/contact")
    suspend fun getContactCount(@Header("token") token: String): Response<List<GetAllContactResponse>>
/* headers: token: {your_access_token}

responce [
 {
     "id": 2,
     "firstName": "Suyunov",
     "lastName": "Laziz",
     "phone": "+998909191571"
 },
 .....
 {
     "id": 5,
     "firstName": "Suyunov",
     "lastName": "Laziz",
     "phone": "+998909191571"
 }
]
  */

    @POST("/api/v1/contact")
    suspend fun addContact(@Header("token") token: String, @Body contact: AddContactRequest): Response<AddContactResponse>
    /*
 headers:  "token": "247f19b1-e715-4195-8b74-1123d5926d1d"
body: {   "firstName":"Suyunov",
    "lastName":"Laziz",
    "phone":"+998909191571" }

  responce: {
    "id": 1,
    "firstName": "Suyunov",
    "lastName": "Laziz",
    "phone": "+998909191571"
}
     */


    @GET("/api/v1/contact")
    suspend fun getAllContact(@Header("token") token: String): Response<List<GetAllContactResponse>>
    /*
    headers: token: {your_access_token}

responce [
    {
        "id": 2,
        "firstName": "Suyunov",
        "lastName": "Laziz",
        "phone": "+998909191571"
    },
    .....
    {
        "id": 5,
        "firstName": "Suyunov",
        "lastName": "Laziz",
        "phone": "+998909191571"
    }
]
     */


    @PUT("/api/v1/contact")    // edit qilish
    suspend fun editContact(@Header("token") token: String, @Body contact_to_edit: EditContactRequest): Response<EditContactResponse>
/*
headers: "token": "{your_access_token}"

body {
    "id": 1,
    "firstName": "Suyunov2",
    "lastName": "Laziz2",
    "phone": "+998909190000" }

responce {
    "id": 1,
    "firstName": "Suyunov2",
    "lastName": "Laziz2",
    "phone": "+998909190000"
}
 */


    @DELETE("/api/v1/contact")
    suspend fun deleteContact(@Header("token") token: String, @Query("id") id: Int): Response<DeleteContactResponse>
/* headers: token: {your_access_token}

params/query: "id": 1

responce: {
    "id": 1,
    "firstName": "Suyunov2",
    "lastName": "Laziz2",
    "phone": "+998909190000"
}
 */


}