package com.devnic.gmonitoring.ui.weeks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devnic.gmonitoring.R
import com.devnic.gmonitoring.database.model.ModelWeeks
import com.devnic.gmonitoring.databinding.ItemweeksBinding

class AdapterViewWeeks(private val model : (ModelWeeks) -> Unit) :
    ListAdapter<ModelWeeks, AdapterViewWeeks.Viewholderweek>(ModelComparatorW()) {
    class Viewholderweek(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemweeksBinding.bind(view)
        fun bind(modelWeeks: ModelWeeks, click : (ModelWeeks) -> Unit) {
            binding.itemFechaInit.text = modelWeeks.init.toString()
            binding.itemFechaFin.text = modelWeeks.finish.toString()
            binding.itemTotalDay.text = modelWeeks.total_day.toString()
            binding.itemCardWeek.setOnClickListener {
                click(modelWeeks)
            }
        }

        companion object {
            fun create(parent: ViewGroup): Viewholderweek {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.itemweeks, parent, false)
                return Viewholderweek(view)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholderweek {
        return Viewholderweek.create(parent)
    }

    override fun onBindViewHolder(holder: Viewholderweek, position: Int) {
        holder.bind(getItem(position),model)
    }
}

private class ModelComparatorW : DiffUtil.ItemCallback<ModelWeeks>() {
    override fun areItemsTheSame(oldItem: ModelWeeks, newItem: ModelWeeks): Boolean =
        oldItem.idweeks == newItem.idweeks


    override fun areContentsTheSame(oldItem: ModelWeeks, newItem: ModelWeeks): Boolean =
        oldItem == newItem

}