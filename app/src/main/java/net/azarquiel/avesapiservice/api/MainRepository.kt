package net.azarquiel.avesapiservice.api
import net.azarquiel.avesapiservice.entities.*

class MainRepository {
    val service = WebAccess.avesService

    suspend fun getAllZonas(): List<Zona> {
        val webResponse = service.getAllZonas().await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.zonas
        }
        return emptyList()
    }

    suspend fun getRecursosByZona(idzona: Long): List<Recurso> {
        val webResponse = service.getRecursosByZona(idzona).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.recursos
        }
        return emptyList()
    }

    suspend fun saveUsuario(usuario: Usuario): Usuario? {
        val webResponse = service.saveUsuario(usuario).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.usuario
        }
        return null
    }

    suspend fun getUserByNickAndPass(nick:String, pass:String): Usuario? {
        val webResponse = service.getUserByNickAndPass(nick, pass).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.usuario
        }
        return null
    }

    suspend fun getComentariosByRecurso(idrecurso: Long): List<ComentarioView> {
        val webResponse = service.getComentariosByRecurso(idrecurso).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.comentarios
        }
        return emptyList()
    }

    suspend fun saveComentarioByRecurso(idrecurso: Long, comentario: Comentario): Comentario? {
        var comentarioresponse: Comentario? = null
        val webResponse = service.saveComentarioByRecurso(idrecurso, comentario).await()
        if (webResponse.isSuccessful) {
            comentarioresponse = webResponse.body()!!.comentario
        }
        return comentarioresponse
    }

}