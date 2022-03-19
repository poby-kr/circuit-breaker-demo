package com.gorany.circuitbreakerdemo.service

import com.gorany.circuitbreakerdemo.config.CustomCircuitBreakerConfig
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class RequestService {

    companion object {

        private val circuitBreaker = CustomCircuitBreakerConfig.circuitBreaker("demo")
        private val circuitBreaker2 = CustomCircuitBreakerConfig.circuitBreaker("demo2")

        private val restTemplate = RestTemplate()
    }

    fun call(): String =
        circuitBreaker.executeCallable {
            val value = (0..10_000).random()

                val response = restTemplate.exchange(
                    UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/random/$value").toUriString(),
                    HttpMethod.GET,
                    null,
                    String::class.java)

                response.body!!
        }

    fun call2(): String =
        circuitBreaker2.executeCallable {
            val value = (0..10_000).random()

            val response = restTemplate.exchange(
                UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/random/$value/2").toUriString(),
                HttpMethod.GET,
                null,
                String::class.java)

            response.body!!
        }
}