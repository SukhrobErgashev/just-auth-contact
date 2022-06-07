package uz.gita.justcontact.contact.presentation.contact

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.activemedia.udic.contactapp.utils.isConnected
import uz.gita.justcontact.contact.domain.ContactRepository
import uz.gita.justcontact.contact.data.model.response.MyResult
import uz.gita.justcontact.contact.data.model.request.contact.AddContactRequest
import uz.gita.justcontact.contact.data.model.request.contact.EditContactRequest
import uz.gita.justcontact.contact.data.model.response.contact.GetAllContactResponse
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(private val contactRepository: ContactRepository) : ViewModel() {
//    private val contactRepository = ContactRepository()

    val errorLiveData = MutableLiveData<String>()
    val notConnectionLiveData = MutableLiveData<String>()
    val progressLiveData = MutableLiveData<Boolean>()

    val countLiveData = MutableLiveData<Int>()
    val allContactListLiveData = MutableLiveData<List<GetAllContactResponse>>()
    val addContactLiveData = MutableLiveData<Unit>()
    val editContactLiveData = MediatorLiveData<Unit>()
    val deleteContactLiveData = MediatorLiveData<Unit>()

    fun getContactCounted(){
        if (!isConnected()){
            notConnectionLiveData.value = "Internet connection lost!"
            contactRepository.getRoomContactCount().onEach {
                countLiveData.value = it
            }.launchIn(viewModelScope)
            return
        }
        progressLiveData.value = true
        contactRepository.getContactCount().onEach {
            when (it) {
                is MyResult.Success -> { countLiveData.postValue(it.data!!) }
                is MyResult.Message -> { errorLiveData.postValue(it.message) }
                is MyResult.Error -> { errorLiveData.postValue(it.error.message) }
            }
            progressLiveData.postValue(false)
        }.launchIn(viewModelScope)
    }


    fun getAllContact(){
        if (!isConnected()){
            notConnectionLiveData.value = "Internet connection lost!"
            contactRepository.getRoomAllContactList().onEach {
                allContactListLiveData.value = it
            }.launchIn(viewModelScope)
            return
        }
        progressLiveData.value = true
        contactRepository.getAllContactList().onEach {
            when(it){
                is MyResult.Success -> { allContactListLiveData.postValue(it.data!!) }
                is MyResult.Message -> { errorLiveData.postValue(it.message) }
                is MyResult.Error -> { errorLiveData.postValue(it.error.message) }
            }
            progressLiveData.postValue(false)
        }.launchIn(viewModelScope)
    }

    fun addContact(contact: AddContactRequest){
        if (!isConnected()){
            notConnectionLiveData.value = "Internet connection lost!"
            return
        }

        progressLiveData.value = true
        contactRepository.addContactData(contact).onEach {
            when(it){
                is MyResult.Success -> { addContactLiveData.postValue(Unit) }
                is MyResult.Message -> { errorLiveData.postValue(it.message) }
                is MyResult.Error -> { errorLiveData.postValue(it.error.message) }
            }
            progressLiveData.postValue(false)
        }.launchIn(viewModelScope)

    }


    fun editContact(contact: EditContactRequest){
        if (!isConnected()){
            notConnectionLiveData.value = "Internet connection lost!"
            return
        }
        progressLiveData.value = true
        contactRepository.editContact(contact).onEach {
            when(it){
                is MyResult.Success -> { editContactLiveData.postValue(Unit) }
                is MyResult.Message -> { errorLiveData.postValue(it.message) }
                is MyResult.Error -> { errorLiveData.postValue(it.error.message) }
            }
            progressLiveData.postValue(false)
        }.launchIn(viewModelScope)
    }

    fun deleteContact(id: Int){
        if (!isConnected()){
            notConnectionLiveData.value = "Internetga ulanishda muammo bor"
            return
        }
        progressLiveData.value = true
        contactRepository.deleteContact(id).onEach {
            when(it){
                is MyResult.Success -> { deleteContactLiveData.postValue(Unit) }
                is MyResult.Message -> { errorLiveData.postValue(it.message) }
                is MyResult.Error -> { errorLiveData.postValue(it.error.message) }
            }
            progressLiveData.postValue(false)
        }.launchIn(viewModelScope)
    }

}