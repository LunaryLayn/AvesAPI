package net.azarquiel.avesapiservice.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import net.azarquiel.avesapiservice.R
import net.azarquiel.avesapiservice.adapters.ZonaAdapter
import net.azarquiel.avesapiservice.databinding.ActivityMainBinding
import net.azarquiel.avesapiservice.entities.Usuario
import net.azarquiel.avesapiservice.entities.Zona
import net.azarquiel.avesapiservice.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var searchView: SearchView
    private lateinit var zonas: List<Zona>
    private var usuario: Usuario? = null

    private lateinit var sh: SharedPreferences

    private lateinit var viewmodel: MainViewModel
    private lateinit var adapter: ZonaAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        viewmodel = ViewModelProvider(this).get(MainViewModel::class.java)
        sh = getSharedPreferences("Config", Context.MODE_PRIVATE)

        getUserSH()
        initRV()
        getZonas()
    }



    private fun getZonas() {
        viewmodel.getAllZonas().observe(this, Observer { it ->
            it?.let{
                zonas = it
                adapter.setZonas(zonas)
            }
        })
    }

    private fun initRV() {
        adapter = ZonaAdapter(this, R.layout.rowzonas)
        binding.cm.rvzonas.layoutManager = LinearLayoutManager(this)
        binding.cm.rvzonas.adapter = adapter
    }

    //LOGINS
    private fun onClickLoginRegister() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Login/Register")
        val ll = LinearLayout(this)
        ll.setPadding(30, 30, 30, 30)
        ll.orientation = LinearLayout.VERTICAL

        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        lp.setMargins(0, 50, 0, 50)

        val textInputLayoutNick = TextInputLayout(this)
        textInputLayoutNick.layoutParams = lp
        val etnick = EditText(this)
        etnick.setPadding(0, 80, 0, 80)
        etnick.textSize = 20.0F
        etnick.hint = "Nick"
        textInputLayoutNick.addView(etnick)

        val textInputLayoutPass = TextInputLayout(this)
        textInputLayoutPass.layoutParams = lp
        val etpass = EditText(this)
        etpass.setPadding(0, 80, 0, 80)
        etpass.textSize = 20.0F
        etpass.hint = "Pass"
        textInputLayoutPass.addView(etpass)

        ll.addView(textInputLayoutNick)
        ll.addView(textInputLayoutPass)

        builder.setView(ll)

        builder.setPositiveButton("Aceptar") { dialog, which ->
            login(etnick.text.toString(), etpass.text.toString())
        }

        builder.setNegativeButton("Cancelar") { dialog, which ->
        }
        builder.show()
    }

    private fun login(nick: String, pass: String) {
        viewmodel.getUserByNickAndPass(nick, pass).observe(this, Observer { it ->
            if(it==null){
                viewmodel.saveUsuario(Usuario(0,nick, pass)).observe(this, Observer { it ->
                    it?.let{
                        usuario=it
                        msg("Registrado nuevo usuario con exito: ${usuario!!.nick}")
                        saveSH()
                    }
                })
            }
            else {
                usuario = it
                msg("Inicio de sesion correcto, bienvenido: ${usuario!!.nick}")
                saveSH()
            }
        })
    }

    private fun saveSH() {
        val editor = sh.edit()
        if(usuario!=null) {
            editor.putLong("id", usuario!!.id)
            editor.putString("nick", usuario!!.nick)
            editor.putString("pass", usuario!!.pass)
        }
        else {
            editor.remove("id")
            editor.remove("nick")
            editor.remove("pass")
        }
        editor.apply()
    }

    private fun getUserSH() {
        val nick = sh.getString("nick", null)
        if(nick!=null) {
            usuario = Usuario(sh.getLong("id", 0),nick, sh.getString("pass", null)!!)
            msg("Bienvenido de nuevo${usuario!!.nick}")
        }
    }

    private fun msg(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        //Parte 1 de la busqueda - copiar funciones abajo y hacer referencia a ellas en la extension
        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        searchView.setQueryHint("Search...")
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_login -> {
                onClickLoginRegister()
                true
            }
            R.id.action_logout -> {
                usuario=null
                saveSH()
                msg("Logout correcto")
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onQueryTextChange(query: String): Boolean {
        val original = ArrayList<Zona>(zonas)
        adapter.setZonas(original.filter { zona -> zona.nombre.contains(query,true) })
        return false
    }

    override fun onQueryTextSubmit(text: String): Boolean {
        return false
    }

    fun onClickZona(v: View) {
        val zona = v.tag as Zona
        val intent = Intent(this, ZonaDetail::class.java)
        intent.putExtra("zona", zona)
        intent.putExtra("usuario", usuario)
        startActivity(intent)
    }
}