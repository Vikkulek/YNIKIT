package com.example.ynikit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var loginBtn: Button
    private lateinit var goToRegisterActivityTv: TextView
    private lateinit var captchaText: TextView
    private lateinit var captchaInput: EditText
    private lateinit var refreshCaptchaBtn: Button

    private var currentCaptcha: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Инициализация элементов
        emailEt = findViewById(R.id.email_et)
        passwordEt = findViewById(R.id.password_et)
        loginBtn = findViewById(R.id.login_btn)
        goToRegisterActivityTv = findViewById(R.id.go_to_register_activity_tv)
        captchaText = findViewById(R.id.captcha_text)
        captchaInput = findViewById(R.id.captcha_input)
        refreshCaptchaBtn = findViewById(R.id.refresh_captcha_btn)

        // Генерация первой капчи
        generateCaptcha()

        // Обновление капчи
        refreshCaptchaBtn.setOnClickListener {
            generateCaptcha()
        }

        // Обработчик входа
        loginBtn.setOnClickListener {
            val email = emailEt.text.toString().trim().lowercase()
            val password = passwordEt.text.toString()
            val userCaptcha = captchaInput.text.toString()

            // Проверка заполнения полей
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Проверка капчи (теперь всегда)
            if (userCaptcha != currentCaptcha) {
                Toast.makeText(this, "Неверная капча", Toast.LENGTH_SHORT).show()
                generateCaptcha() // Генерируем новую капчу
                return@setOnClickListener // Не продолжаем авторизацию
            }

            // Авторизация через Firebase (только если капча верна)
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Ваша оригинальная логика перехода
                        if (email.contains("adminrole")) {
                            startActivity(Intent(this@LoginActivity, Admin::class.java))
                        } else {
                            startActivity(Intent(this@LoginActivity, Profile::class.java))
                        }
                        finish()
                    } else {
                        val error = task.exception?.message ?: "Ошибка авторизации"
                        Toast.makeText(this, "Ошибка: $error", Toast.LENGTH_SHORT).show()
                        generateCaptcha() // Новая капча при ошибке входа
                    }
                }
        }

        // Переход к регистрации
        goToRegisterActivityTv.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SingUpActivity::class.java))
        }
    }

    private fun generateCaptcha() {
        val chars = "ABCDEFGHJKLMNPQRSTUVWXYZabcdfghjklmnpqrstuvwxyz23456789"
        currentCaptcha = (1..4)
            .map { chars.random() }
            .joinToString("")

        captchaText.text = currentCaptcha
        captchaInput.text.clear()
    }
}