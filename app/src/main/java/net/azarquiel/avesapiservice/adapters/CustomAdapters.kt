package net.azarquiel.avesapiservice.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.azarquiel.avesapiservice.R
import net.azarquiel.avesapiservice.entities.Comentario
import net.azarquiel.avesapiservice.entities.Recurso
import net.azarquiel.avesapiservice.entities.Usuario
import net.azarquiel.avesapiservice.entities.Zona

class ZonaAdapter(val context: Context,
                  val layout: Int
) : RecyclerView.Adapter<ZonaAdapter.ViewHolder>() {

    private var dataList: List<Zona> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setZonas(zonas: List<Zona>) {
        this.dataList = zonas
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Zona){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            val zonatvnombre = itemView.findViewById(R.id.recursodetfecha) as TextView
            val zonatvlocalizacion = itemView.findViewById(R.id.zonatvlocalizacion) as TextView

            zonatvnombre.text = dataItem.nombre
            zonatvlocalizacion.text = dataItem.localizacion

            // foto de internet a traves de Picasso
          //  Picasso.get().load("${dataItem.thumbnail.path}/standard_fantastic.${dataItem.thumbnail.extension}").into(ivrowZona)


            itemView.tag = dataItem

        }

    }
}


class RecursoAdapter(val context: Context,
                  val layout: Int
) : RecyclerView.Adapter<RecursoAdapter.ViewHolder>() {

    private var dataList: List<Recurso> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setRecursos(recursos: List<Recurso>) {
        this.dataList = recursos
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Recurso){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            val recursoiv = itemView.findViewById(R.id.recursoiv) as ImageView
            val recursotvnombre = itemView.findViewById(R.id.recursotvnombre) as TextView

            recursotvnombre.text = dataItem.titulo
            Picasso.get().load("${dataItem.url}").into(recursoiv)

            // foto de internet a traves de Picasso
            //  Picasso.get().load("${dataItem.thumbnail.path}/standard_fantastic.${dataItem.thumbnail.extension}").into(ivrowZona)


            itemView.tag = dataItem

        }

    }
}

class ComentarioAdapter(val context: Context,
                     val layout: Int
) : RecyclerView.Adapter<ComentarioAdapter.ViewHolder>() {

    private var dataList: List<Comentario> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setComentarios(comentarios: List<Comentario>) {
        this.dataList = comentarios
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Comentario){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            val recursodetfecha = itemView.findViewById(R.id.recursodetfecha) as TextView
            val recursodetcomentario = itemView.findViewById(R.id.recursodetcomentario) as TextView
            val recursodetusuario = itemView.findViewById(R.id.recursodetusuario) as TextView

            recursodetfecha.text = dataItem.fecha
            recursodetcomentario.text = dataItem.comentario
            recursodetusuario.text = dataItem.nick

            // foto de internet a traves de Picasso
            //  Picasso.get().load("${dataItem.thumbnail.path}/standard_fantastic.${dataItem.thumbnail.extension}").into(ivrowZona)


            itemView.tag = dataItem

        }

    }
}