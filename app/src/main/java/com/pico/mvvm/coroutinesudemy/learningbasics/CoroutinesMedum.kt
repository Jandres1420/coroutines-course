package com.pico.mvvm.coroutinesudemy.learningbasics

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.random.Random

fun main(){
    //dispatchers()
    //nestedCoroutine()
    //playingWithNestedCoroutines()
    //changeWithContext()
    basicFlows()
}

fun basicFlows() {
    newTopic("Flows básico")
    runBlocking {
        launch { getDataByFlow().collect{ println(it) } }
        launch { (1..50).forEach {
            delay(someTime()/10)
            println("Tarea 2...")
        } }
    }

}
fun getDataByFlow():Flow<Float> {
    return flow{
        (1..5).forEach{
            println("Procesando datos...")
            delay(someTime())
            emit(20 + it + Random.nextFloat())
        }
    }
}

fun changeWithContext() {
    runBlocking {
        newTopic("withContext")
        startMessage()
        withContext(newSingleThreadContext("Hilo personalizado ")){
            startMessage()
            delay(someTime())
            println("Hilo personalizo en ejecución")
            endMessage("Hilo personalizado")
        }
        withContext(Dispatchers.IO){
            startMessage()
            delay(someTime())
            println("Peticion al servidor")
            endMessage("Hilo IO Servidor")
        }
        endMessage()
    }
}

fun nestedCoroutine() {
    runBlocking {
        newTopic("Anidar")
        val job = launch {
            startMessage()
            launch {
                startMessage()
                delay(someTime())
                println("Otra tarea")
                endMessage()
            }
            launch(Dispatchers.IO) {
                startMessage()

                launch(newSingleThreadContext("CURSOS ANDROID ANT")) {
                    startMessage()
                    delay(someTime())
                    println("Otra tarea CURSOS ANDROID ANT")
                    endMessage()
                }

                delay(someTime())
                println("tarea en el servidor")
                endMessage()
            }

            var sum = 0
            (1..100).forEach {
                sum += it
                delay(someTime()/100)
            }
            println("Sum = $sum")
            endMessage()
        }
        delay(someTime()/100)
        //job.cancel()
        println("Job cancelado...")
    }
}


fun playingWithNestedCoroutines(){
    runBlocking {
        val job = launch {
            startMessage()
            println("HILO PADRE")
            launch(Dispatchers.Default){
                startMessage()
                println("HILO Default")
                endMessage("Murio hilo Default")
            }

            launch(Dispatchers.IO){
                startMessage()
                println("HILO IO")
                endMessage("Murio hilo IO")

                launch(newSingleThreadContext("Prueba Juan Pico")) {
                    startMessage()
                    println("Mi corrutina personalizada con un dispatcher jiji")
                    endMessage("Murio hilo Juan Pico")
                }
            }
            var list = listOf(1,2,3,4,5,6,7)
            var countOdd = 0
            list.forEach{ if(it % 2 == 0) countOdd++}
            println("NUMERO DE PARES EN LISTA $countOdd")
            endMessage("Hilo padre")
        }
        delay(someTime()/ 100)
        job.cancel()
        println("JOB CANCELADO")
    }
}
fun dispatchers() {
    runBlocking {
        newTopic("Dispatchers")
        launch {
            startMessage()
            println("None")
            endMessage()
        }
        launch(Dispatchers.IO) {
            startMessage()
            println("IO")
            endMessage()
        }
        launch(Dispatchers.Unconfined) {
            startMessage()
            println("Unconfined")
            endMessage()
        }
        // Main solo para android USER INTERFACE
//        launch(Dispatchers.Main) {
//            startMessage()
//            println("Unconfined")
//            endMessage()
//        }
        launch(Dispatchers.Default) {
            startMessage()
            println("Default")
            endMessage()
        }
        launch(newSingleThreadContext("Cursos Android ANT")) {
            startMessage()
            println("Mi corrutina personalizada con un dispatcher ")
            endMessage()
        }

        newSingleThreadContext("CursosAndroidANT epa").use {myContext ->
            launch(myContext){
                startMessage()
                println("Mi corrutina personalizada con un dispatcher 2")
                endMessage()
            }
        }
    }
}
