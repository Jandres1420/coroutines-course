package com.pico.mvvm.coroutinesudemy

import kotlin.concurrent.thread
import kotlin.random.Random

fun main(){
    lambda()
    threads()
}

fun threads() {
    println(multiThread(2,3))
}

fun multiThread(x: Int, y: Int): Int {
    var result = 0
    thread {
        Thread.sleep(someTime())
        result = x * y
    }
    Thread.sleep(2100) //horrible
    return result
}

fun someTime(): Long  = Random.nextLong(500,2000)

fun lambda(){
    println(multi(2,3))

    multiLambda(2,3) { result ->
        println(result)
    }
}


fun multiLambda(x: Int, y: Int, callBack:(result: Int) -> Unit) {
    callBack(x * y)
}

fun multi(x: Int, y: Int): Int {
    return x * y
}
