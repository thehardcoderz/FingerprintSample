package com.hardcoderz.fingerprintsample

import android.annotation.SuppressLint
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.multidots.fingerprintauth.FingerPrintAuthCallback
import com.multidots.fingerprintauth.FingerPrintAuthHelper
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), FingerPrintAuthCallback {
    lateinit var fingerprintHelper: FingerPrintAuthHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fingerprintHelper = FingerPrintAuthHelper.getHelper(this, this)
    }

    override fun onResume() {
        super.onResume()
        fingerprintHelper.startAuth()
    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            fingerprintHelper.stopAuth()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onNoFingerPrintHardwareFound() {
        labelHelper.text = "Fingerprint authentication not supported on the device"
    }

    override fun onAuthFailed(errorCode: Int, errorMessage: String?) {
        toast("Authentication Failed")
    }

    @SuppressLint("SetTextI18n")
    override fun onNoFingerPrintRegistered() {
        labelHelper.text = "No Fingerprint registered"
    }

    @SuppressLint("SetTextI18n")
    override fun onBelowMarshmallow() {
        labelHelper.text = "Fingerprint feature is not available on this OS"
    }

    override fun onAuthSuccess(cryptoObject: FingerprintManager.CryptoObject?) {
        snackbar(rootView, "Authentication Success!")
    }
}
