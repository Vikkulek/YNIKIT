package com.example.ynikit.ui.gallery

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ynikit.R
import com.example.ynikit.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private val presentation = "https://drive.google.com/file/d/1TIZetB_KpVuUIJbUBP0DnKqXCOdV0N5Z/view?usp=drivesdk"
    private val isp01 = "https://mgutm.ru/wp-content/uploads/2024/09/09.02.07-opop_veb_2024.pdf"
    private val isp02 = "https://mgutm.ru/wp-content/uploads/doc/edu/oop/opop-09.02.07-informaczionnye-sistemy-i-programmirovanie-ppssz-2023-programmist.pdf"
    private val isp03 = "https://mgutm.ru/wp-content/uploads/doc/edu/oop/opop-09.02.07-informaczionnye-sistemy-i-programmirovanie-ppssz-2023-razrabochik-veb-i-multimedijnyh-prilozhenij.pdf"
    private val isp04 = "https://mgutm.ru/wp-content/uploads/doc/edu/oop/spo/09.02.07-informaczionnye-sistemy-i-programmirovanie-ppssz-2022-programmist.pdf"
    private val isp05 = "https://mgutm.ru/wp-content/uploads/doc/edu/oop/spo/09.02.07-informaczionnye-sistemy-i-programmirovanie-ppssz-2022-razrabochik-veb-i-multimedijnyh-prilozhenij.pdf"
    private val isp06 = "https://disk.yandex.ru/d/sU_j5WuHWwc8-w"
    private val isp07 = "https://disk.yandex.ru/d/zxbagrC34YgYuA"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val contentPresentation: ImageView = root.findViewById(R.id.PresentationImageView)
        val contentISP01: TextView = root.findViewById(R.id.contentISP01)
        val contentISP02: TextView = root.findViewById(R.id.contentISP02)
        val contentISP03: TextView = root.findViewById(R.id.contentISP03)
        val contentISP04: TextView = root.findViewById(R.id.contentISP04)
        val contentISP05: TextView = root.findViewById(R.id.contentISP05)
        val contentISP06: TextView = root.findViewById(R.id.contentISP06)
        val contentISP07: TextView = root.findViewById(R.id.contentISP07)

        contentPresentation.setOnClickListener {
            openWebPage(presentation)
        }
        contentISP01.setOnClickListener {
            openWebPage(isp01)
        }
        contentISP02.setOnClickListener {
            openWebPage(isp02)
        }
        contentISP03.setOnClickListener {
            openWebPage(isp03)
        }
        contentISP04.setOnClickListener {
            openWebPage(isp04)
        }
        contentISP05.setOnClickListener {
            openWebPage(isp05)
        }
        contentISP06.setOnClickListener {
            openWebPage(isp06)
        }
        contentISP07.setOnClickListener {
            openWebPage(isp07)
        }
        return root
    }

    private fun openWebPage(url: String) {
        val context = context ?: return

        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent) // Пробуем открыть без лишних проверок
        } catch (e: Exception) {
            Toast.makeText(
                context,
                "Ошибка: ${e.localizedMessage}. Установите браузер.",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}