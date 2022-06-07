package uz.gita.justcontact.contact.ui.screen.auth

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.justcontact.R
import uz.gita.justcontact.contact.data.model.request.auth.LogInRequest
import uz.gita.justcontact.databinding.FragmentLoginBinding
import uz.gita.justcontact.contact.presentation.auth.LoginViewModel
import uz.gita.justcontact.contact.util.showToast

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel by viewModels<LoginViewModel>()

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginProgressBar.hide()
        screenWorks()



        binding.registerButton.setOnClickListener {
            // viewModelga
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        viewModel.progressLiveData.observe(viewLifecycleOwner, progressObserver)
        viewModel.loginSuccessLiveData.observe(this, successObserver)
        viewModel.notConnectionLiveData.observe(viewLifecycleOwner, notConnectionObserver)
        viewModel.errorLiveData.observe(viewLifecycleOwner, errorObserver)
    }


    fun screenWorks() {
        binding.loginButton.setOnClickListener {
            val phone = "+998" + binding.phoneLoginEditText.text.toString()
            val password = binding.passwordLoginEditText.text.toString()

            if (!phone.equals("") && password.length > 5){
                viewModel.userLogin(LogInRequest(phone = phone, password = password))
            } else {
                showToast("Insert required data!")
            }
        }

        binding.passwordLoginEditText.doOnTextChanged { text, start, before, count ->
            text?.let {
                if (it.length < 6) {
                    binding.passwordLoginEditText.setError("Type more!")
                }
            }
        }

        binding.phoneLoginEditText.doOnTextChanged { text, start, before, count ->
            text?.let {
                if (it.length < 9) {
                    binding.phoneLoginEditText.setError("Type more!")
                }
            }
        }
    }




    val progressObserver = Observer<Boolean> {
        if (it) binding.loginProgressBar.show()
        else binding.loginProgressBar.hide()
    }

    val successObserver = Observer<Unit> {
        showToast("Successful logged in")
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment2())
    }

    val notConnectionObserver = Observer<String> { showToast(it) }

    val errorObserver = Observer<String> { showToast(it) }

}