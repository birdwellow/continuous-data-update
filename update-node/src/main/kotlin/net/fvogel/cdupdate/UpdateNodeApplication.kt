package net.fvogel.cdupdate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UpdateNodeApplication

fun main(args: Array<String>) {
    runApplication<UpdateNodeApplication>(*args)
}
