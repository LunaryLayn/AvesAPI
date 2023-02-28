package net.azarquiel.avesapiservice.entities

import java.io.Serializable

data class Zona(
   var id: Long,
   var nombre: String,
   var localizacion: String,
   var formaciones_principales: String,
   var presentacion: String,
   var geom_lat: Double,
   var geom_lon: Double,): Serializable

data class Recurso(
    var id: Long,
    var zona: Long,
    var titulo: String,
    var url: String,
): Serializable

data class Usuario(
    var id: Long,
    var nick: String,
    var pass: String
): Serializable

data class Comentario(
    var id: Long,
    var recurso: Long,
    var nick: String,
    var fecha: String,
    var comentario: String,
)

data class Respuesta(
    val zona: Zona,
    val zonas: List<Zona>,
    val recurso: Recurso,
    val recursos: List<Recurso>,
    val usuario: Usuario,
    val usuarios: List<Usuario>,
    val comentario: Comentario,
    val comentarios: List<Comentario>
)

