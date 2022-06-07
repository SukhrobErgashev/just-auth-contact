package uz.gita.justcontact.contact.ui.screen.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.justcontact.R
import uz.gita.justcontact.contact.data.model.common.StartFragmentEnum
import uz.gita.justcontact.contact.presentation.auth.SplashViewModel
import uz.gita.justcontact.databinding.FragmentSplashBinding

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {
    val authSplashViewModel: SplashViewModel by viewModels()
    private val binding by viewBinding(FragmentSplashBinding::bind)


    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        authSplashViewModel.loginFragmentLiveData.observe(this) {
            when (it) {
                StartFragmentEnum.MAIN -> findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
                StartFragmentEnum.LOGIN -> findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
            }
        }
    }
}