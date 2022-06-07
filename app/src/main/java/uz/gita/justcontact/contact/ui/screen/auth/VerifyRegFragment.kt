package uz.gita.justcontact.contact.ui.screen.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.justcontact.R
import uz.gita.justcontact.contact.data.model.request.auth.VerifyRegRequest
import uz.gita.justcontact.contact.presentation.auth.VerifyViewModel
import uz.gita.justcontact.contact.util.showToast
import uz.gita.justcontact.databinding.FragmentVerifyRegBinding

@AndroidEntryPoint
class VerifyRegFragment : Fragment(R.layout.fragment_verify_reg) {
    private val binding by viewBinding(FragmentVerifyRegBinding::bind)
    private val viewModel by viewModels<VerifyViewModel>()
    private val navArgs by navArgs<VerifyRegFragmentArgs>()
    val handler = Handler(Looper.getMainLooper())
    val a = lifecycleScope

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.bVerifyProgressBar.hide()
        screenWorks()
        observers()

    }

    private fun resendAction() {
        val resendButton: TextView = binding.bResendButton
        binding.bResendButton.isClickable = false
        resendButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.resend_pale_color))


        a.launch {
            for (i in 30 downTo 0){
                delay(1000)
                handler.post {
                    if (i<10) binding.bResendButton.text = "00:0"+i
                    else binding.bResendButton.text = "00:"+i
                }
            }
            handler.post {
                binding.bResendButton.text = "resend code"
                binding.bResendButton.isClickable = true
                resendButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
        }

    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun observers() {
        viewModel.progressLiveData.observe(viewLifecycleOwner, progressObserver)
        viewModel.verifySuccessLiveData.observe(this, verifySuccessObserver)
        viewModel.resendSuccessLiveData.observe(viewLifecycleOwner, resendSuccessObserver)
        viewModel.notConnectionLiveData.observe(viewLifecycleOwner, notConnectionObserver)
        viewModel.errorLiveData.observe(viewLifecycleOwner, errorObserver)
    }

    private fun screenWorks() {
        binding.bVerifyButton.setOnClickListener {
            val code = binding.bCodeEditText.text.toString()

            if (code.length == 6) {
                viewModel.userVerify(VerifyRegRequest(code, navArgs.data.phone))
            } else {
                showToast("Insert 6 digit code!")
            }
        }

        binding.bResendButton.setOnClickListener {
            viewModel.regResend(navArgs.data)
            it.visibility = View.INVISIBLE
        }

        binding.bResendButton.setOnClickListener {
            resendAction()
            viewModel.regResend(navArgs.data)
        }
    }

    val progressObserver = Observer<Boolean> {
        if (it) binding.bVerifyProgressBar.show()
        else binding.bVerifyProgressBar.hide()
    }

    val verifySuccessObserver = Observer<Unit> {
        a?.cancel()
        showToast("Successful verified in")
        findNavController().navigate(VerifyRegFragmentDirections.actionVerifyRegFragmentToHomeFragment())
    }

    val resendSuccessObserver = Observer<Unit> {
        showToast("Code sent again")
    }

    val notConnectionObserver = Observer<String> { showToast(it) }

    val errorObserver = Observer<String> { showToast(it) }

}