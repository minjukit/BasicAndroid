package com.example.slidergallery

import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) { //슈퍼클래스의 생성자를 추가

    var uris = mutableListOf<Uri>()

    override fun getItemCount(): Int {
        Log.d("size", uris.size.toString())
        return uris.size
    }

    override fun createFragment(position: Int): Fragment {
        return PhotoFragment.newInstance(uris[position])
    }

}