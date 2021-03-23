package com.savage9ishere.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savage9ishere.marsrealestate.network.MarsAPI
import com.savage9ishere.marsrealestate.network.MarsApiFilter
import com.savage9ishere.marsrealestate.network.MarsProperty
import kotlinx.coroutines.launch

enum class MarsApiStatus {
    LOADING, ERROR, DONE
}

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    private val _status = MutableLiveData<MarsApiStatus>()
    // The external immutable LiveData for the response String
    val status: LiveData<MarsApiStatus>
        get() = _status

    private val _properties = MutableLiveData<List<MarsProperty>>()
    val properties: LiveData<List<MarsProperty>>
        get() = _properties

    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty> ()
    val navigateToSelectedProperty : LiveData<MarsProperty>
        get() = _navigateToSelectedProperty

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties(MarsApiFilter.SHOW_ALL)
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    private fun getMarsRealEstateProperties(filter: MarsApiFilter) {
//        MarsAPI.retrofitService.getProperties().enqueue(
//            object: Callback<List<MarsProperty>> {
//                override fun onFailure(call: Call<List<MarsProperty>>, t: Throwable) {
//                    _response.value = "Failure: " + t.message
//                }
//
//                override fun onResponse(call: Call<List<MarsProperty>>, response: Response<List<MarsProperty>>) {
//                    _response.value = "Success: ${response.body()?.size} Mars properties retrieved"
//
//                }
//
//            })

        viewModelScope.launch {
            _status.value = MarsApiStatus.LOADING
            try {
                _properties.value = MarsAPI.retrofitService.getProperties(filter.value)
                _status.value = MarsApiStatus.DONE
            }
            catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

    fun updateFilter(filter: MarsApiFilter){
        getMarsRealEstateProperties(filter)
    }

    fun displayPropertyDetails(marsPorperty: MarsProperty) {
        _navigateToSelectedProperty.value = marsPorperty
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}
