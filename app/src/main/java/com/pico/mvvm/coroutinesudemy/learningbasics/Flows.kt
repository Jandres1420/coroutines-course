package com.pico.mvvm.coroutinesudemy.learningbasics

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Locale

fun main(){
//    coldFlow()
//    cancelFlow()
//    flowOperators()
//    terminalFlowOperators()
    bufferFlow()
}

fun bufferFlow() {
    TODO("Not yet implemented")
}

fun terminalFlowOperators() {
    runBlocking {
        newTopic("Operadores Flow terminales")
        newTopic("List")
        // Converts from flow to list when all the values are processed
        val list = getDataByFlow()
//            .toList()
        println("List $list")
        newTopic("Single")
        // solo toma el primero con single
        val single = getDataByFlow()
//            .take(1)
//            .single()
        println("Single $single")

        //Toma el primero
        newTopic("First")
        val first = getDataByFlow()
//            .first()
        println("First $first")

        // it will take the last value of the flow
        newTopic("Last")
        val last = getDataByFlow()
//            .last()
        println("Last $last")

        // accumula los valores anteriores y los va sumando o pues lo que diga en la ultima linea
        newTopic("Reduce")
        val saving = getDataByFlow()
            .reduce { accumulator, value ->
                println("Accumulator: $accumulator")
                println("Value: $value")
                println("Curret Saving: ${accumulator + value}")
                accumulator + value
            }
        println("Saving: $saving")

        // el acumulador va a comenzar desde lo qe se halla puesto, en este caso lastSaving, value el indice 0
        newTopic("Fold")
        val lastSaving = saving
        val totalSaving = getDataByFlow()
            .fold(lastSaving) { acc, value ->
                println("Accumulator: $acc")
                println("Value: $value")
                println("Curret Saving: ${acc + value}")
                acc + value
            }
    }
}

fun flowOperators() {
    runBlocking {
        newTopic("Operadores Flow intermediarios")
        newTopic("Operador Map")
        getDataByFlow()
            .map {
            // in this case the last method would be the one that has the relevance from the other
            setFormat(it)
            setFormat(convertCelsToFahr(it), "F")
        }
//            .collect{ println(it) }

        //EL ORDEN DE LOS OPERADORES IMPORTA
        newTopic("Filter")
        getDataByFlow()
            .filter {
                // It will only show on screen if it < 23
                it < 23
            }
            .map {
                setFormat(it)
            }
//            .collect{ println(it) }

        // This operator is used for emiting more than only one time information, in this case is gonna do setFormat
        // and is gonna be on C and on the second line is gonna be on F
        newTopic("Transform")
        getDataByFlow()
            .transform {
                emit(setFormat(it))
                emit(setFormat(convertCelsToFahr(it), "F"))
            }
//            .collect{println(it)}

        // it will take the first n elements
        newTopic("Take")
        getDataByFlow()
            .take(3)
            .map { setFormat(it) }
            .collect{ println(it) }
    }
}

fun convertCelsToFahr(cels: Float): Float = ((cels * 9) /5 ) + 32

fun setFormat(temp: Float, degree: String = "C"):String {
    return String.format(Locale.getDefault(),
        "%.1f$degree", temp)
}

fun cancelFlow() {
    runBlocking {
        newTopic("Cancelar Flow")
        val job = launch {
            getDataByFlow().collect { println(it) }
        }
        delay(someTime()*2)
        job.cancel()
    }
}

fun coldFlow() {
    newTopic("Flows are Cold")
    runBlocking {
        val dataFlow = getDataByFlow()
        println("esperando...")
        delay(someTime())
        dataFlow.collect{println(it)}
    }

}
