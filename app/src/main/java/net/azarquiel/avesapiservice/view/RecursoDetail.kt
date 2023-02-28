package net.azarquiel.avesapiservice.view

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso
import net.azarquiel.avesapiservice.R
import net.azarquiel.avesapiservice.adapters.ComentarioAdapter
import net.azarquiel.avesapiservice.databinding.ActivityRecursoDetailBinding
import net.azarquiel.avesapiservice.entities.Comentario
import net.azarquiel.avesapiservice.entities.Recurso
import net.azarquiel.avesapiservice.entities.Usuario
import net.azarquiel.avesapiservice.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

class RecursoDetail : AppCompatActivity() {

    private var usuario: Usuario? = null
    private lateinit var image: ImageView
    private lateinit var comentarios: List<Comentario>
    private lateinit var recurso: Recurso
    private lateinit var viewmodel: MainViewModel
    private lateinit var adapter: ComentarioAdapter
    private lateinit var binding: ActivityRecursoDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecursoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)


        viewmodel = ViewModelProvider(this).get(MainViewModel::class.java)
        image = binding.cmrecursodetail.ivrecurso

        usuario = intent.getSerializableExtra("usuario") as Usuario?
        recurso = intent.getSerializableExtra("recurso") as Recurso
        Picasso.get().load("${recurso.url}").into(image)

        initRV()
        getComentarios()

        binding.fab.setOnClickListener { view ->
            newComentario()
        }
    }

    private fun newComentario() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Nuevo comentario")
        val ll = LinearLayout(this)
        ll.setPadding(30, 30, 30, 30)
        ll.orientation = LinearLayout.VERTICAL

        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        lp.setMargins(0, 50, 0, 50)

        val textInputLayoutPass = TextInputLayout(this)
        textInputLayoutPass.layoutParams = lp
        val etpass = EditText(this)
        etpass.setPadding(0, 80, 0, 80)
        etpass.textSize = 20.0F
        etpass.hint = "Comentario"
        textInputLayoutPass.addView(etpass)

        ll.addView(textInputLayoutPass)

        builder.setView(ll)

        builder.setPositiveButton("Aceptar") { dialog, which ->
            saveComentario(etpass.text.toString())
        }

        builder.setNegativeButton("Cancelar") { dialog, which ->
        }
        builder.show()
    }

    private fun saveComentario(s: String) {
        val fecha = Date()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val fechaTXT = sdf.format(fecha)

        val com = Comentario(12, recurso.id, usuario!!.nick, fechaTXT, s)
        viewmodel.saveComentarioByRecurso(recurso.id, com).observe(this, Observer { it ->
            it?.let{
                msg("Anotado comentario...")
                getComentarios()
            }
        })
    }

    private fun msg(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }

    private fun getComentarios() {
        viewmodel.getComentariosByRecurso(recurso.id).observe(this, Observer { it ->
            it?.let{
                comentarios = it
                adapter.setComentarios(comentarios)
            }
        })
    }

    private fun initRV() {
        adapter = ComentarioAdapter(this, R.layout.rowcomentario)
        binding.cmrecursodetail.rvrecurso.layoutManager = LinearLayoutManager(this)
        binding.cmrecursodetail.rvrecurso.adapter = adapter
    }

}