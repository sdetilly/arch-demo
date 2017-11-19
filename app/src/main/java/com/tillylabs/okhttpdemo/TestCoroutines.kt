package com.tillylabs.okhttpdemo

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.suspendCoroutine

/**
 * Created by steven on 2017-11-18.
 */
class TestCoroutines {
    //suspend

    fun postSomething(something: Any) {
        launch(CommonPool) {
            val token = preparePostAsync()
        }
    }

    suspend fun preparePostAsync(): String{
        return suspendCoroutine {  }
    }
}