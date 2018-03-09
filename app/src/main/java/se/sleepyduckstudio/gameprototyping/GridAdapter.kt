package se.sleepyduckstudio.gameprototyping

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class GridAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private val VIEW_TYPE = 1
        private val VIEW_TYPE_SPACE = 2
    }

    private var grid = Grid(0, 0) { _, _ -> AdapterItem(0, 0, "") }

    init {
        setHasStableIds(true)
        shuffle()
    }

    fun shuffle() {
        val propertySet = getCompleteRandomPropertySet(3)
        val width = 3
        val height = propertySet.size

        val oldGrid = grid.data

        grid = Grid(width + 2, height) { row, col ->
            when (col) {

                in 1..width -> {
                    val property = propertySet[row][col - 1]
                    AdapterItem(VIEW_TYPE,
                            property.hashCode().toLong(),
                            property)
                }

                else -> AdapterItem(VIEW_TYPE_SPACE, (row * col).toLong())
            }
        }

        DiffUtil.calculateDiff(AdpterItemDiffUtil(oldGrid, grid.data))
                .dispatchUpdatesTo(this)
    }

    override fun getItemCount() = grid.size

    override fun getItemViewType(position: Int) = grid.itemAtIndex(position).viewType

    override fun getItemId(position: Int) = grid.itemAtIndex(position).id

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TextViewHolder -> bindHolder(holder, position)
        }
    }

    private fun bindHolder(holder: TextViewHolder, position: Int) {
        holder.textView?.text = grid.itemAtIndex(position).text

        holder.itemView.setOnClickListener {
            val oldGrid = grid.data

            grid.shiftCenter(holder.adapterPosition)

            DiffUtil.calculateDiff(AdpterItemDiffUtil(oldGrid, grid.data))
                    .dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            when (viewType) {

                VIEW_TYPE_SPACE -> SpaceViewHolder(
                        LayoutInflater
                                .from(parent.context)
                                .inflate(R.layout.list_item_space, parent, false))

                else -> TextViewHolder(
                        LayoutInflater
                                .from(parent.context)
                                .inflate(R.layout.list_item, parent, false))

            }
}

class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView = itemView as? TextView
}

class SpaceViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

data class AdapterItem(val viewType: Int, val id: Long, val text: String = "")

class AdpterItemDiffUtil(
        private val oldList: List<AdapterItem>,
        private val newList: List<AdapterItem>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int)
            = oldList[oldItemPosition] == newList[newItemPosition]

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int)
            = areItemsTheSame(oldItemPosition, newItemPosition)

}