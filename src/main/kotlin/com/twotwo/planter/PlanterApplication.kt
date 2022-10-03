package com.twotwo.planter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PlanterApplication

fun main(args: Array<String>) {
    runApplication<PlanterApplication>(*args)
}
