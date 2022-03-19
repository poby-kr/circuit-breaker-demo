package com.gorany.circuitbreakerdemo.api

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Logger

@RestController
class RandomController {

    private val log = LoggerFactory.getLogger(Logger::class.java)

    @GetMapping("/api/random/{value}")
    fun responseGreenOrRed(@PathVariable("value") value: Int): ResponseEntity<*> {

        val result = value % 2 == 0

        log.info("value: $value / result : $result")

        return if(result)
            ResponseEntity("success", HttpStatus.OK)
        else
            ResponseEntity("fail", HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @GetMapping("/api/random/{value}/2")
    fun responseGreenOrRed2(@PathVariable("value") value: Int): ResponseEntity<*> {

        val result = value % 2 == 0

        log.info("value: $value / result : $result")

        return if(result)
            ResponseEntity("success", HttpStatus.OK)
        else
            ResponseEntity("fail", HttpStatus.INTERNAL_SERVER_ERROR)
    }
}