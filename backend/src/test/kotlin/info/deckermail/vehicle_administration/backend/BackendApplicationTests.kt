package info.deckermail.vehicle_administration.backend

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Import(TestBackendApplication::class)
class BackendApplicationTests {

    @Test
    fun contextLoads() {
    }

}
