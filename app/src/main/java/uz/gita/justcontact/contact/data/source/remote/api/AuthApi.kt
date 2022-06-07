package uz.gita.justcontact.contact.data.source.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import uz.gita.justcontact.contact.data.model.request.auth.LogInRequest
import uz.gita.justcontact.contact.data.model.request.auth.RegRequest
import uz.gita.justcontact.contact.data.model.request.auth.VerifyRegRequest
import uz.gita.justcontact.contact.data.model.response.auth.LogInResponse
import uz.gita.justcontact.contact.data.model.response.auth.RegResponse
import uz.gita.justcontact.contact.data.model.response.auth.VerifyRegResponse

interface AuthApi {

    @POST("/api/v1/register")
    suspend fun regAccount(@Body request: RegRequest): Response<RegResponse>
    /*
    body: {
    "firstName": "Kamolov",
    "lastName": "Zukhriddin",
    "phone": "+998900221693",
    "password": "123456"
}

       responce: {
    "message": "Sms habar +998900221693 raqamiga yuborildi"}
     */

    @POST("/api/v1/register/verify")
    suspend fun verifyRegistration(@Body request: VerifyRegRequest): Response<VerifyRegResponse>
/*
body: {
    "phone": "+998900221693",
    "code": "904205"
}

responce: {
    "token": "af08c3ef-9b74-4ff1-adc7-d820820ccd38",
    "phone": "+998900221693"
}
 */


    @POST("/api/v1/login")
    suspend fun logInAccount(@Body request: LogInRequest): Response<LogInResponse>
    /*
    body: {
    "phone": "+998900221693",
    "password": "123456" }

    responce: {
    "token": "8177caff-414b-44ec-aada-304ff3d3d6a6",
    "phone": "+998900221693" }
     */



}