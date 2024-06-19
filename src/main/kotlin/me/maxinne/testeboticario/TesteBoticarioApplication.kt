package me.maxinne.testeboticario

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootApplication
class TesteBoticarioApplication

fun main(args: Array<String>) {
    runApplication<TesteBoticarioApplication>(*args)
}
