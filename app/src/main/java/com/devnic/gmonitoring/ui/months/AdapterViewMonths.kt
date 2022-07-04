package com.devnic.gmonitoring.ui.months

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devnic.gmonitoring.R
import com.devnic.gmonitoring.database.model.ModelMonths
import com.devnic.gmonitoring.databinding.ItemmonthsBinding
import com.devnic.gmonitoring.ui.months.AdapterViewMonths.*

class AdapterViewMonths(private val detail : ( ModelMonths) -> Unit) :
    ListAdapter<ModelMonths, Viewholdermonths>(ModelComparator()) {

    class Viewholdermonths(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemmonthsBinding.bind(view)

        fun bind(model: ModelMonths ,onClick : (ModelMonths) -> Unit) {
            binding.itemFechaInit.text = model.day.toString()
            binding.itemMes.text = getmonth(model.months)
            binding.itemDescription.text = model.description

            binding.itemCard.setOnClickListener {
                onClick(model)
            }
        }
        fun getmonth(month: Int): String {
            val mont = arrayListOf(
                "Enero",
                "Febrero",
                "Marzo",
                "Abril",
                "Mayo",
                "Junio",
                "Julio",
                "Agosto",
                "Septiembre",
                "Octubre",
                "Noviembre",
                "Diciembre"
            )
            return mont[month]
        }

        companion object {
            fun create(parent: ViewGroup): Viewholdermonths {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.itemmonths, parent, false)
                return Viewholdermonths(view)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholdermonths {
        return Viewholdermonths.create(parent)
    }

    override fun onBindViewHolder(holder: Viewholdermonths, position: Int) {
        holder.bind(getItem(position),detail)
    }


}

private class ModelComparator : DiffUtil.ItemCallback<ModelMonths>() {
    override fun areItemsTheSame(oldItem: ModelMonths, newItem: ModelMonths): Boolean =
        oldItem.idmonths == newItem.idmonths


    override fun areContentsTheSame(oldItem: ModelMonths, newItem: ModelMonths): Boolean =
        oldItem == newItem

}
