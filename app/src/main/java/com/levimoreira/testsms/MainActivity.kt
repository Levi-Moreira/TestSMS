package com.levimoreira.testsms

import android.icu.util.TimeUnit
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential


class MainActivity : AppCompatActivity() {

    private var mVerificationId: String = ""
    private var mResendToken: PhoneAuthProvider.ForceResendingToken? = null

    private var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }

            }

            override fun onCodeSent(verificationId: String?,
                                    token: PhoneAuthProvider.ForceResendingToken?) {


                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId.toString()
                mResendToken = token

                // ...
            }
        }

        buttonSend.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        editText.text.toString(),        // Phone number to verify
                        60,                 // Timeout duration
                        java.util.concurrent.TimeUnit.SECONDS,   // Unit of timeout
                        this,               // Activity (for callback binding)
                        mCallbacks as PhoneAuthProvider.OnVerificationStateChangedCallbacks)
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {}
}
