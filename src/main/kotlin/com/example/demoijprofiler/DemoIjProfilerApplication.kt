package com.example.demoijprofiler

import io.pyroscope.http.Format
import io.pyroscope.javaagent.EventType
import io.pyroscope.javaagent.PyroscopeAgent
import io.pyroscope.javaagent.config.Config
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.HashMap
import java.util.Random
import java.util.UUID
import java.util.stream.IntStream


@SpringBootApplication
class DemoIjProfilerApplication

fun main(args: Array<String>) {
    PyroscopeAgent.start(
        Config.Builder()
            .setApplicationName("spring-boot-demo")
            .setProfilingEvent(EventType.ITIMER)
            .setProfilingAlloc("512k")
            .setProfilingLock("10ms")
            .setFormat(Format.JFR)
            .setServerAddress("http://172.17.0.2:4040")
            .build()
    )
    runApplication<DemoIjProfilerApplication>(*args)
}

@RestController
class DemoController(
    private val heavyLoadService: HeavyLoadService
) {

    @GetMapping("/memoryLoad")
    fun memoryLoad(@RequestParam limit: Int) {
        heavyLoadService.memoryLoad(limit)
    }

    @GetMapping("/threadSleepers")
    fun threadSleepers(@RequestParam number: Int) {
        heavyLoadService.threadSleepers(number)
    }
}

@Service
class HeavyLoadService {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private val keyValueCache = HashMap<String, SomeClass>()

    fun memoryLoad(limit: Int) {
        IntStream.rangeClosed(1, limit).forEach {
            logger.info("Allocating memory, iteration $it")
            val key = UUID.randomUUID().toString()
            keyValueCache[key] = SomeClass("hello world $key")
        }
    }

    fun threadSleepers(number: Int, timeMs: Long = 50) {
        IntStream.rangeClosed(1, number).forEach {
            logger.info("Thread sleeping, iteration $it")
            Thread.sleep(timeMs)
        }
    }
}

data class SomeClass(
    val message: String
)
