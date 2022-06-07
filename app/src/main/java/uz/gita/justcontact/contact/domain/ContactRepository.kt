package uz.gita.justcontact.contact.domain

import androidx.room.RoomDatabase
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import uz.gita.justcontact.contact.data.model.response.MyResult
import uz.gita.justcontact.contact.data.source.local.MyPreference
import uz.gita.justcontact.contact.data.source.remote.ApiClient
import uz.gita.justcontact.contact.data.source.remote.api.ContactApi
import uz.gita.justcontact.contact.data.model.request.contact.AddContactRequest
import uz.gita.justcontact.contact.data.model.request.contact.EditContactRequest
import uz.gita.justcontact.contact.data.model.response.MessageResponse
import uz.gita.justcontact.contact.data.model.response.contact.GetAllContactResponse
import uz.gita.justcontact.contact.data.source.local.room.ContactDataBase
import uz.gita.justcontact.contact.data.source.local.room.dao.ContactDao
import javax.inject.Inject


class ContactRepository @Inject constructor(
    private val contactApi: ContactApi,
    private val pref: MyPreference,
    private val dao: ContactDao) {
//    private val contactApi = ApiClient.retrofit.create(ContactApi::class.java)
//    private val pref = MyPreference.getInstance()

    fun getContactCount() = flow<MyResult<Int>> {
        val response = contactApi.getContactCount(pref.token)

        if (response.isSuccessful) {
            response.body()?.let {
                emit(MyResult.Success(it.size))
            }
        } else {
            val gson = Gson()
            val errorResponse = gson.fromJson(response.errorBody()?.charStream(), MessageResponse::class.java);

            if (errorResponse.message.equals("Ma'lumot yuborishda xatolik")) emit(MyResult.Message("Kontaktlar soni 0"))
            else emit(MyResult.Message("Xatolik: " + errorResponse.message))
        }

    }.catch {
        emit(MyResult.Error(Throwable("Error: " + this.toString())))
    }.flowOn(Dispatchers.IO)


    fun getRoomContactCount() = flow<Int> {
        val response = dao.getContacts()
        emit(response.size)
    }.flowOn(Dispatchers.IO)




    fun getAllContactList() = flow<MyResult<List<GetAllContactResponse>>> {
        val response = contactApi.getAllContact(pref.token)

        if (response.isSuccessful) {
            response.body()?.let {
                dao.addContactList(it.map { it.toContactEntity() })
                emit(MyResult.Success(dao.getContacts().map { it.toGetAllContactResponse() }))
            }
        } else {
            val gson = Gson()
            val errorResponse = gson.fromJson(response.errorBody()?.charStream(), MessageResponse::class.java);

            if (errorResponse.message.equals("Ma'lumot yuborishda xatolik")) emit(MyResult.Message("Xatolik: sizda kontaktlar yo'q "))
            else emit(MyResult.Message("Xatolik: " + errorResponse.message))
        }

    }.catch {
        emit(MyResult.Error(Throwable("Error: " + this.toString())))
    }.flowOn(Dispatchers.IO)

    fun getRoomAllContactList() = flow<List<GetAllContactResponse>> {
        val response = dao.getContacts().map { it.toGetAllContactResponse()}
        emit(response)
    }.flowOn(Dispatchers.IO)


    fun addContactData(contact: AddContactRequest) = flow<MyResult<Unit>> {
        val response = contactApi.addContact(pref.token, contact)

        if (response.isSuccessful) {
            response.body()?.let {
                //
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


    fun editContact (contact: EditContactRequest) = flow<MyResult<Unit>> {
        val response = contactApi.editContact(pref.token, contact)

        if (response.isSuccessful) {
            response.body()?.let {
                dao.editContact(contact.id, contact.firstName, contact.lastName, contact.phone)
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


    fun deleteContact(contact_Id: Int) = flow<MyResult<Unit>> {
        val response = contactApi.deleteContact(pref.token, contact_Id)

        if (response.isSuccessful) {
            response.body()?.let {
                dao.deleteContact(contact_Id)
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


}