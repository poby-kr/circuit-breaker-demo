package com.gorany.circuitbreakerdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CircuitBreakerDemoApplication

fun main(args: Array<String>) {
    runApplication<CircuitBreakerDemoApplication>(*args)
}
