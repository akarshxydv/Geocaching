package com.example.geocachinglpu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import com.example.geocachinglpu.databinding.ActivityQrScannerBinding

import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class qrScanner : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityQrScannerBinding
    private var qrdatano:String=""
    private var reqrdata:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityQrScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reqrdata = intent.getStringExtra("qrdata").toString()

        binding.scanner.setOnClickListener(this)

//        var reqrdata=intent.getStringExtra("qrdata")
//        binding.qrdata.text=reqrdata
//        binding.qrdata.text=qrdatano
//        if(qrdatano==reqrdata){
//            binding.qrdata.text="Congratulation you have done this"
//        }
    }

    override fun onClick(v: View) {
        val intentIntegrator = IntentIntegrator(this)
        intentIntegrator.setPrompt("Scan a barcode or QR Code")
//        intentIntegrator.setOrientationLocked()
        intentIntegrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (intentResult != null) {
            if (intentResult.contents == null) {
                Toast.makeText(applicationContext, "Cancelled", Toast.LENGTH_SHORT).show()
            } else {

                Handler(Looper.getMainLooper()).postDelayed({
                qrdatano = intentResult.contents
                    binding.qrdata.text = reqrdata
                    if (qrdatano == reqrdata) {
                        binding.qrdata.text = "Congratulation you have done this"
                    }
//                }else binding.qrdata.text="Try right qr code for this key"

                }, 2000)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
//    fun setMessage(){
//        var reqrdata=intent.getStringExtra("qrdata")
//        binding.qrdata.text=reqrdata
//        if(qrdatano==reqrdata){
//            binding.qrdata.text="Congratulation you have done this"}
////                }else binding.qrdata.text="Try right qr code for this key"
//    }

}