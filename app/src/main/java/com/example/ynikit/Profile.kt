package com.example.ynikit

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.ynikit.databinding.ActivityProfileBinding

class Profile : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarProfile.toolbar)

        // Обработчик для кнопки email (fab)
        binding.appBarProfile.fab.setOnClickListener {
            val email = "priem.ukit@mgutm.ru"
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$email")
                putExtra(Intent.EXTRA_SUBJECT, "Обращение из приложения")
                putExtra(Intent.EXTRA_TEXT, "Здравствуйте! Я пишу вам из приложения YNIKIT.")
            }

            try {
                startActivity(emailIntent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Почтовое приложение не найдено", Toast.LENGTH_SHORT).show()
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://mail.google.com/mail/?view=cm&to=$email"))
                startActivity(webIntent)
            }
        }

        // Обработчик для кнопки телефона (fab1)
        binding.appBarProfile.fab1.setOnClickListener {
            val phoneNumber = "89851979758"
            val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }

            try {
                startActivity(dialIntent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Приложение для звонков не найдено", Toast.LENGTH_SHORT).show()
            }
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_profile)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Добавленный код для работы с nav_header_main.xml
        setupNavigationHeader()
    }

    private fun setupNavigationHeader() {
        // Получаем доступ к header NavigationView
        val headerView = binding.navView.getHeaderView(0)

        // Находим элементы из nav_header_main.xml
        val tgIcon = headerView.findViewById<ImageView>(R.id.imageViewTG)
        val vkIcon = headerView.findViewById<ImageView>(R.id.imageViewVK)
        val collegeLogo = headerView.findViewById<ImageView>(R.id.imageView)
        val textView = headerView.findViewById<TextView>(R.id.textView)

        // Установка текста (пример)
        textView.text = "Добро пожаловать!"

        // Обработка кликов по иконкам
        tgIcon.setOnClickListener {
            openUrl("https://t.me/s/ukit_college")
        }

        vkIcon.setOnClickListener {
            openUrl("https://vk.com/unikitpage")
        }

        collegeLogo.setOnClickListener {
            Toast.makeText(this, "Университетский колледж информационных технологий", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Не удалось открыть ссылку", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.profile, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_profile)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}