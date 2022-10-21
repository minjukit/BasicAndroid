
# 웹 브라우저 App



---

## Used
- ViewBinding
- WebView
- Implicit Intent
- Option menu & Context menu

<br>

## 기능
- 어플 내 웹 페이지 탐색
- 홈 메뉴 , 툴바
- 메뉴에서 검색사이트 변경
- 페이지를 문자나, 메일로 공유

<br>

---

<br>

- 웹뷰 사용법 
1. settings.javaScriptEnabled 기능 키기
2. webViewClient 는 WebViewClient클래스 그대로 or 재정의 지정

<br>

- 메뉴 아이템을 툴바 밖으로 노출
showAsAction속성을 ifRoom으로 설정
  - never : 절대 보이지 않음
  - ifRoom : 툴바에 여유가 있다면 노출
  - always : 항상 노출
  - withText : 글자와 icon 함께 노출
  - collapseActionView : 액션 뷰와 결합하면 축소되는 메뉴 생성

<br>

- Option Menu
1. 메뉴 리소스 파일 생성
2. 옵션 메뉴 작성
3. 옵션 메뉴 등록 onCreateOptionsMenu() : menuInflater로 메뉴 리소스 inflate해주기 
4. 옵션 메뉴 클릭 이벤트 onOptionsItemSelected(item: MenuItem)

<br>

- Context Menu
: 특정 View를 길게 클릭 시 표시되는 메뉴

1. 메뉴 리소스 파일 생성
2. 컨텍스트 메뉴 작성
3. 컨텍스트 메뉴 등록 onCreateContextMenu()
4. 컨텍스트 메뉴 클릭 이벤트 onContextItemSelected()
5. 컨텍스트 메뉴가 표시되었으면 하는 뷰 지정 registerForContextMenu(View view)

<br>

- 확장함수 사용, apply()사용

<br>

- 암시적 인텐트

안드로이드 11(api 30)이상의 경우 개인정보에 민감한 암시적 인텐트의 사용이 제한됨.

매니페스트 파일에서 패키지 가시성을 설정해주어야 암시적 인텐트 사용 가능 

```xml
  <queries>
        <intent>
            <action android:name="android.intent.action.SENDTO"/>
            <data android:scheme="*"/>
        </intent>
    </queries>
```

- Android 9 버전부터 허용되지 않은 http사이트 접근 불허
android:usesCleartextTraffic = "true"