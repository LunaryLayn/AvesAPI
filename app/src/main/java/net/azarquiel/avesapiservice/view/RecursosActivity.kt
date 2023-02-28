package net.azarquiel.avesapiservice.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import net.azarquiel.avesapiservice.R
import net.azarquiel.avesapiservice.adapters.RecursoAdapter
import net.azarquiel.avesapiservice.adapters.ZonaAdapter
import net.azarquiel.avesapiservice.databinding.ActivityRecursosBinding
import net.azarquiel.avesapiservice.entities.Recurso
import net.azarquiel.avesapiservice.entities.Usuario
import net.azarquiel.avesapiservice.viewmodel.MainViewModel

class RecursosActivity : AppCompatActivity() {

    private lateinit var recursos: List<Recurso>
    private var usuario: Usuario? = null
    private var zonaid: Long = 0
    private lateinit var viewmodel: MainViewModel
    private lateinit var adapter: RecursoAdapter
    private lateinit var binding: ActivityRecursosBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecursosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        viewmodel = ViewModelProvider(this).get(MainViewModel::class.java)

        zonaid = intent.getLongExtra("zonaid", 0)
        usuario = intent.getSerializableExtra("usuario") as Usuario?

        initRV()
        getRecursos()
    }

    private fun getRecursos() {
        viewmodel.getRecursosByZona(zonaid).observe(this, Observer { it ->
            it?.let{
                recursos = it
                adapter.setRecursos(recursos)
            }
        })
    }

    private fun initRV() {
        adapter = RecursoAdapter(this, R.layout.rowrecursos)
        binding.cmrecursos.rvrecursos.layoutManager = LinearLayoutManager(this)
        binding.cmrecursos.rvrecursos.adapter = adapter
    }

    fun onClickRecurso(v: View) {
        val recurso = v.tag as Recurso
        val intent = Intent(this, RecursoDetail::class.java)
        intent.putExtra("recurso", recurso)
        intent.putExtra("usuario", usuario)
        startActivity(intent)
    }

}