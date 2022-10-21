package com.example.webbrowser

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import java.lang.Exception

//Context 확장함수 4개

//메시지
fun Context.sendMessage(text: String, number: String = ""): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:$number"))
        intent.putExtra("sms_body", text)
        startActivity(intent)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }

}

//이메일

fun Context.email(email: String, subject: String = "", text: String = ""): Boolean {

    val intent = Intent(Intent.ACTION_SENDTO).apply {//첨부 파일이 없을 경우 이메일 인텐트 SENDTO
        data = Uri.parse("mailto:") // only email apps should handle this
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        if (subject.isNotEmpty())
            putExtra(Intent.EXTRA_SUBJECT, subject)
        if (text.isNotEmpty())
            putExtra(Intent.EXTRA_TEXT, text)
    }
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
        return true
    }
    return false
}

//문자열 공유
fun Context.share(text: String, subject: String = ""): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_SEND)//첨부 파일이 1개인 경우 SEND
        intent.type = "text/plain"
        if (subject.isNotEmpty())
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        if (text.isNotEmpty())
            intent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(intent, null))
        true
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        false
    }
}

//웹 브라우저에서 열기

fun Context.browse(url: String, newTask: Boolean = false): Boolean {

    val intent = Intent(Intent.ACTION_VIEW) //웹페이지 열기
    intent.data = Uri.parse(url)

    if(newTask){
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) //새로운 테스크를 생성하여 그 테스크안에 액티비티를 추가할때 사용합니다.
    }

    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
        return true
    }
    return false

}

