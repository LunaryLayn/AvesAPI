package net.azarquiel.avesapiservice.view

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import net.azarquiel.avesapiservice.databinding.ActivityZonaDetailBinding
import net.azarquiel.avesapiservice.entities.Usuario
import net.azarquiel.avesapiservice.entities.Zona

class ZonaDetail : AppCompatActivity() {

    private lateinit var zonadetformaciones: TextView
    private lateinit var zonadetpresentacion: TextView
    private lateinit var zonadetlocalizacion: TextView
    private lateinit var zonadetnombre: TextView
    private var usuario: Usuario? = null
    private lateinit var zona: Zona

    private lateinit var binding: ActivityZonaDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityZonaDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        title = intent.getStringExtra("nombre")
        zona = intent.getSerializableExtra("zona") as Zona
        usuario = intent.getSerializableExtra("usuario") as Usuario?

        zonadetnombre = binding.cmzona.zonadetnombre
        zonadetlocalizacion = binding.cmzona.zonadetlocalizacion
        zonadetpresentacion = binding.cmzona.zonadetpresentacion
        zonadetformaciones = binding.cmzona.zonadetformaciones

        zonadetnombre.text = zona.nombre
        zonadetlocalizacion.text = zona.localizacion
        zonadetpresentacion.text = zona.presentacion
        zonadetformaciones.text = zona.formaciones_principales

        binding.fab.setOnClickListener { view ->
            val intent = Intent(this, RecursosActivity::class.java)
            intent.putExtra("zonaid", zona.id)
            intent.putExtra("usuario", usuario)
            startActivity(intent)
        }

    }
}