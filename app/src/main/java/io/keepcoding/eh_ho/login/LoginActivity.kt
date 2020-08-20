package io.keepcoding.eh_ho.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.RequestError
import io.keepcoding.eh_ho.data.SignInModel
import io.keepcoding.eh_ho.data.SignUpModel
import io.keepcoding.eh_ho.data.UserRepo
import io.keepcoding.eh_ho.topics.TopicsActivity
import io.keepcoding.eh_ho.isFirsTimeCreated
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.container
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_in.inputPassword
import kotlinx.android.synthetic.main.fragment_sign_in.inputUsername
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*

class LoginActivity : AppCompatActivity(),
    SignInFragment.SignInInteractionListener,
    SignUpFragment.SignUpInteractionListener {

    val signUpFragment = SignUpFragment()
    val signInFragment = SignInFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (isFirsTimeCreated(savedInstanceState)) {
            checkSession()
        }
    }

    private fun checkSession() {
        if (UserRepo.isLogged(this.applicationContext)) {
            showTopics()
        } else {
            onGoToSignIn()
        }
    }

    private fun showTopics() {
        Toast.makeText(this, "Cuando el logeo ha sido correcto Estoy en showTopics() de LoginActivity !", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, TopicsActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onGoToSignUp() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, signUpFragment)
            .commit()
    }

    override fun onSignIn(signInModel: SignInModel) {
        // Validar formulario de Login
        if (isFormLoginValid()) {

            enableLoading()
            UserRepo.signIn(this.applicationContext,
                signInModel,
                { showTopics() },
                { error ->
                    enableLoading(false)
                    handleError(error)
                }
            )
        } else {
            // Si estan vación muestrame errores showErrors()
            showErrorsLogin()
        }
    }

    // Validación de formulario Login
    private fun isFormLoginValid() = inputUsername.text.isNotEmpty() && inputPassword.text.isNotEmpty()
    // Validación de formulario
    private fun showErrorsLogin() {
        if (inputUsername.text.isEmpty())
            inputUsername.error = getString(R.string.error_empty)
        if (inputPassword.text.isEmpty())
            inputPassword.error = getString(R.string.error_empty)
    }

    override fun onSignUp(signUpModel: SignUpModel) {
        // Validar formulario de Registro
        if (isFormRegisterValid()) {
            enableLoading()
            UserRepo.signUp(this.applicationContext,
                signUpModel,
                {
                    enableLoading(false)
                    Snackbar.make(container, R.string.message_sign_up, Snackbar.LENGTH_LONG).show()
                },
                {
                    enableLoading(false)
                    handleError(it)
                }
            )
        } else {
            showErrorsRegister()
        }

    }

    // Validación de formulario Login
    private fun isFormRegisterValid() =
        inputEmail.text.isNotEmpty()
                && inputUsername.text.isNotEmpty()
                && inputPassword.text.isNotEmpty()
                && inputConfirmPassword.text.isNotEmpty()

    // Validación de formulario Registro
    private fun showErrorsRegister() {
        if (inputEmail.text.isEmpty())
            inputEmail.error = getString(R.string.error_empty)
        if (inputUsername.text.isEmpty())
            inputUsername.error = getString(R.string.error_empty)
        if (inputPassword.text.isEmpty())
            inputPassword.error = getString(R.string.error_empty)
        if (inputConfirmPassword.text.isEmpty())
            inputConfirmPassword.error = getString(R.string.error_empty)
    }


    private fun handleError(error: RequestError) {
        if (error.messageResId != null)
            Snackbar.make(container, error.messageResId, Snackbar.LENGTH_LONG).show()
        else if (error.message != null)
            Snackbar.make(container, error.message, Snackbar.LENGTH_LONG).show()
        else
            Snackbar.make(container, R.string.error_default, Snackbar.LENGTH_LONG).show()
    }

    override fun onGoToSignIn() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, signInFragment)
            .commit()
    }



    private fun enableLoading(enabled: Boolean = true) {
        if (enabled) {
            fragmentContainer.visibility = View.INVISIBLE
            viewLoading.visibility = View.VISIBLE
        } else {
            fragmentContainer.visibility = View.VISIBLE
            viewLoading.visibility = View.INVISIBLE
        }
    }
}
