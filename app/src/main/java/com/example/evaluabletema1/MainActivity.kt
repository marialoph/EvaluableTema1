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
    // Se crea el binding para acceder a las vistas del activity_main de forma directa
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
        //Al pulsar este botón nos llevará al segundo activity, para poder realizar una llamada.
        btnLlamar = bindingMain.buttonLlamar
        btnLlamar.setOnClickListener{
                view->
            intent = Intent(this, LlamadaActivity::class.java).apply{
                putExtra("name", "maria")
            }
            startActivity(intent)
        }

        // Al pulsar el botón, se abrirá Spotify en el navegador.
        // Se crea un Intent con la acción de abrir una url, y se inicia la actividad para mostrar la página.
        var url = "https://open.spotify.com/intl-es"
        btnSpotify = bindingMain.buttonSpotify
        btnSpotify.setOnClickListener{
                view->
            var link = Uri.parse(url)
            intent = Intent(Intent.ACTION_VIEW,link)
            startActivity(intent)

        }

        // Al pulsar el botón, se abrirá el maps en el navegador.
        // Se crea un Intent con la acción de abrir una url, y se inicia la actividad para mostrar la página.
        var url1 = "https://www.google.com/maps/preview"
        btnMapa = bindingMain.buttonMapa
        btnMapa.setOnClickListener{
                view->
            var link = Uri.parse(url1)
            intent = Intent(Intent.ACTION_VIEW,link)
            startActivity(intent)

        }
        //Al pulsar al boton se abrirá la aplicacion de alarma y se creará gracias al método crearAlarma()
        btnAlarma = bindingMain.buttonAlarma
        btnAlarma.setOnClickListener{
            crearAlarma()
        }

    }

    //Este método hace que se configure una alarma que sonará en dos minutos
    private fun crearAlarma() {
        val alarma = Calendar.getInstance()
        alarma.add(Calendar.MINUTE, 2) //Sonará en dos minutos

        intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, "Alarma en 2 minutos") //Mensaje de la alarma
            putExtra(AlarmClock.EXTRA_HOUR, alarma.get(Calendar.HOUR_OF_DAY)) //Hora
            putExtra(AlarmClock.EXTRA_MINUTES, alarma.get(Calendar.MINUTE)) //Minutos
        }

        //Verifica si existe una aplicación que maneje el intent antes de lanzarlo
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)//Abre la aplicación de la alarma
        } else {
            Toast.makeText(this, "No se puede crear la alarma", Toast.LENGTH_SHORT).show()
        }
    }
}