package net.azarquiel.avesapiservice.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.avesapiservice.api.MainRepository
import net.azarquiel.avesapiservice.entities.*

class MainViewModel : ViewModel() {


    private var repository: MainRepository = MainRepository()

    fun getAllZonas(): MutableLiveData<List<Zona>> {
        val zonas = MutableLiveData<List<Zona>>()
        GlobalScope.launch(Main) {
            zonas.value = repository.getAllZonas()
        }
        return zonas
    }

    fun getRecursosByZona(idzona: Long): MutableLiveData<List<Recurso>> {
        val recursos = MutableLiveData<List<Recurso>>()
        GlobalScope.launch(Main) {
            recursos.value = repository.getRecursosByZona(idzona)
        }
        return recursos
    }

    fun getUserByNickAndPass(nick:String, pass:String):MutableLiveData<Usuario> {
        val usuarioresponse= MutableLiveData<Usuario>()
        GlobalScope.launch(Main) {
            usuarioresponse.value = repository.getUserByNickAndPass(nick, pass)
        }
        return usuarioresponse
    }

    fun saveUsuario(usuario: Usuario):MutableLiveData<Usuario> {
        val usuarioresponse= MutableLiveData<Usuario>()
        GlobalScope.launch(Main) {
            usuarioresponse.value = repository.saveUsuario(usuario)
        }
        return usuarioresponse
    }

    fun getComentariosByRecurso(idrecurso: Long): MutableLiveData<List<ComentarioView>> {
        val comentarios = MutableLiveData<List<ComentarioView>>()
        GlobalScope.launch(Main) {
            comentarios.value = repository.getComentariosByRecurso(idrecurso)
        }
        return comentarios
    }

    fun saveComentarioByRecurso(idrecurso: Long, comentario: Comentario): MutableLiveData<Comentario> {
        val comentarioresponse = MutableLiveData<Comentario>()
        GlobalScope.launch(Main) {
            comentarioresponse.value = repository.saveComentarioByRecurso(idrecurso, comentario)
        }
        return comentarioresponse
    }

}
