package com.pico.mvvm.coroutinesudemy.learningbasics

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.random.Random

private const val SEPARATOR = "===================="

fun main(){
    //lambda()
    //threads()
    coroutinesVsThreads()
}

fun coroutinesVsThreads() {
    newTopic("Corrutines vs Threads")
    runBlocking {
        (1..1_000_000).forEach {
            launch {
                delay(someTime())
                print("*")
            }
        }
    }
    // por parte de thread es muy lento y consume muchisimo
//    (1..1_000_000).forEach{
//        thread {
//            Thread.sleep(someTime())
//            println("*")
//        }
//    }
}
fun newTopic(topic: String) {
    println("\n$SEPARATOR $topic $SEPARATOR\n")
}

fun threads() {
    newTopic("Threads")
    println("THREAD ${multiThread(2,3)}")
    multiThreadLambda(2,3){
        println("THREAD+LAMBDA $it")
    }
}

fun multiThreadLambda(x: Int, y: Int, callBack:(result: Int) -> Unit) {
    var result = 0
    thread {
        Thread.sleep(someTime())
        result = x * y
        callBack(result)
    }

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

    newTopic("lambda")
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
