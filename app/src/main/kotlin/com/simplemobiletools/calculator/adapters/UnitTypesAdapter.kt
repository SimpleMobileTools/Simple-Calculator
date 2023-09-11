package com.simplemobiletools.calculator.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.simplemobiletools.calculator.activities.SimpleActivity
import com.simplemobiletools.calculator.databinding.ItemUnitTypeBinding
import com.simplemobiletools.calculator.helpers.converters.Converter
import com.simplemobiletools.commons.extensions.applyColorFilter
import com.simplemobiletools.commons.extensions.getProperPrimaryColor
import com.simplemobiletools.commons.extensions.getProperTextColor

class UnitTypesAdapter (val activity: SimpleActivity, val items: List<Converter>, val itemClick: (id: Int) -> Unit) : RecyclerView.Adapter<UnitTypesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemUnitTypeBinding.inflate(activity.layoutInflater, parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bindView(item, position)
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(private val binding: ItemUnitTypeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: Converter, id: Int): View {
            itemView.apply {
                binding.unitImage.setImageResource(item.imageResId)
                binding.unitLabel.setText(item.nameResId)

                binding.unitLabel.setTextColor(activity.getProperTextColor())
                binding.unitImage.applyColorFilter(activity.getProperPrimaryColor())

                setOnClickListener {
                    itemClick(id)
                }
            }

            return itemView
        }
    }
}
