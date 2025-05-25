package com.example.ynikit

import android.content.ActivityNotFoundException
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

        val WantBeAdmin: Button = findViewById(R.id.WantBeAdmin)
        val checkButton: Button = findViewById(R.id.checkButton)
        val addInfoButton: Button = findViewById(R.id.addInfoButton)
        val checkButtonTable: Button = findViewById(R.id.checkButtonTable)

        val formUrl = "https://docs.google.com/forms/d/e/1FAIpQLSfMhIJXW09TJSO9fqp3SByOClLYpx4dYScFrXlRFWMUIG3xIQ/viewform?usp=header"
        val InfoForRedactor = "https://drive.google.com/drive/folders/1Dt04Ab86X9wu9d7JtwdQaHhqrehUCiVI"
        val checkButtonTableURL = "https://docs.google.com/spreadsheets/d/1fl0d1KfQnqnrOjg69ayagjyJNVYrsBrNUm6CIJGl2_A/edit?resourcekey=&gid=1134262254#gid=1134262254"

        checkButton.setOnClickListener {
            openWebPage(formUrl)
        }

        addInfoButton.setOnClickListener {
            openWebPage(InfoForRedactor)
        }

        WantBeAdmin.setOnClickListener {
            sendAdminRequestEmail()
        }

        checkButtonTable.setOnClickListener {
            openWebPage(checkButtonTableURL)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun sendAdminRequestEmail() {
        val email = "vika.verminskayav@gmail.com"
        val subject = "Обращение из приложения"
        val body = "Здравствуйте! Я пишу вам из приложения YNIKIT. Прошу предоставить мне права администратора"

        try {
            // Способ 1: Используем ACTION_SENDTO с mailto (предпочтительный)
            val mailtoIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, body)
            }

            // Способ 2: Альтернативный вариант с ACTION_SEND
            val fallbackIntent = Intent(Intent.ACTION_SEND).apply {
                type = "message/rfc822"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, body)
            }

            // Пытаемся использовать mailto сначала
            try {
                startActivity(mailtoIntent)
            } catch (e: ActivityNotFoundException) {
                // Если не сработало, пробуем общий вариант
                startActivity(Intent.createChooser(fallbackIntent, "Выберите почтовое приложение"))
            }

        } catch (e: Exception) {
            Toast.makeText(this, "Не удалось найти почтовое приложение", Toast.LENGTH_LONG).show()

            // Предлагаем установить Gmail через браузер
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.gm"))
                startActivity(browserIntent)
            } catch (e: Exception) {
                Toast.makeText(this, "Установите почтовое приложение (например, Gmail)", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun openWebPage(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                // Добавляем флаг для нового таска
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Не удалось открыть ссылку. Установите браузер.",
                Toast.LENGTH_LONG
            ).show()

            // Открываем Play Market для установки браузера
            try {
                val marketIntent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=com.android.chrome"))
                startActivity(marketIntent)
            } catch (e: Exception) {
                Toast.makeText(this, "Установите браузер (например, Chrome)", Toast.LENGTH_LONG).show()
            }
        }
    }
}