package info.deckermail.vehicle_administration.backend

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
@DirtiesContext
@AutoConfigureMockMvc
@ActiveProfiles("integrationTest")
@Import(
    AsyncCoroutinesTestDispatcherConfig::class,
    TestBackendApplication::class,
    MailHogConfig::class
)
internal annotation class FullIntegrationTest
