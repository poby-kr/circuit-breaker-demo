package com.gorany.circuitbreakerdemo.config

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import java.time.Duration

object CustomCircuitBreakerConfig {

    //Create a custom config for a CircuitBreaker
    private fun customCircuitBreakerConfig(): CircuitBreakerConfig = CircuitBreakerConfig.custom()
        .slidingWindowSize(100)
        .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
        .automaticTransitionFromOpenToHalfOpenEnabled(true)
        .permittedNumberOfCallsInHalfOpenState(10)
        .waitDurationInOpenState(Duration.ofSeconds(120L))
        .slowCallRateThreshold(50f)
        .failureRateThreshold(50f)
        .minimumNumberOfCalls(20)
        .slowCallDurationThreshold(Duration.ofSeconds(5))
        .build()

    //Create a CircuitBreakerRegistry with a custom global config
    private fun circuitBreakerRegistry(): CircuitBreakerRegistry = CircuitBreakerRegistry.of(customCircuitBreakerConfig())

    fun circuitBreaker(name: String): CircuitBreaker = circuitBreakerRegistry()
        .circuitBreaker(name)
}