package se.sleepyduckstudio.gameprototyping


data class Grid<out T>(
        private val width: Int,
        private val height: Int,
        private val initial: (row: Int, column: Int) -> T) {

    val size = width * height

    val data: List<T> get() = grid.toList()

    private val grid = MutableList(size) { index ->
        initial(indexAsRow(index), indexAsColumn(index))
    }

    private val centerColumn = width / 2

    private fun indexAsRow(index: Int) = index / width
    private fun indexAsColumn(index: Int) = index % width

    fun itemAtIndex(index: Int) = grid[index]

    operator fun get(row: Int) = grid.filterIndexed { index, _ -> indexAsRow(index) == row }

    fun shiftCenter(index: Int) {
        val column = indexAsColumn(index)
        val row = indexAsRow(index)

        when {
            column < centerColumn -> shiftRow(row, ShiftDirection.Right)
            column > centerColumn -> shiftRow(row, ShiftDirection.Left)
            else -> return
        }
    }

    private fun shiftRow(row: Int, direction: ShiftDirection) {
        val start = row * width
        val end = (row + 1) * width - 1

        when (direction) {

            ShiftDirection.Left -> {
                val leftmost = itemAtIndex(start)
                for (i in start + 1..end) {
                    grid[i - 1] = itemAtIndex(i)
                }
                grid[end] = leftmost
            }

            ShiftDirection.Right -> {
                val rightmost = itemAtIndex(end)
                for (i in end - 1 downTo start) {
                    grid[i + 1] = itemAtIndex(i)
                }
                grid[start] = rightmost
            }
        }
    }
}

enum class ShiftDirection {
    Left, Right
}