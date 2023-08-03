package sso.hyeon.wachacha

import android.content.SharedPreferences
//import android.database.Observable
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class LivePreference<T> constructor(
    private val updates: Observable<String>,
    private val preferences: SharedPreferences,
    private val key: String,
    private val defaultValue: T
) : MutableLiveData<T>() {

    private var disposable: Disposable? = null

    override fun onActive() {//관찰자 존재
        super.onActive()

        value = (preferences.all[key] as T) ?: defaultValue

        disposable = updates//걊 업데이트, 관찰자에게 알림
            .filter { t -> t == key }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableObserver<String>() {
                override fun onComplete() {
                }

                override fun onNext(t: String) {
                    postValue((preferences.all[t] as T) ?: defaultValue)
                }

                override fun onError(e: Throwable) {
                }
            })
    }

    override fun onInactive() {
        super.onInactive()
        disposable?.dispose()
    }

}