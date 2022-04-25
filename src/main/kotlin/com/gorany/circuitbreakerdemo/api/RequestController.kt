package com.gorany.circuitbreakerdemo.api

import com.gorany.circuitbreakerdemo.service.RequestService
import io.github.resilience4j.circuitbreaker.CallNotPermittedException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class RequestController(
    private val requestService: RequestService
) {

    companion object {
        private val log = LoggerFactory.getLogger(Logger::class.java)
    }

    @GetMapping("/req")
    fun request(): ResponseEntity<*> {

        return try {
            val result = requestService.call3()
            ResponseEntity(result, HttpStatus.OK)
        } catch (e: CallNotPermittedException) {
            log.warn("Circuit Breaker -> OPEN")
            ResponseEntity("Circuit Breaker -> OPEN", HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(e::message, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/req/2")
    fun request2(): ResponseEntity<*> {

        return try {
            val result = requestService.call2()
            ResponseEntity(result, HttpStatus.OK)
        } catch (e: CallNotPermittedException) {
            log.warn("Circuit Breaker2 -> OPEN")
            ResponseEntity("Circuit Breaker2 -> OPEN", HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(e::message, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/slow/{sec}")
    fun slowRequest(@PathVariable("sec") sec: Long) =
        try {
            ResponseEntity(requestService.slowCall(sec), HttpStatus.OK)
        } catch (e: CallNotPermittedException) {
            log.warn("Slow Circuit Breaker -> OPEN")
        } catch (e: Exception) {
            ResponseEntity(e::message, HttpStatus.INTERNAL_SERVER_ERROR)
        }
}