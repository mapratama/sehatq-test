package sehatq.android.products.utils

import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth

object Utils {

    fun facebookLogout() {
        FirebaseAuth.getInstance().signOut()
        LoginManager.getInstance().logOut()
    }
}