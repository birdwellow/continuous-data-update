package net.fvogel.simpoc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SimPocApplication

fun main(args: Array<String>) {
    runApplication<SimPocApplication>(*args)
}
