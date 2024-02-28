package info.deckermail.vehicle_administration.backend

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest
@Import(TestBackendApplication::class)
internal class BackendApplicationTests {

    @Test
    internal fun contextLoads() {
    }

}
