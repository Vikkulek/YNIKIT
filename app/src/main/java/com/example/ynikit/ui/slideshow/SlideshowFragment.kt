package com.example.ynikit.ui.slideshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.ynikit.databinding.FragmentSlideshowBinding
//import com.example.ynikit.databinding.FragmentTestFoAbiturientBinding

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private val binding get() = _binding!!

    // Правильные ответы
    private val correctAnswers = listOf(
        "B", "B", "B", "B", "B",  // Ответы на вопросы 1-5
        "A", "B", "D", "A", "C",  // Ответы на вопросы 6-10
        "B", "B", "B", "B", "C"   // Ответы на вопросы 11-15
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Настройка кнопки проверки
        binding.checkButton.setOnClickListener {
            checkAnswers()
        }
    }

    private fun checkAnswers() {
        var score = 0
        val totalQuestions = 15

        // Проверяем каждый вопрос
        for (i in 1..totalQuestions) {
            val radioGroup = getRadioGroup(i)
            val selectedId = radioGroup?.checkedRadioButtonId

            if (selectedId != -1 && selectedId != null) {
                val radioButton = view?.findViewById<RadioButton>(selectedId)
                val userAnswer = radioButton?.text?.toString()?.substring(0, 1)

                if (userAnswer == correctAnswers[i-1]) {
                    score++
                }
            }
        }

        // Показываем результат
        val resultText = when {
            score >= 12 -> "Отличный результат! $score из $totalQuestions\nУ тебя хорошие задатки для программирования!"
            score >= 7 -> "Неплохо! $score из $totalQuestions\nЕсть понимание основ, но нужно подтянуть некоторые темы."
            else -> "$score из $totalQuestions\nПока сложно, но если интересно - начинай с основ!"
        }

        binding.resultTextView.text = resultText
        binding.resultTextView.visibility = View.VISIBLE
    }

    private fun getRadioGroup(questionNumber: Int): RadioGroup? {
        return when (questionNumber) {
            1 -> binding.radioGroup1
            2 -> binding.radioGroup2
            3 -> binding.radioGroup3
            4 -> binding.radioGroup4
            5 -> binding.radioGroup5
            6 -> binding.radioGroup6
            7 -> binding.radioGroup7
            8 -> binding.radioGroup8
            9 -> binding.radioGroup9
            10 -> binding.radioGroup10
            11 -> binding.radioGroup11
            12 -> binding.radioGroup12
            13 -> binding.radioGroup13
            14 -> binding.radioGroup14
            15 -> binding.radioGroup15
            else -> null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}