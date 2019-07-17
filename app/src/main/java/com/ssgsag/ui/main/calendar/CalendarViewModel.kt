package com.ssgsag.ui.main.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssgsag.base.BaseViewModel
import com.ssgsag.data.model.category.Category

class CalendarViewModel : BaseViewModel() {
    private val defaultSet = arrayListOf(
        Category(-2, true),
        Category(-1, false),
        Category(0, true),
        Category(1, true),
        Category(2, true),
        Category(3, true),
        Category(4, true),
        Category(5, true)
    )
    private val _categorySort =
        MutableLiveData<ArrayList<Category>>().apply {
            postValue(defaultSet)
        }
    val categorySort: LiveData<ArrayList<Category>> get() = _categorySort

    //코드 수정 필요 ㅎㅎ;;;
    fun checkCate(categoryIdx: Int) {
        when (categoryIdx) {
            -2 -> {
                if (!defaultSet[0].isChecked) {
                    _categorySort.postValue(defaultSet.apply {
                        for (i in defaultSet.indices) {
                            if (this[i].categoryIdx != -1) this[i].isChecked = true else this[i].isChecked = false
                        }
                    })
                } else {
                    _categorySort.postValue(defaultSet.apply {
                        for (i in defaultSet.indices) {
                            this[i].isChecked = false
                        }
                    })
                }
            }
            -1 -> {
                if (!defaultSet[1].isChecked) {
                    _categorySort.postValue(defaultSet.apply {
                        for (i in defaultSet.indices) {
                            if (this[i].categoryIdx != -1) this[i].isChecked = false else this[i].isChecked = true
                        }
                    })
                } else {
                    _categorySort.postValue(
                        defaultSet.apply {
                            for (i in defaultSet.indices) {
                                this[i].isChecked = false
                            }
                        })
                }
            }
            else -> {
                defaultSet[categoryIdx + 2].isChecked = !defaultSet[categoryIdx + 2].isChecked
                defaultSet[1].isChecked = false

                for (i in 2 until defaultSet.size) {
                    if (!defaultSet[i].isChecked) {
                        defaultSet[0].isChecked = false; break
                    }
                    if (i == defaultSet.size - 1) defaultSet[0].isChecked = true
                }
                _categorySort.postValue(defaultSet)
            }
        }
    }

    companion object {
        private val TAG = "CalendarViewModel"
    }
}