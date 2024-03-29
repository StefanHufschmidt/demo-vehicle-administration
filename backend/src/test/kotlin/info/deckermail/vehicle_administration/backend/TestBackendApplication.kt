package info.deckermail.vehicle_administration.backend

import org.springframework.boot.SpringApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.boot.with
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
internal class TestBackendApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.from(BackendApplication::main)
                .with(
                    TestBackendApplication::class,
                    MailHogConfig::class,
                )
                .run(*args)
        }
    }

    @Bean
    @ServiceConnection
    fun postgresContainer(): PostgreSQLContainer<*> {
        return PostgreSQLContainer(DockerImageName.parse("postgres:16.2"))
    }

}


