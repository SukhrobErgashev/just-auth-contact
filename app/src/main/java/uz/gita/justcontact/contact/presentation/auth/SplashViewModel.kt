package uz.gita.justcontact.contact.presentation.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.justcontact.contact.data.model.common.StartFragmentEnum
import uz.gita.justcontact.contact.domain.AuthRepository
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
//    private val authRepository = AuthRepository()

    val loginFragmentLiveData = MutableLiveData<StartFragmentEnum>()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            delay(3000)
            loginFragmentLiveData.postValue(authRepository.getStartFragment())
        }

    }


}
