package com.example.ynikit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class SingUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var usernameEt: EditText  // Поле для имени пользователя
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var registerBtn: Button
    private lateinit var goToLoginTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        auth = FirebaseAuth.getInstance()

        usernameEt = findViewById(R.id.username_et)  // Инициализация поля имени
        emailEt = findViewById(R.id.email_et)
        passwordEt = findViewById(R.id.password_et)
        registerBtn = findViewById(R.id.sign_up_btn)
        goToLoginTv = findViewById(R.id.backBtn)

        registerBtn.setOnClickListener {
            val username = usernameEt.text.toString().trim()
            val email = emailEt.text.toString().trim().lowercase()
            val password = passwordEt.text.toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Сохраняем имя пользователя в Firebase
                        val user = auth.currentUser
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(username)
                            .build()
                        user?.updateProfile(profileUpdates)

                        // Проверка на админа
                        if (email.contains("adminrole")) {
                            startActivity(Intent(this, Admin::class.java))
                        } else {
                            startActivity(Intent(this, Profile::class.java))
                        }
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Ошибка: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        goToLoginTv.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}