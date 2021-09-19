package org.igye.android.gamepad

open class NextElemSelector<T>(
    private val allElems: List<T>,
    private val nextElemIdx: (IntArray) -> Int
) {
    init {
        if (allElems.isEmpty()) {
            throw AndroidGamepadException("allElems.isEmpty()")
        }
    }
    private val counts: IntArray = allElems.asSequence().map { 0 }.toMutableList().toIntArray()

    fun nextElem(): T {
        val selectedIdx = nextElemIdx(counts)
        val selectedElem = allElems[selectedIdx]
        counts[selectedIdx] = counts[selectedIdx] + 1
        return selectedElem
    }

    fun getCounts(): Array<Int> = counts.toTypedArray()
}

class RandomElemSelector<T>(allElems: List<T>): NextElemSelector<T>(allElems=allElems, nextElemIdx = Utils::nextRandomIdxByCounts)
class SequentialElemSelector<T>(allElems: List<T>): NextElemSelector<T>(allElems=allElems, nextElemIdx = Utils::nextSequentialIdxByCounts)