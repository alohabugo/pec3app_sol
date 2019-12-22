package edu.uoc.android

import android.content.pm.PackageManager
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_contacts.*
import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import edu.uoc.android.util.ImageUtils
import kotlinx.android.synthetic.main.activity_settings.*
import java.io.File
import java.io.IOException

private val REQUEST_IMAGE_CAPTURE = 1
private val cameraRequestCode = 1888

class SettingsActivity : AppCompatActivity() {

    private var canOpenCamera: Boolean = false
    private var photo: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        if(ImageUtils.hasImageInStorage()) {
            // image exist in external storage
            // print last image stored
            text_no_image?.visibility = View.INVISIBLE
            val filePath = ImageUtils.getImage()
            val bit = BitmapFactory.decodeFile(filePath.toString())
            image_thumbnail?.setImageBitmap(bit)
        }
        else {
            Log.d("PEC3", "hasn't image in storage!!")
        }

        take_picture_button?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                // open camera when click button
                // camera will open only if permission are accepted
                if(canOpenCamera) {
                    Log.d("PEC3", "OPEN CAMERA!!!!!!!!!!!!")
                    openCamera()
                }
            }
        })

        // check if mobile has camera
        if(checkCameraHardware(this)) {
            // check permission
            /**
             * Ask for permission when application is running
             */
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {

                Log.d("PEC3", "REQUEST PERMISSIONS!!!!")
                ActivityCompat.requestPermissions(
                        this,
                        arrayOf(
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ),
                        cameraRequestCode
                )

                Log.d("PEC4", "END REQUEST PERMISSIONS")

            } else {
                // has permission
                canOpenCamera = true
            }
        }
    }


    private fun checkCameraHardware(context: Context): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
    }

    fun openCamera() {
        Log.d("PEC3", "OPEN CAMERA!!!!")
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        if(hasPermission(Manifest.permission.CAMERA) && hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // If request is cancelled, the result arrays are empty.
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // permission was granted
                canOpenCamera = true
            }
        }

    }

    private fun hasPermission(perm: String):Boolean {
        return(PackageManager.PERMISSION_GRANTED== ActivityCompat.checkSelfPermission(this, perm));
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("PEC3", "onActivityResult")

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Log.d("PEC3", "decode the image!!!!!")
            text_no_image?.visibility = View.INVISIBLE
            try {
                val imageBitmap = data!!.extras.get("data") as Bitmap
                ImageUtils.createImageFile(imageBitmap);
                image_thumbnail?.setImageBitmap(imageBitmap)

                Toast.makeText(this, "Image saved", Toast.LENGTH_SHORT).show()
            } catch(ex:Error) {
                Toast.makeText(this, "The imaged couldn't be saved", Toast.LENGTH_SHORT).show()
            }

        }
    }

}
