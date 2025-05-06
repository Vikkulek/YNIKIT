package com.example.ynikit.ui.slideshow

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
import com.example.ynikit.databinding.FragmentSlideshowBinding
import com.example.ynikit.ui.gallery.GalleryViewModel


class SlideshowFragment : Fragment() {
    private var _binding: FragmentSlideshowBinding? = null
    private val binding get() = _binding!!
    private val checkButton = "https://docs.google.com/forms/d/e/1FAIpQLSfMhIJXW09TJSO9fqp3SByOClLYpx4dYScFrXlRFWMUIG3xIQ/viewform?usp=header"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)

        // Способ 1: Если используете View Binding напрямую (лучше)
        binding.checkButton.setOnClickListener {
            openWebPage(checkButton)
        }

        // Способ 2: Если используете findViewById (убедитесь, что ID существует)
        // val contentPresentation: ImageView = binding.root.findViewById(R.id.checkButton)
        // contentPresentation.setOnClickListener { openWebPage(checkButton) }

        return binding.root
    }

    private fun openWebPage(url: String) {
        if (!isAdded) return
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                "Ошибка: ${e.localizedMessage}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}