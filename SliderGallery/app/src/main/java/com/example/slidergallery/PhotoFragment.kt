package com.example.slidergallery

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.slidergallery.databinding.FragmentPhotoBinding
import java.nio.BufferUnderflowException

private const val ARG_URI = "uri" //컴파일 상수 초기화는 String, primitive 으로만 초기화 할 수 있음

class PhotoFragment : Fragment() {

    private lateinit var uri: Uri
    //프래그먼트 뷰바인딩
    private var _binding : FragmentPhotoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        //Fragment 가 생성되면 onCreate 메서드 호출
        super.onCreate(savedInstanceState)

        arguments?.getParcelable<Uri>(ARG_URI)?.let{
            uri = it
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPhotoBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //프래그먼트가 생성되면 이미지뷰에 uri경로에 있는 사진 로딩
        val imageView = binding.imageView
        //콘텐츠 프로바이저에 접근하기 위해서 컨텍스트가 필요하니 requireContext로 얻고 객체를 얻음
        val descriptor = requireContext().contentResolver.openFileDescriptor(uri,"r")
        descriptor?.use {
            val bitmap = BitmapFactory.decodeFileDescriptor(descriptor.fileDescriptor)
            imageView.load(bitmap)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(uri:Uri) = PhotoFragment().apply{
            arguments = Bundle().apply {
                putParcelable(ARG_URI,uri)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}