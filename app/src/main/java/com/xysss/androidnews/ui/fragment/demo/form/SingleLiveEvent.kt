package com.xysss.androidnews.ui.fragment.demo.form

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Author:bysd-2
 * Time:2021/7/2010:51
 */
open class SingleLiveEvent<T> : MutableLiveData<T?>() {
    private val mPending = AtomicBoolean(false)

    //  @MainThread
    //    public void observe(LifecycleOwner owner, final Observer<T> observer) {
    @MainThread
    fun mObserve(owner: LifecycleOwner?, observer: Observer<T?>) {
        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.")
        }

        // Observe the internal MutableLiveData
        super.observe(owner!!, Observer { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }

    companion object {
        private const val TAG = "SingleLiveEvent"
    }
}