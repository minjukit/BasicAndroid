package com.example.slidergallery

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.slidergallery.databinding.ActivityMainBinding
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    //권한 요청을 처리하는 객체
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        isGranted -> //요청한 권한의 결과
        if (isGranted){
            getAllPhotos()
        }else{

            Toast.makeText(this, "권한 거부됨", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //권한 부여 확인
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){ // 권한 없으면

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) // true 반환 시 이전에 거부한 적 있음
           ){ // 이전에 이미 권한이 거부되었을 때는 별도로 메시지 표시
                AlertDialog.Builder(this).apply {
                    setTitle("권한 요청")
                    setMessage("사진 정보를 얻으려면 외부 저장소 권한이 필요합니다")
                    setPositiveButton("승인"){_,_ ->
                        //권한 요청
                        requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }
                    setNegativeButton("거부", null)
                }.show() //다이얼로그 표시
            }else {
                //읽기 권한 요청
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            return

        }else{// 이미권한허용되었다면
            getAllPhotos()
        }

    }

    private fun getAllPhotos(){
        val uris = mutableListOf<Uri>() //모든 사진 정보를 담을 리스트

        //모든 사진 정보 가져오기
        contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, //어떤 데이터를 가져오는지 uri형태로 = 외부저장소데이터
            null, null, null,
            "${MediaStore.Images.ImageColumns.DATE_TAKEN} DESC" //URI 와 최신순정렬
        )?.use{ //확장함수
            cursor ->
            while (cursor.moveToNext()){ //cursor 포인터로 내부적으로 데이터 이동 (이동가능하면 true)
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                //사진이 저장된 DB의 id를 붙이는 URI 얻기
                val contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,id) //uri 결합
                uris.add(contentUri)
            }
        }
        Log.d("MainAcitivity", "getAllPhotos: ${uris}")

        //viewPager2 어댑터 연결
        val adapter = PagerAdapter(supportFragmentManager, lifecycle)
        adapter.uris=uris


        binding.viewPager.adapter = adapter

        // 3초 슬라이드쇼 구현
        timer(period = 3000){ //3초 마다 Ui 변경
            //백그라운드 스레드로 동작
            runOnUiThread {
                if(binding.viewPager.currentItem< adapter.itemCount -1){
                    binding.viewPager.currentItem = binding.viewPager.currentItem + 1 //마지막페이지가 아니면 다음페이지로

                }else{
                    binding.viewPager.currentItem = 0 // 첫 페이지로
                }
            }

        }
    }

}

