package com.example.ynikit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    // Объявление переменных для элементов UI
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var loginBtn: Button
    private lateinit var goToRegisterActivityTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Инициализация элементов UI
        emailEt = findViewById(R.id.email_et)
        passwordEt = findViewById(R.id.password_et)
        loginBtn = findViewById(R.id.login_btn)
        goToRegisterActivityTv = findViewById(R.id.go_to_register_activity_tv)

        // Устанавливаем обработчик на кнопку "Login"
        loginBtn.setOnClickListener {
            val email = emailEt.text.toString().trim().lowercase() // Приводим к нижнему регистру и убираем пробелы
            val password = passwordEt.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Логируем email для отладки
                        println("🔹 Email пользователя: $email")

                        // Проверяем наличие "adminrole" (в нижнем регистре)
                        if (email.contains("adminrole")) {
                            println("🔹 Обнаружен adminrole! Переход на Admin.kt")
                            startActivity(Intent(this@LoginActivity, Admin::class.java))
                        } else {
                            println("🔹 Обычный пользователь. Переход на Profile.kt")
                            startActivity(Intent(this@LoginActivity, Profile::class.java))
                        }
                        finish() // Закрываем LoginActivity
                    } else {
                        val error = task.exception?.message ?: "Ошибка авторизации"
                        Toast.makeText(this, "Ошибка: $error", Toast.LENGTH_SHORT).show()
                    }
                }
        }

            // Устанавливаем обработчик на текстовую ссылку для перехода в экран регистрации
            goToRegisterActivityTv.setOnClickListener {
                startActivity(Intent(this@LoginActivity, SingUpActivity::class.java))
            }

    }
}