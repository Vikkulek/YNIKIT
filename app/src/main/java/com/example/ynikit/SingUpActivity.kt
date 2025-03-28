package com.example.ynikit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SingUpActivity : AppCompatActivity() {
    private lateinit var signUpBtn: Button
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var usernameEt: EditText
    private lateinit var icon: ImageView
    private lateinit var backBtn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        // Инициализация элементов интерфейса
        backBtn = findViewById(R.id.backBtn)
        signUpBtn = findViewById(R.id.sign_up_btn)
        emailEt = findViewById(R.id.email_et)
        passwordEt = findViewById(R.id.password_et)
        usernameEt = findViewById(R.id.username_et)

        // Обработчик кнопки "Назад"
        backBtn.setOnClickListener {
            finish()
        }

        // Обработчик кнопки "Sign up"
        signUpBtn.setOnClickListener {
            val email = emailEt.text.toString().trim()
            val password = passwordEt.text.toString().trim()
            val username = usernameEt.text.toString().trim()

            // Проверка на пустые поля
            if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
                Toast.makeText(applicationContext, "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Создание пользователя
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Регистрация успешна!", Toast.LENGTH_SHORT).show()
                        // Переход на профиль
                        startActivity(Intent(this@SingUpActivity, Profile::class.java))
                        finish()

                        val user = FirebaseAuth.getInstance().currentUser
                        val uid = user?.uid

                        if (uid == null) {
                            Toast.makeText(this, "Ошибка: UID пользователя равен null", Toast.LENGTH_SHORT).show()
                            return@addOnCompleteListener
                        }

                        val userInfo = hashMapOf(
                            "email" to email,
                            "username" to username,
                            "profileImage" to "",
                            "chats" to ""
                        )

                        FirebaseDatabase.getInstance().getReference("Users")
                            .child(uid)
                            .setValue(userInfo)
                            .addOnCompleteListener { dbTask ->
                                if (dbTask.isSuccessful) {
                                    Toast.makeText(this, "Данные успешно добавлены в базу данных", Toast.LENGTH_SHORT).show()
                                    // Переход на профиль
                                    startActivity(Intent(this@SingUpActivity, Profile::class.java))
                                    finish()
                                }
                                else {
                                    Toast.makeText(this, "Ошибка записи в базу: ${dbTask.exception?.message}", Toast.LENGTH_LONG).show()
                                }
                            }
                            .addOnFailureListener { dbError ->
                                Toast.makeText(this, "Ошибка базы данных: ${dbError.message}", Toast.LENGTH_LONG).show()
                            }

                    } else {
                        Toast.makeText(this, "Ошибка регистрации: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener { authError ->
                    Toast.makeText(this, "Ошибка аутентификации: ${authError.message}", Toast.LENGTH_LONG).show()
                }
        }
    }
}
