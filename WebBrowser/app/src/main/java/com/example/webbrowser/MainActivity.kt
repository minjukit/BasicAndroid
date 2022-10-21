package com.example.webbrowser

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.webbrowser.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 1. 웹뷰 기본설정
        binding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() { // 웹뷰 클라이언트 재정의
                override fun onPageFinished( //페이지가 끝날 때
                    view: WebView?,
                    url: String?
                ) {
                    binding.urlEditText.setText(url) // edittext에 해당 url 표시
                }
            }
        }

        binding.webView.loadUrl("https://www.google.com") //구글 사이트 로딩

        // 2. 버튼 클릭 이벤트
        binding.urlEditText.setOnEditorActionListener { textView, actionId, keyEvent -> //setOnEditorAction리스너는 글자 입력될때마다 호출
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {//키보드 서치버튼 누르면
                binding.webView.loadUrl(binding.urlEditText.text.toString())
                true // true 반환하면 이벤트 종료
            } else {
                false
            }
        }

        // 5-2. 컨텍스트 메뉴 표시할 뷰 등록
        registerForContextMenu(binding.webView)


    }
    // 3. 뒤로가기 버튼 재정의
    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {// 이전 페이지로 갈 수 있다면
            binding.webView.goBack()
        } else {
            super.onBackPressed() //원래 동작 수행
        }
    }

    // 4-1. 옵션 메뉴 Activity에 표시
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu) //메뉴리소스 지정
        return true // 트루반환시 액티비티에 메뉴 있다
    }

    // 4-2. 옵션메뉴 클릭이벤트
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_google, R.id.action_home -> {
                binding.webView.loadUrl("https://www.google.com")
                return true
            }
            R.id.action_naver -> {
                binding.webView.loadUrl("https://www.naver.com")
                return true
            }
            R.id.action_daum -> {
                binding.webView.loadUrl("https://www.daum.net")
                return true
            }
            R.id.action_call -> {
                val intent = Intent(Intent.ACTION_DIAL) //암시적 인텐트
                intent.data = Uri.parse("tel:02-000-000")
                if(intent.resolveActivity(packageManager) != null){
                    startActivity(intent)
                }
                return true
            }
            R.id.action_send_text -> {
                binding.webView.url?.let{ url->
                    //Extension.kt의 문자열 확장함수
                    sendMessage(url,"02-000-0000")
                }
                return true
            }
            R.id.action_email -> {
                binding.webView.url?.let{ url->
                    email("minju0426@naver.com","추천 사이트 기록", url)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 5-1. 컨텍스트 메뉴 등록
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context,menu) //컨텍스트 메뉴 리소스 지정
    }

    // 5-3. 컨텍스트 메뉴 클릭 이벤트

    override fun onContextItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.action_share ->{
                binding.webView.url?.let{
                    share(it)
                }
                return true
            }
            R.id.action_browser ->{
                binding.webView.url?.let{
                        //url-> browse(url)
                    browse(it)
                }
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

}


