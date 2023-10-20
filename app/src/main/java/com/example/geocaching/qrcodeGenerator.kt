package com.example.geocaching

import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.geocaching.databinding.ActivityQrcodeGeneratorBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class qrcodeGenerator : AppCompatActivity() {
    private lateinit var binding:ActivityQrcodeGeneratorBinding
    private lateinit var qrdata:String
    private lateinit var keys:String
    private lateinit var databaseref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityQrcodeGeneratorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var coursetitle=intent.getStringExtra("coursetitle")
        binding.coursetitle.text="Enter detail for ${coursetitle}"

        binding.qrgenerator.setOnClickListener() {
            qrdata=generateRandom4DigitNumber()

            val qrCodeBitmap = generateQRCode(qrdata)
            binding.qrCodeImageView.setImageBitmap(qrCodeBitmap)

        }

        binding.qrdownload.setOnClickListener(){
            saveImageToGallery()
        }
        binding.savevalue.setOnClickListener(){

            var keys =binding.keys.text.toString().trim()
            var qrudata=qrdata
            databaseref= FirebaseDatabase.getInstance().getReference("$coursetitle")
            var keyinfo=qrInfo(keys,qrudata)
            databaseref.child(qrudata).setValue(keyinfo).addOnCompleteListener {

                Toast.makeText(this,"Details Submitted",Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }










    //   generate qr code

    private fun generateQRCode(data: String): Bitmap {
        val qrCodeWriter = QRCodeWriter()
        val bitMatrix: BitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) 0xFF000000.toInt() else 0xFFFFFFFF.toInt())
            }
        }

        return bitmap
    }
    private fun generateRandom4DigitNumber(): String {
        val random = (1000..9999).random()
        return random.toString()
    }





    //   to download images


    private fun saveImageToGallery() {
        val bitmap = (binding.qrCodeImageView.drawable as BitmapDrawable).bitmap
        keys=binding.keys.text.toString().trim()
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "image_${keys}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")

        }

        val contentResolver = contentResolver
        val imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        try {
            imageUri?.let { uri ->
                val outputStream = contentResolver.openOutputStream(uri)
                outputStream?.use { os ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
                    Toast.makeText(this, "Image saved", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error saving image", Toast.LENGTH_SHORT).show()
        }
    }
}