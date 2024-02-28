package info.deckermail.vehicle_administration.backend

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.test.context.DynamicPropertyRegistry
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
internal class MailHogConfig {

    companion object {
        private const val MAILHOG_SMTP_PORT = 1025
        private const val MAILHOG_HTTP_PORT = 8025
    }

    private val mailHog = GenericContainer(DockerImageName.parse("mailhog/mailhog:v1.0.1"))
        .withExposedPorts(MAILHOG_HTTP_PORT, MAILHOG_SMTP_PORT)

    @Bean
    fun mailHogContainer(dynamicPropertyRegistry: DynamicPropertyRegistry): GenericContainer<*> {
        dynamicPropertyRegistry.add("spring.mail.host") { mailHog.host }
        dynamicPropertyRegistry.add("spring.mail.port") { mailHog.getMappedPort(MAILHOG_SMTP_PORT) }
        return mailHog
    }

    @Suppress("HttpUrlsUsage")
    @Bean
    fun mailHogTestClient(restTemplateBuilder: RestTemplateBuilder): MailHogTestClient =
        MailHogTestClient(
            restTemplateBuilder.build(),
            "http://${mailHog.host}:${mailHog.getMappedPort(MAILHOG_HTTP_PORT)}/"
        )
}