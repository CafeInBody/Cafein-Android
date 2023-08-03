package sso.hyeon.caffeinbody

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.R
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View.GONE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import sso.hyeon.caffeinbody.database.Caffeine
import sso.hyeon.caffeinbody.database.Drinks
import sso.hyeon.caffeinbody.database.DrinksDatabase
import sso.hyeon.caffeinbody.database.Size
import sso.hyeon.caffeinbody.databinding.ActivityPlusDrinkBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*


class PlusDrinkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlusDrinkBinding
    val PERMISSION_Album = 101 // 앨범 권한 처리
    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_GALLERY_TAKE = 2
    var num:Int ?=null
    var currentPhotoPath: String? = null
    var bitmap: Bitmap? = null
    var tag = "PlusDrinkActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlusDrinkBinding.inflate(layoutInflater)
        num= intent.getIntExtra("name", 2)
        Log.e(tag, "num: $num")
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        //    supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("음료 등록하기")

        if(!checkPersmission())
              requestPermission()

        val oItems = arrayOf<CharSequence>("카메라로 사진찍기", "앨범에서 사진선택","취소")

        val oDialog: AlertDialog.Builder = AlertDialog.Builder(
            this,
            R.style.Theme_DeviceDefault_Light_Dialog_Alert
        )


        binding.confirmButton.setOnClickListener{

       //     saveBitmapAsJPGFile(bitmap)
            var drinkname = binding.editTextDrink.text.toString()
            var caffeine = binding.caffeine.text.toString().toInt()
            var madeby = binding.madeBy.text.toString()
            val tempFile = File(cacheDir, drinkname)
            tempFile.createNewFile() // 자동으로 빈 파일을 생성하기

            val out: FileOutputStream = FileOutputStream(tempFile) // 파일을 쓸 수 있는 스트림을 준비하기

            if(bitmap == null) bitmap = (binding.drinkImg.getDrawable() as BitmapDrawable).bitmap //null일때 대비
            bitmap!!.compress(
                Bitmap.CompressFormat.JPEG,
                100,
                out
            ) // compress 함수를 사용해 스트림에 비트맵을 저장하기

            out.close() // 스트림 닫아주기
            val imgpath = "$cacheDir/$drinkname"

            Toast.makeText(applicationContext, "파일 저장 성공", Toast.LENGTH_SHORT).show()

            val db = DrinksDatabase.getInstance(applicationContext)!!
            CoroutineScope(Dispatchers.IO).launch{
                if(num==0) db.drinksDao().insert(Drinks(drinkname,
                    Size(0,0,0),
                    Caffeine(caffeine,0,0),
                    "",madeby,imgpath , true,true,true))
                else if (num ==1) db.drinksDao().insert(Drinks(drinkname,
                    Size(0,0,0),
                    Caffeine(caffeine,0,0),
                    "",madeby,imgpath , true,false,true))
                else if(num == 2)
                    db.drinksDao().insert(Drinks(drinkname,
                        Size(0,0,0),
                        Caffeine(caffeine,0,0),
                        "해열·진통제",madeby,imgpath , true,false,true))
            }

            val intent = Intent(this, CaffeineListActivity::class.java)
            intent.putExtra("listnum",num)
            startActivity(intent)
            finish()
        }

        binding.drinkImg.setOnClickListener{
            oDialog
                .setTitle("이미지 선택")
                .setItems(oItems, DialogInterface.OnClickListener { dialog, which ->
                   if(which == 0) {
                       if(checkPersmission())
                           dispatchTakePictureIntent()
                       else
                           requestPermission()
                   }
                    else if(which ==1 ){
                        openGalleryForImage()
                    }


                })
                .setCancelable(false)
                .show()

        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(READ_EXTERNAL_STORAGE, CAMERA),
            REQUEST_IMAGE_CAPTURE)
    }

    // 카메라 권한 체크
    private fun checkPersmission(): Boolean {
        return (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    }

    // 권한요청 결과
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d("TAG", "Permission: " + permissions[0] + "was " + grantResults[0] + "카메라 허가 받음 예이^^")
        }else{
            Log.d("TAG","카메라 허가 못받음 ㅠ 젠장!!")
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            if (takePictureIntent.resolveActivity(this.packageManager) != null) {
                // 찍은 사진을 그림파일로 만들기
                val photoFile: File? =
                    try {
                        createImageFile()
                    } catch (ex: IOException) {
                        Log.d("TAG", "그림파일 만드는도중 에러생김")
                        null
                    }

                // 그림파일을 성공적으로 만들었다면 onActivityForResult로 보내기
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this, "com.example.cameraonly.fileprovider", it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }


    // 카메라로 촬영한 이미지를 파일로 저장해준다
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }


    private fun rotateImageIfRequired(imagePath: String): Bitmap? {
        var degrees = 0
        try {
            val exif = ExifInterface(imagePath)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> degrees = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> degrees = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> degrees = 270
            }
        } catch (e: IOException) {
            Log.e("ImageError", "Error in reading Exif data of $imagePath", e)
        }

        val decodeBounds: BitmapFactory.Options = BitmapFactory.Options()
        decodeBounds.inJustDecodeBounds = true
        var bitmap: Bitmap? = BitmapFactory.decodeFile(imagePath, decodeBounds)
        val numPixels: Int = decodeBounds.outWidth * decodeBounds.outHeight
        val maxPixels = 2048 * 1536 // requires 12 MB heap
        val options: BitmapFactory.Options = BitmapFactory.Options()
        options.inSampleSize = if (numPixels > maxPixels) 2 else 1
        bitmap = BitmapFactory.decodeFile(imagePath, options)
        if (bitmap == null) {
            return null
        }

        val matrix = Matrix()
        matrix.setRotate(degrees.toFloat())
        bitmap = Bitmap.createBitmap(
            bitmap, 0, 0, bitmap.width,
            bitmap.height, matrix, true
        )
        return bitmap
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_TAKE)
    }


    // onActivityResult 로 이미지 설정
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode){
            1 -> {
                if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){

                    // 카메라로부터 받은 데이터가 있을경우에만
                    val file = File(currentPhotoPath)
                    if (Build.VERSION.SDK_INT < 28) {
                        val bitmap = MediaStore.Images.Media
                            .getBitmap(contentResolver, Uri.fromFile(file))  //Deprecated
                        binding.drinkImg.setImageBitmap(bitmap)
                    }
                    else{
                        val decode = ImageDecoder.createSource(this.contentResolver,
                            Uri.fromFile(file))
                        bitmap = ImageDecoder.decodeBitmap(decode)
                        binding.drinkImg.setImageBitmap(bitmap)
                        binding.plusBtn.visibility = GONE
                    }
                }
            }

            2 -> {
                if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALLERY_TAKE){
                    binding.drinkImg.setImageURI(data?.data) // handle chosen image
                    binding.plusBtn.visibility = GONE
                }
            }


        }

    }
    private fun newJpgFileName() : String {
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis())
        return "${filename}.jpg"
    }


    private fun saveBitmapAsJPGFile(bitmap: Bitmap) {
        val path = File(filesDir, "image")
        if(!path.exists()){        path.mkdirs()
        }
        val file = File(path, newJpgFileName())
        var imageFile: OutputStream? = null
        try{        file.createNewFile()
            imageFile = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, imageFile)
            imageFile.close()
            Toast.makeText(this, file.absolutePath, Toast.LENGTH_LONG).show()
        }catch (e: Exception){
            null
        }
    }

    //뒤로가기 버튼
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

}