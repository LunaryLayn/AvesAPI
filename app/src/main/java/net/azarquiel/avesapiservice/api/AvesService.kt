package net.azarquiel.avesapiservice.api

import kotlinx.coroutines.Deferred
import net.azarquiel.avesapiservice.entities.Comentario
import net.azarquiel.avesapiservice.entities.Respuesta
import net.azarquiel.avesapiservice.entities.Usuario
import net.azarquiel.avesapiservice.entities.Zona
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AvesService {
    @GET("zonas")
    fun getAllZonas(): Deferred<Response<Respuesta>>

    @GET("zona/{idzona}/recursos")
    fun getRecursosByZona(@Path("idzona") idzona: Long): Deferred<Response<Respuesta>>

    @GET("usuario")
    fun getUserByNickAndPass(
        @Query("nick") nick: String,
        @Query("pass") pass: String
    ): Deferred<Response<Respuesta>>

    @POST("usuario")
    fun saveUsuario(
        @Body usuario: Usuario
    ): Deferred<Response<Respuesta>>

    @GET ("recurso/{idrecurso}/comentarios")
    fun getComentariosByRecurso(@Path("idrecurso") idrecurso: Long): Deferred<Response<Respuesta>>

    @POST("recurso/{idrecurso}/comentario")
    fun saveComentarioByRecurso(@Path("idrecurso") idrecurso: Long,
                                @Body comentario: Comentario): Deferred<Response<Respuesta>>

}