package com.example.google_solution.Activity

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.google_solution.R
import com.example.google_solution.databinding.ActivitySignInBinding
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SignIn : AppCompatActivity() {
    private lateinit var binding:ActivitySignInBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
    private lateinit var callbackManager: CallbackManager

//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//
//        if(currentUser != null)
//        {
//            startActivity(
//                Intent(
//                    this, Home::class.java
//                )
//            )
//        }
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=  ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        callbackManager = CallbackManager.Factory.create()

        auth = Firebase.auth

//        LoginManager.getInstance().registerCallback(callbackManager, object :
//            FacebookCallback<LoginResult> {
//            override fun onSuccess(result: LoginResult) {
//                Log.d("Success", "Success")
//                handleFacebookAccessToken(result.accessToken)
//            }
//
//            override fun onCancel() {
//                Toast.makeText(this@SignIn, "Login Cancel", Toast.LENGTH_SHORT).show()
//                Log.d("cancel", "Lets go")
//                Firebase.auth.signOut()
//            }
//
//            override fun onError(error: FacebookException) {
//                Toast.makeText(this@SignIn, error.message, Toast.LENGTH_LONG).show()
//                Log.e(ContentValues.TAG, "Its definitely an error")
//            }
//        })

    binding.signUp.setOnClickListener {
        startActivity(Intent(this,SignUp::class.java))
        finish()
    }

    binding.inBut.setOnClickListener {
        startActivity(Intent(this,Home::class.java))
        finish()
    }

    }

    private fun handleFacebookAccessToken(token: AccessToken) {

        val tak = token.token
        Log.d(ContentValues.TAG, "handleFacebookAccessToken:$tak")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    val db = FirebaseFirestore.getInstance()
                    val userMap = hashMapOf(
                        "name" to user?.displayName,
                        "email" to user?.email,
                        "profileImage" to ""
                    )
                    db.collection("USERS").document(user!!.uid).set(userMap)

                    if(task.result.additionalUserInfo?.isNewUser == true) {
                        startActivity(
                            Intent(
                                this@SignIn,
                                Home::class.java
                            )
                        )
                    } else {
                        startActivity(
                            Intent(
                                this@SignIn,
                                Home::class.java
                            )
                        )
                    }
//
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
//                    updateUI(null)
                }
            }
    }
}