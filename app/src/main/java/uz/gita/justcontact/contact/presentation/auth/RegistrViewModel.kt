package uz.gita.justcontact.contact.presentation.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.activemedia.udic.contactapp.utils.isConnected
import uz.gita.justcontact.contact.data.model.response.MyResult
import uz.gita.justcontact.contact.domain.AuthRepository
import uz.gita.justcontact.contact.data.model.request.auth.RegRequest
import javax.inject.Inject

@HiltViewModel
class RegistrViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
//    private val repository = AuthRepository()

    val progressLiveData = MutableLiveData<Boolean>()
    val registerSuccessLiveData = MutableLiveData<Unit>()
    val notConnectionLiveData = MutableLiveData<String>()
    val errorLiveData = MutableLiveData<String>()

    fun regUser(regContact: RegRequest) {
        if (!isConnected()) {
            notConnectionLiveData.value = "Internetga ulanish mavjud emas!"
            return
        }
        progressLiveData.value = true

        progressLiveData.value = true
        authRepository.userRegister(regContact).onEach {
            when(it){
                is MyResult.Success -> { registerSuccessLiveData.postValue(Unit) }
                is MyResult.Message -> { errorLiveData.postValue(it.message) }
                is MyResult.Error -> { errorLiveData.postValue(it.error.message) }
            }
            progressLiveData.postValue(false)
        }.launchIn(viewModelScope)
    }

}

