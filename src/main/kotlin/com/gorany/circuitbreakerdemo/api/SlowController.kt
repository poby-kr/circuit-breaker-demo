package com.gorany.circuitbreakerdemo.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class SlowController {

    companion object {
        private val log = LoggerFactory.getLogger(Logger::class.java)
    }

    @GetMapping("/api/slow/{sec}")
    fun slowResponse(@PathVariable("sec") sec: Long): ResponseEntity<String> {

        log.info("$sec 초 대기합니다.")

        Thread.sleep(sec * 1000)
        return ResponseEntity("success", HttpStatus.OK)
    }
}