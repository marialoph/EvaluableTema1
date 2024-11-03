package com.example.evaluabletema1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.evaluabletema1.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var bindingMain: ActivityMainBinding
    private lateinit var btnLlamar : ImageButton
    private lateinit var btnAlarma : ImageButton
    private lateinit var btnSpotify : ImageButton
    private lateinit var btnMapa : ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindingMain = ActivityMainBinding.inflate(layoutInflater)

        setContentView(bindingMain.root)
        ViewCompat.setOnApplyWindowInsetsListener(bindingMain.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initEvent()
    }

    private fun initEvent() {
        btnLlamar = bindingMain.buttonLlamar
        btnLlamar.setOnClickListener{
                view->
            intent = Intent(this, LlamadaActivity::class.java).apply{
                putExtra("name", "maria")
            }
            startActivity(intent)
        }

        var url = "https://open.spotify.com/intl-es"
        btnSpotify = bindingMain.buttonSpotify
        btnSpotify.setOnClickListener{
                view->
            var link = Uri.parse(url)
            intent = Intent(Intent.ACTION_VIEW,link)
            startActivity(intent)

        }
        var url1 = "https://www.google.com/maps/preview"
        btnMapa = bindingMain.buttonMapa
        btnMapa.setOnClickListener{
                view->
            var link = Uri.parse(url1)
            intent = Intent(Intent.ACTION_VIEW,link)
            startActivity(intent)

        }
        btnAlarma = bindingMain.buttonAlarma
        btnAlarma.setOnClickListener{
            crearAlarma()
        }

    }

    private fun crearAlarma() {
        val alarma = Calendar.getInstance()
        alarma.add(Calendar.MINUTE, 2)

        intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, "Alarma en 2 minutos")
            putExtra(AlarmClock.EXTRA_HOUR, alarma.get(Calendar.HOUR_OF_DAY))
            putExtra(AlarmClock.EXTRA_MINUTES, alarma.get(Calendar.MINUTE))
        }

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "No se puede crear la alarma", Toast.LENGTH_SHORT).show()
        }
    }
}