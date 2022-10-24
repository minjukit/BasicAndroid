# 슬라이더 갤러리 App



## Used
- ViewPager2
- Coil
- ViewBinding
- Timer
- Content Provider

<br>

## 기능
- 갤러리 구현
- 좌우 슬라이드 모션 
- 3초마다 자동 슬라이드

<br>

---

- Content Provider
  앱의 데이터 접근을 다른 앱에 허용하는 컴포넌트
  
  <br>
  
- 콘텐츠 프로바이더로 사진 정보 가져오기
  1. 외부 저장소 읽기 권한 부여
  2. 외부 저장소 읽기 권한 = 위험 권한이므로 사용자에게 권한 허용 요청
  3. contentResolver 객체를 이용하여 데이터를 Cursor 객체로 가져오기

<br>

- Coil

  1. 미사용 리소스 자동 해제
  2. 메모리 효율적으로 관리
  3. 이미지 비동기식 로딩 (UI 끊김X)

<br>

- ViewPager
PagerAdapter 클래스 상속받아 어댑터 생성 


<br>

코틀린에서는 
- getSupportFragmentManager() 대신 supportFragmentManager 프로퍼티로 접근 가능
- getLifecycle() 대신 lifecycle 프로퍼티로 접근 가능

