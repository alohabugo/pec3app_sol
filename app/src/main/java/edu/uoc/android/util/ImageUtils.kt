package edu.uoc.android.util

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.IOException
import android.R.attr.bitmap
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream


object ImageUtils {
    private val imageTheFile: String = "pac3Image.jpg"
    private val folder: String = "PEC3App";
    private var currentImage: String = ""


    fun hasImageInStorage(): Boolean {
        return getImage()!= null
    }


    fun getImage(): File {
        val file = File(getStorageDir(), this.folder+File.separator+this.imageTheFile)
        Log.d("PEC3", file.absolutePath)
        return (file);
    }

    public fun getStorageDir(): File? {
        return Environment.getExternalStorageDirectory();
    }


    @Throws(IOException::class)
    public fun createImageFile(bitmap: Bitmap) {
        Log.d("PAC3", "!!!!!!!!!!!!!!CREATE IMAGE FILE; :::: ")

        // Set Folder Path
        val storageDir = Environment.getExternalStorageDirectory().toString() + File.separator + this.folder;
        val storageDirFile = File(storageDir.toString())

        storageDirFile.mkdirs()

        val imageFile = File(getStorageDir(), this.folder+File.separator+this.imageTheFile)
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val fos = FileOutputStream(imageFile)
        fos.write(stream.toByteArray())
        fos.close()
    }

}