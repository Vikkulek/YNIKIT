package com.example.ynikit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Admin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin)

        // Находим кнопку в макете
        val WantBeAdmin: Button = findViewById(R.id.WantBeAdmin)
        val checkButton: Button = findViewById(R.id.checkButton) // Убедитесь, что у вас есть кнопка с таким id в activity_admin.xml
        val addInfoButton: Button = findViewById(R.id.addInfoButton) // Если эта кнопка у вас есть

        // Ссылка для перехода
        val formUrl = "https://docs.google.com/forms/d/e/1FAIpQLSfMhIJXW09TJSO9fqp3SByOClLYpx4dYScFrXlRFWMUIG3xIQ/viewform?usp=header"
        val InfoForRedactor = "https://drive.google.com/drive/folders/1Dt04Ab86X9wu9d7JtwdQaHhqrehUCiVI"
        // Обработчик нажатия на кнопку
        checkButton.setOnClickListener {
            openWebPage(formUrl)
        }
        addInfoButton.setOnClickListener{
            openInfo(InfoForRedactor)
        }
        WantBeAdmin.setOnClickListener {  val email = "vika.verminskayav@gmail.com" // Проверьте, нет ли опечаток!
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$email")
                putExtra(Intent.EXTRA_SUBJECT, "Обращение из приложения")
                putExtra(Intent.EXTRA_TEXT, "Здравствуйте! Я пишу вам из приложения YNIKIT. Прошу предоставить мне права администратора")
            }

            // Проверяем, есть ли почтовое приложение
            if (emailIntent.resolveActivity(packageManager) != null) {
                startActivity(emailIntent)
            } else {
                // Если нет, предлагаем установить Gmail
                Toast.makeText(this, "Почтовое приложение не найдено", Toast.LENGTH_SHORT).show()
                val playStoreIntent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("market://details?id=com.google.android.gm")
                }
                if (playStoreIntent.resolveActivity(packageManager) != null) {
                    startActivity(playStoreIntent)
                }
            } }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }



    private fun openWebPage(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Не удалось открыть ссылку. Установите браузер.",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    private fun openInfo(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Не удалось открыть ссылку. Установите браузер.",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}