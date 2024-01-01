package com.example.countdown.data

class Counter(
    private val days: Long = 0,
    private val hours: Long = 0,
    val minutes: Long = 0,
    private val seconds: Long = 0
) {

    fun getTimeInMs(): Long {
        return days * 24 * 3600 * 1000 + hours * 3600 * 1000 + minutes * 60 * 1000 + seconds * 1000
    }

    companion object {
        private fun formatTimeUnit(unit: Int): String {
            return if (unit < 10) "0$unit" else unit.toString()
        }

        fun formatTimeRemain(milliseconds: Long): TimeRemain {
            val seconds = (milliseconds / 1000).toInt()
            val minutes = seconds / 60
            val hours = minutes / 60
            val days = hours / 24

            val remainingHours = formatTimeUnit(hours % 24)
            val remainingMinutes = formatTimeUnit(minutes % 60)
            val remainingSeconds = formatTimeUnit(seconds % 60)

            return TimeRemain(
                formatTimeUnit(days),
                remainingHours,
                remainingMinutes,
                remainingSeconds
            )
        }
    }

}
