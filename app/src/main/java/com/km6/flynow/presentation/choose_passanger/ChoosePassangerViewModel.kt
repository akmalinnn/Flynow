package com.km6.flynow.presentation.choose_passanger

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChoosePassangerViewModel (private val extras: Bundle?): ViewModel() {
    val adultCountLiveData =
        MutableLiveData(0).apply {
            postValue(extras?.getInt(ChoosePassangerFragment.EXTRA_ADULT))
        }
    val childrenCountLiveData =
        MutableLiveData(0).apply {
            postValue(extras?.getInt(ChoosePassangerFragment.EXTRA_CHILDREN))
        }
    val babyCountLiveData =
        MutableLiveData(0).apply {
            postValue(extras?.getInt(ChoosePassangerFragment.EXTRA_BABY))
        }
    val totalCountLiveData =
        MutableLiveData(0).apply {
            postValue(extras?.getInt(ChoosePassangerFragment.EXTRA_TOTAL))
        }

    fun addAdult() {
        val count = (adultCountLiveData.value ?: 0) + 1
        adultCountLiveData.postValue(count)
        totalCountLiveData.postValue(totalCountLiveData.value?.plus(1))
    }

    fun minusAdult() {
        if ((adultCountLiveData.value ?: 0) > 0) {
            val count = (adultCountLiveData.value ?: 0) - 1
            adultCountLiveData.postValue(count)
            totalCountLiveData.postValue(totalCountLiveData.value?.minus(1))
        }
    }

    fun addChildren() {
        val count = (childrenCountLiveData.value ?: 0) + 1
        childrenCountLiveData.postValue(count)
        totalCountLiveData.postValue(totalCountLiveData.value?.plus(1))
    }

    fun minusChildren() {
        if ((childrenCountLiveData.value ?: 0) > 0) {
            val count = (childrenCountLiveData.value ?: 0) - 1
            childrenCountLiveData.postValue(count)
            totalCountLiveData.postValue(totalCountLiveData.value?.minus(1))
        }
    }
    fun addBaby() {
        val count = (babyCountLiveData.value ?: 0) + 1
        babyCountLiveData.postValue(count)
        totalCountLiveData.postValue(totalCountLiveData.value?.plus(1))
    }

    fun minusBaby() {
        if ((babyCountLiveData.value ?: 0) > 0) {
            val count = (babyCountLiveData.value ?: 0) - 1
            babyCountLiveData.postValue(count)
            totalCountLiveData.postValue(totalCountLiveData.value?.minus(1))
        }
    }
}