package uz.gita.justcontact.contact.presentation.contact

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.justcontact.contact.domain.AuthRepository
import uz.gita.justcontact.contact.domain.ContactRepository
import javax.inject.Inject

/*
   Author: Zukhriddin Kamolov
   Created: 25.04.2022 at 15:59
   Project: JustContact
*/

@HiltViewModel
class MainScreenViewModel  @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
//    private val authRepository = AuthRepository()

    val progressLiveData = MutableLiveData<Boolean>()
    val logOutSuccessLiveData = MutableLiveData<Unit>()

    fun userLogOut() {
        progressLiveData.value = true
        if (authRepository.logOutAccount()){
            logOutSuccessLiveData.value = Unit
            progressLiveData.value = false
        }
    }

}