package uz.gita.justcontact.contact.ui.screen.auth

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.justcontact.R
import uz.gita.justcontact.contact.data.model.request.auth.RegRequest
import uz.gita.justcontact.contact.presentation.auth.RegistrViewModel
import uz.gita.justcontact.contact.util.showToast
import uz.gita.justcontact.databinding.FragmentRegisterBinding
@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {
    private val binding by viewBinding(FragmentRegisterBinding::bind)
    private val viewModel by viewModels<RegistrViewModel>()
    private var temp: RegRequest? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.regProgressBar.hide()
        screenWorks()


//        binding.forgetButton.setOnClickListener {
//            showToast("This function will work soon...")
//        }

        viewModel.progressLiveData.observe(viewLifecycleOwner, progressObserver)
        viewModel.registerSuccessLiveData.observe(viewLifecycleOwner, successObserver)
        viewModel.notConnectionLiveData.observe(viewLifecycleOwner, notConnectionObserver)
        viewModel.errorLiveData.observe(viewLifecycleOwner, errorObserver)
    }

    fun screenWorks() {
        binding.registerButton.setOnClickListener {
            val name = binding.nameRegEditText.text.toString()
            val lastName = binding.lastNameRegEditText.text.toString()
            val phone = "+998" + binding.phoneRegEditText.text.toString()
            val password = binding.passwordRegEditText.text.toString()

            temp = RegRequest(name, lastName, password, phone)

            if (!name.equals("") && !lastName.equals("") && phone.length == 13 && password.length > 5) {
                viewModel.regUser(temp!!)
            } else {
                showToast("Insert required data!")
            }
        }

        binding.passwordRegEditText.doOnTextChanged { text, start, before, count ->
            text?.let {
                if (it.length < 6) {
                    binding.passwordRegEditText.setError("Type more!")
                }
            }
        }

        binding.phoneRegEditText.doOnTextChanged { text, start, before, count ->
            text?.let {
                if (it.length < 9) {
                    binding.phoneRegEditText.setError("Type more!")
                }
            }
        }
    }


    val progressObserver = Observer<Boolean> {
        if (it) binding.regProgressBar.show()
        else binding.regProgressBar.hide()

    }
    val successObserver = Observer<Unit> {
        temp?.let { findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToVerifyRegFragment(it)) }
    }

    val notConnectionObserver = Observer<String> { showToast(it) }
    val errorObserver = Observer<String> { showToast(it) }

}