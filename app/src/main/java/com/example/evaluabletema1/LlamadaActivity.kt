package com.example.evaluabletema1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.evaluabletema1.databinding.ActivityLlamadaBinding
import com.example.evaluabletema1.databinding.ActivityMainBinding

class LlamadaActivity : AppCompatActivity() {
        // Se crea el bindingLlamada para acceder a las vistas del activity_llamada de forma directa
        private lateinit var bindingLlamada: ActivityLlamadaBinding
        private lateinit var btnVolver : ImageButton
        private lateinit var introduceNumero : EditText
        private lateinit var btnLlamar : ImageButton
        private var numeroTelefono : String = ""

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            bindingLlamada = ActivityLlamadaBinding.inflate(layoutInflater)

            setContentView(bindingLlamada.root)
            ViewCompat.setOnApplyWindowInsetsListener(bindingLlamada.root) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
            //Asigno las vistas a las variables btnLlamar, introduceNumero y btnVolver para manejarlas desde el código.
            btnLlamar = bindingLlamada.buttonLlama
            introduceNumero = bindingLlamada.editNumero
            btnVolver = bindingLlamada.buttonVolver
            //Creo el método
            initEvent()
        }

    private fun initEvent() {
        //Se introducirá un número por el editext 'introduceNumero' y se ejecutará el método requestPermission.
        //Sino se introduce un número saltará el mensaje Toast del else
        btnLlamar.setOnClickListener{
            numeroTelefono = introduceNumero.text.toString()
            if(numeroTelefono.isNotEmpty()){
                requestPermissions() //Solicita permisos para realizar la llamada
            }else{
                Toast.makeText(this, "Introduce un telefono", Toast.LENGTH_SHORT).show()
            }
        }

        //Al pulsar el boton de volver, irá al activity principal
        btnVolver = bindingLlamada.buttonVolver
        btnVolver.setOnClickListener{
                view->
            intent = Intent(this, MainActivity::class.java).apply{
                putExtra("name", "maria")
            }
            startActivity(intent)
        }
    }

    //Método para verificar y solicitar permisos
    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Verifica si el permiso se ha dado
            if (permissionPhone()) {
                call() //Llama si el permiso se ha concedido
            } else {
                //En caso de que el permiso no se ha concedido, lo solicitará
                requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
            }
        } else {
            call() // Llama directamente si la API es menor
        }
    }

    //Comprueba que el permiso se ha concedido
    private fun permissionPhone(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED

    }

    //Método que maneja la respuesta de la solicitud de permisos
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            call() //Método que llama
        } else {
            //Si el permiso ha sido denegado, se mostrará este mensaje
            Toast.makeText(
                this, "Se necesita habilitar los permisos para llamar",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    //Método para relizar la llamada utilizando el numero introducido en el editext
    private fun call() {
        val intent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:$numeroTelefono")
        }
        startActivity(intent) //Se inicia la llamada
    }

}