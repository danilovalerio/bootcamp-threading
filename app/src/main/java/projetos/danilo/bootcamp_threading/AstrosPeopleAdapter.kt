package projetos.danilo.bootcamp_threading

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class AstrosPeopleAdapter : RecyclerView.Adapter<AstrosPeopleAdapter.AstrosPeopelViewHolder>() {
    val list: MutableList<AstrosPeople> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AstrosPeopelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.astro_people_item, parent, false)

        return AstrosPeopelViewHolder(view)
    }

    override fun onBindViewHolder(holder: AstrosPeopelViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun update(list: List<AstrosPeople>) {
        notifyItemRangeRemoved(0, this.list.size)
        this.list.clear()
        this.list.addAll(list)
        notifyItemRangeInserted(0, this.list.size)
    }

    inner class AstrosPeopelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var itemSelected: AstrosPeople? = null

        //recebe o nome e a nave do astronauta
        val nameCraft = itemView.findViewById<TextView>(R.id.tv_name_astropeople)
        var icAstroPeople: ImageView = itemView.findViewById<ImageView>(R.id.iv_icon_craft)

        init {
            itemView.setOnClickListener {
               itemSelected = itemSelected
                Log.i("CLICK_ITEM", "Name ${itemSelected?.name}")
            }
        }

        fun bind(astrosPeople: AstrosPeople) {
            val nameHifenCraft: String = "${astrosPeople.name} - ${astrosPeople.craft}"
            this.itemSelected = astrosPeople
            nameCraft.text = nameHifenCraft
            icAstroPeople.setImageResource(R.drawable.ic_flight)
        }

    }
}