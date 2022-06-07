package uz.gita.justcontact.contact.presentation.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.activemedia.udic.contactapp.utils.isConnected
import uz.gita.justcontact.contact.domain.AuthRepository
import uz.gita.justcontact.contact.data.model.response.MyResult
import uz.gita.justcontact.contact.data.model.request.auth.RegRequest
import uz.gita.justcontact.contact.data.model.request.auth.VerifyRegRequest
import javax.inject.Inject

/*
   Author: Zukhriddin Kamolov
   Created: 25.04.2022 at 18:35
   Project: JustContact
*/
@HiltViewModel
class VerifyViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
//    private val authRepository = AuthRepository()

    val progressLiveData = MutableLiveData<Boolean>()
    val verifySuccessLiveData = MutableLiveData<Unit>()
    val resendSuccessLiveData = MutableLiveData<Unit>()
    val notConnectionLiveData = MutableLiveData<String>()
    val errorLiveData  = MutableLiveData<String>()


    fun userVerify(request: VerifyRegRequest) {
        if (!isConnected()) {
            notConnectionLiveData.value = "Internetga ulanish mavjud emas!"
            return
        }
        progressLiveData.value = true
        authRepository.verifyReg(request).onEach {
            when(it){
                is MyResult.Success -> { verifySuccessLiveData.postValue(Unit) }
                is MyResult.Message -> { errorLiveData.postValue(it.message) }
                is MyResult.Error -> { errorLiveData.postValue(it.error.message) }
            }
            progressLiveData.postValue(false)
        }.launchIn(viewModelScope)
    }

    fun regResend(regContact: RegRequest) {
        if (!isConnected()) {
            notConnectionLiveData.value = "Internetga ulanish mavjud emas!"
            return
        }
        progressLiveData.value = true

        progressLiveData.value = true
        authRepository.userRegister(regContact).onEach {
            when(it){
                is MyResult.Success -> { resendSuccessLiveData.postValue(Unit) }
                is MyResult.Message -> { errorLiveData.postValue(it.message) }
                is MyResult.Error -> { errorLiveData.postValue(it.error.message) }
            }
            progressLiveData.postValue(false)
        }.launchIn(viewModelScope)
    }
}