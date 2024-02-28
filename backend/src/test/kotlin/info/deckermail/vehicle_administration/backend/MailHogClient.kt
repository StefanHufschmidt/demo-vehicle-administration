package info.deckermail.vehicle_administration.backend

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.mail.internet.MimeUtility
import org.springframework.web.client.RestTemplate
import java.nio.charset.StandardCharsets

// OpenAPI for MailHog:
// https://github.com/mailhog/MailHog/blob/v1.0.1/docs/APIv2/swagger-2.0.yaml (v2)
internal class MailHogTestClient(
    private val restTemplate: RestTemplate,
    private val mailHogHttpUrl: String,
) {

    private val objectMapper = jacksonObjectMapper()

    fun readMessages(): List<MailHogMessage> {
        val mailList = objectMapper.readTree(
            restTemplate.getForObject("${mailHogHttpUrl}api/v2/messages", String::class.java)
        )

        return mailList.get("items").map { message ->
            MailHogMessage(
                subject = message.get("Content").get("Headers").get("Subject").get(0).asText(),
                body = String(
                    MimeUtility.decode(
                        message.get("Content")
                            .get("Body")
                            .asText()
                            .byteInputStream(StandardCharsets.UTF_8),
                        "quoted-printable"
                    ).readAllBytes())
                    .replace("\r\n", "\n"),
                from = message.get("Raw").get("From").asText(),
                to = message.get("Raw").get("To").map { it.textValue() }.toSet(),
            )
        }
    }
}

internal data class MailHogMessage(
    val subject: String,
    val body: String,
    val from: String,
    val to: Set<String>,
)