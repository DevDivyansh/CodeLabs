package com.example.loginportal.ui.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.loginportal.R
import kotlinx.android.synthetic.main.main_fragment.*

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view:View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        val submitButton = view.findViewById<Button>(R.id.submitButton)
        submitButton.setOnClickListener {
            viewModel.submit(email = emailEditText.text.toString(), password = passwordEditText.text.toString())
        }
        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer { viewState ->
            when(viewState){
                LoginArea.Some -> {
                    progressView.isGone = true
                    emailEditText.error = null
                    passwordEditText.error = null
                    emailEditText.isEnabled = true
                    passwordEditText.isEnabled = true
                    submitButton.isEnabled = true

                }
                LoginArea.Progress -> {
                    progressView.isVisible = true
                    emailEditText.isEnabled = false
                    passwordEditText.isEnabled = false
                    submitButton.isEnabled = false


                }
                is LoginArea.Failed -> {
                    progressView.isGone = true
                    emailEditText.error = viewState.message
                    emailEditText.isEnabled = true
                    passwordEditText.isEnabled = true
                    submitButton.isEnabled = true


                }
                is LoginArea.Succeed -> {
                    progressView.isGone = true
                    emailEditText.error = null
                    emailEditText.isEnabled = false
                    passwordEditText.isEnabled = false
                    submitButton.isEnabled = false
                    loginTitleView.text = viewState.message

                }
            }

        })

    }
}