package sehatq.android.products.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import sehatq.android.products.R
import sehatq.android.products.viewmodel.AuthViewModel
import java.util.*


class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: AuthViewModel
    private lateinit var googleSignInClient: GoogleSignInClient

    private var callbackManager: CallbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupViewModel()
    }

    private fun setupViewModel() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(resources.getString(R.string.request_id_token))
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        viewModel = AuthViewModel(callbackManager)
        viewModel.taskAuth.observe(this, getFacebookName)
        viewModel.onMessageError.observe(this, onMessageErrorObserver)
    }

    private val getFacebookName = Observer<Task<AuthResult>> {
        it.addOnCompleteListener(this) { task ->
            if (task.isSuccessful)
                redirectSuccesLogin()
            else {
                showToast(resources.getString(R.string.fb_error))
                task.exception!!.printStackTrace()
            }
        }
    }

    private val onMessageErrorObserver= Observer<Any> {
        showToast(it.toString())
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun redirectSuccesLogin() {
        val name = FirebaseAuth.getInstance().currentUser!!.displayName!!
        showToast("Selamat Datang $name")

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    fun buttonFacebookLoginOnClick(view: View) {
        LoginManager.getInstance().logInWithReadPermissions(this,
                Arrays.asList("email", "public_profile"))
        viewModel.facebookAuthenticate()
    }

    fun buttonGoogleLoginOnClick(view: View) {
        Log.e("######", "masuk")
        startActivityForResult(googleSignInClient.signInIntent, GOOGLE_SIGNIN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGNIN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                googleAuthenticate(task.getResult(ApiException::class.java)!!)
            } catch (e: ApiException) {
                showToast(resources.getString(R.string.google_signin_failed))
            }
        }
        else callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    fun googleAuthenticate(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        viewModel.auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful)
                        redirectSuccesLogin()
                    else
                        showToast(resources.getString(R.string.google_signin_failed))
                }
    }

    companion object {
        const val GOOGLE_SIGNIN = 1
    }
}
