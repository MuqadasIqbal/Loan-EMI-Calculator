package app.itwingtech.com.saadapps.emicalculator.loan.finance.tool.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.itwingtech.com.saadapps.emicalculator.loan.finance.tool.Models.DataModel
import com.saadapps.emicalculator.loan.finance.tool.databinding.ItemLayoutBinding

/*class CalculatorAdapter(private var items: List<DataModel>) : RecyclerView.Adapter<CalculatorAdapter.CalculatorViewHolder>() {

    inner class CalculatorViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataModel) {
            binding.investImg.setImageResource(data.imageResId)
            binding.investText.text = data.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalculatorViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalculatorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalculatorViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newItems: List<DataModel>) {
        items = newItems
        notifyDataSetChanged()
    }
}*/

class CalculatorAdapter(
    private var items: List<DataModel>,
    private val onItemClick: (DataModel) -> Unit // Pass click listener as a lambda
) : RecyclerView.Adapter<CalculatorAdapter.CalculatorViewHolder>() {

    inner class CalculatorViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataModel) {
            binding.investImg.setImageResource(data.imageResId)
            binding.investText.text = data.title

            binding.root.setOnClickListener {
                onItemClick(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalculatorViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalculatorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalculatorViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newItems: List<DataModel>) {
        items = newItems
        notifyDataSetChanged()
    }
}