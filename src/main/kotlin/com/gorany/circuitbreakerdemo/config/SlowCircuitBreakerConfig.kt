package com.gorany.circuitbreakerdemo.config

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import java.time.Duration

object SlowCircuitBreakerConfig {

    /**
     * - Count 기반 서킷 브레이커
     * - Sliding Windows 10 EA
     * - Open -> Half Open 자동
     * - Open -> Half Open 까지 리드타임 10s
     * - Half Open 에서 허용하는 호출 10 EA
     * - 실패 비율 계산하기 위한 최소 Call 5회 (첫 4회 모두 실패하더라도 OPEN 안됨)
     * - 느린 호출 임계값 3s
     * - 느린 호출 임계치 50%
     * - 호출 실패 임계치 50%
     * */
    private fun customCircuitBreakerConfig(): CircuitBreakerConfig = CircuitBreakerConfig.custom()
        .slidingWindowSize(10)
        .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
        .automaticTransitionFromOpenToHalfOpenEnabled(true)
        .permittedNumberOfCallsInHalfOpenState(10)
        .waitDurationInOpenState(Duration.ofSeconds(10L))
        .slowCallRateThreshold(50f)
        .failureRateThreshold(50f)
        .minimumNumberOfCalls(5)
        .slowCallDurationThreshold(Duration.ofSeconds(3))
        .build()

    //Create a CircuitBreakerRegistry with a custom global config
    private fun circuitBreakerRegistry(): CircuitBreakerRegistry = CircuitBreakerRegistry.of(customCircuitBreakerConfig())

    fun circuitBreaker(name: String): CircuitBreaker = circuitBreakerRegistry()
        .circuitBreaker(name)
}