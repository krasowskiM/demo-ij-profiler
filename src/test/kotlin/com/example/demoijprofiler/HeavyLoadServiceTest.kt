package com.example.demoijprofiler

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.stream.IntStream

@SpringBootTest
@Disabled("Below are performance tests")
class HeavyLoadServiceTest(
    @Autowired val cut: HeavyLoadService
) {

    @Test
    fun `should invoke memory allocation function without issues`() {
        IntStream.rangeClosed(1, 1000).forEach {
            cut.memoryLoad(100)
        }
    }

    @Test
    fun `should invoke thread sleeper function without issues`() {
        IntStream.rangeClosed(1, 1000).forEach {
            cut.threadSleepers(5)
        }
    }
}