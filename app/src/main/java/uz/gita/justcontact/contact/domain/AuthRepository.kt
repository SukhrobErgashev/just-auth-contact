package uz.gita.justcontact.contact.domain

import androidx.room.RoomDatabase
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.justcontact.contact.data.model.common.StartFragmentEnum
import uz.gita.justcontact.contact.data.model.request.auth.LogInRequest
import uz.gita.justcontact.contact.data.model.request.auth.RegRequest
import uz.gita.justcontact.contact.data.model.request.auth.VerifyRegRequest
import uz.gita.justcontact.contact.data.model.response.MessageResponse
import uz.gita.justcontact.contact.data.model.response.MyResult
import uz.gita.justcontact.contact.data.source.local.MyPreference
import uz.gita.justcontact.contact.data.source.local.room.ContactDataBase
import uz.gita.justcontact.contact.data.source.remote.api.AuthApi
import uz.gita.justcontact.contact.data.source.remote.api.ContactApi
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authApi: AuthApi,
                                         private val pref: MyPreference) {
//    private val pref = MyPreference.getInstance()

    fun getStartFragment(): StartFragmentEnum = pref.startFragment


    fun userRegister(regContact: RegRequest) = flow<MyResult<String>> {

        val response = authApi.regAccount(regContact)

        if (response.isSuccessful) {
            response.body()?.let {
                pref.firstName = regContact.firstName;
                pref.lastName = regContact.lastName
                pref.phone = regContact.phone
                pref.password = regContact.password

                // "firstName": "Kamolov",
                //    "lastName": "Zukhriddin",
                //    "phone": "+998900221693",
                //    "password": "123456"
                emit(MyResult.Success(response.body()!!.message))
            }
        } else {
            pref.firstName = "";
            pref.lastName = ""
            pref.phone = ""
            pref.password = ""
            val gson = Gson()
            val errorResponse = gson.fromJson(response.errorBody()?.charStream(), MessageResponse::class.java);
            emit(MyResult.Message("Xatolik: " + errorResponse.message))
        }

    }.catch {
        emit(MyResult.Error(Throwable("Error: " + this.toString())))
    }.flowOn(Dispatchers.IO)

    fun verifyReg(request: VerifyRegRequest) = flow<MyResult<Unit>> {

        val response = authApi.verifyRegistration(request)

        if (response.isSuccessful) {
            response.body()?.let {
                pref.token = it.token
                pref.phone = it.phone
                pref.startFragment = StartFragmentEnum.MAIN
                emit(MyResult.Success(Unit))
            }
        } else {

            val gson = Gson()
            val errorResponse = gson.fromJson(response.errorBody()?.charStream(), MessageResponse::class.java);

            emit(MyResult.Message("Xatolik: " + errorResponse.message))
        }

    }.catch {
        emit(MyResult.Error(Throwable("Error: " + this.toString())))
    }.flowOn(Dispatchers.IO)


    fun logInAccount(request: LogInRequest) = flow<MyResult<Unit>> {

        val response = authApi.logInAccount(request)

        if (response.isSuccessful) {
            response.body()?.let {
                pref.token = it.token
                pref.phone = it.phone
                pref.startFragment = StartFragmentEnum.MAIN
                emit(MyResult.Success(Unit))
            }
        } else {
            val gson = Gson()
            val errorResponse = gson.fromJson(response.errorBody()?.charStream(), MessageResponse::class.java);

            emit(MyResult.Message("Xatolik: " + errorResponse.message))
        }

    }.catch {
        emit(MyResult.Error(Throwable("Error: " + this.toString())))
    }.flowOn(Dispatchers.IO)


    fun logOutAccount():Boolean {
        pref.token = ""
        pref.firstName = ""
        pref.lastName = ""
        pref.startFragment = StartFragmentEnum.LOGIN
        pref.phone = ""
        pref.password = ""
        return true
    }
}