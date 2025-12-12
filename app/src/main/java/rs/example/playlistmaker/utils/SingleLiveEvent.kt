package rs.example.playlistmaker.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class SingleLiveEvent<T> : MutableLiveData<T> {
    constructor() : super()
    constructor(value: T) : super(value)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        var observeNew = true
        super.observe(owner) {
            if (observeNew) observeNew = false
            else observer.onChanged(it)
        }
    }
}