package com.example.loginportal.ui.login

import androidx.core.os.postDelayed
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.logging.Handler

class LoginViewModel : ViewModel() {
    var area:LoginArea=LoginArea.Some

    private val _stateLiveData=MutableLiveData<LoginArea>(LoginArea.Some)
    val stateLiveData:LiveData<LoginArea> = _stateLiveData

    fun submit(email:String, password:String){
        when
        {
            isValidInvalid(email) -> _stateLiveData.value = LoginArea.Failed(message = "Email is not valid")
            isPasswordInvalid(password) -> _stateLiveData.value = LoginArea.Failed(message = "Password is not valid")

            else -> {
                _stateLiveData.value = LoginArea.Progress
                loginProcess { hasSucceed ->
                    if (hasSucceed) {
                        _stateLiveData.value = LoginArea.Succeed(message = "Welcome (^~^)")
                    } else {
                        _stateLiveData.value = LoginArea.Failed(message = "Login attempt Failed")
                    }
                }
            }
        }

    }

    private fun loginProcess(callback:(Boolean) -> Unit){
        android.os.Handler().postDelayed(1500){
            callback(true)

        }

    }

    private fun isPasswordInvalid(password: String): Boolean {
        return password.count() < 4

    }

    private fun isValidInvalid(email: String): Boolean {
        return email.singleOrNull{ it=='@'} == null

    }
    
}


sealed class LoginArea{
    object Some:LoginArea()
    object Progress:LoginArea()

    data class Failed(val message:String):LoginArea()
    data class Succeed(val message:String):LoginArea()
}