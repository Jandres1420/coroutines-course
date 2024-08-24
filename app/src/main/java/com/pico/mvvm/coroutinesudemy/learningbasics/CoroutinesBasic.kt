package com.pico.mvvm.coroutinesudemy.learningbasics

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//TODOS LOS METODOS QUE COMIENCEN CON c SON CONSTRUCTORES
fun main() {
    //globalScope()
    //suspendFun()
    newTopic("Constructores de corrutineas")
    //cRunBlocking()
    //cLaunch()
    //cAsync()
    //job()
    //deferred()
    cProduce()
    readlnOrNull()
}

fun cProduce() {
    runBlocking {
        newTopic("Produce")
        val names = produceNames()
        names.consumeEach { println(it) }
    }
}

fun CoroutineScope.produceNames(): ReceiveChannel<String> = produce{
    (1..5).forEach{ send("name$it")}
}
fun deferred() {
    runBlocking {
        newTopic("Deferred")
        val deferred = async {
            startMessage()
            delay(someTime())
            println("Deferred...")
            endMessage()
            multi(5,2)
            "Hola"
        }
        println("Deferred: $deferred")
        println("Valor del Deferred.await: ${deferred.await()}")

        val result = async {
            multi(3,3)
        }.await()
        println("Result: $result")
    }
}

fun job() {
    runBlocking {
        newTopic("Job")
        val job = launch {
            startMessage()
            delay(2_100) // someTime()
            println("Job...")
            endMessage()
        }
        println("Job $job")
        // delay(4_000) otro flujoo que seria cuando es completado en true
        println("isActive: ${job.isActive}")
        println("isCancelled: ${job.isCancelled}")
        println("isCompleted: ${job.isCompleted}")

        delay(someTime())
        println("Tarea cancelada o interrumpida")
        job.cancel()
        println("isActive: ${job.isActive}")
        println("isCancelled: ${job.isCancelled}")
        println("isCompleted: ${job.isCompleted}")
    }
}

fun cAsync() {
    newTopic("Async")
    // IMPORTANTE LA CORRUTINA ASYNC TOMA EL ULTIMO VALOR DEL COROUTINE SCOPE COMO SU VALOR, SI NO SE DEVUELVE NADA
    // DEVUELVE UNIT SI NO EL VALOR ESPECIFICADO EN LA ULTIMA LINEA DEL ASYNC
    runBlocking {
        val result = async {
            startMessage()
            delay(someTime())
            println("Async...")
            endMessage()
            1
        }
        println("Result: ${result.await()}")
    }
}

fun cLaunch() {
    runBlocking {
        newTopic("Launch")
        launch {
            startMessage()
            delay(someTime())
            println("Launch...")
            endMessage()
        }
    }
}

fun cRunBlocking() {
    newTopic("RunBlocking")
    runBlocking {
        startMessage()
        delay(someTime())
        println("runBlocking...")
        endMessage()
    }
}

fun suspendFun() {
    newTopic("Suspend")
    Thread.sleep(someTime())
    //delay(someTime())
    GlobalScope.launch {
        delay(someTime())
    }
}

fun globalScope() {
    newTopic("Global Scope")
    GlobalScope.launch {
        startMessage()
        println("Mi corrutina")
        endMessage()
    }
}

fun startMessage() {
    println("Comenzando corrutina- ${Thread.currentThread().name}-")
}

fun endMessage(info:String = "") {
    println("Corrutina- ${Thread.currentThread().name} $info - finalizada")
}
