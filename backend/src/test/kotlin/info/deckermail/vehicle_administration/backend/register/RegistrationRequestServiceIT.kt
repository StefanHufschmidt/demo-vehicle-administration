package info.deckermail.vehicle_administration.backend.register

import info.deckermail.vehicle_administration.backend.FullIntegrationTest
import info.deckermail.vehicle_administration.backend.MailHogTestClient
import jakarta.mail.internet.InternetAddress
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext

@FullIntegrationTest
internal class RegistrationRequestServiceIT {

    @Autowired
    private lateinit var registrationRequestService: RegistrationRequestService

    @Autowired
    private lateinit var registrationRequestRepository: RegistrationRequestRepository

    @Autowired
    private lateinit var mailHogTestClient: MailHogTestClient

    @Test
    @DirtiesContext
    internal fun `should send email with correct registration code`() = runTest {
        // when: request registration
        registrationRequestService.processRegistrationRequest(
            RegistrationRequestData(
                "foobar",
                "hello",
                InternetAddress("foobar@hello.com"),
            )
        )

        // then: entry inside registration request table should have been made
        assertTrue(registrationRequestRepository.existsByUsernameOrEmail("foobar", "foobar@hello.com"))
        assertTrue(registrationRequestRepository.existsByUsernameOrEmail("", "foobar@hello.com"))
        assertTrue(registrationRequestRepository.existsByUsernameOrEmail("foobar", ""))

        // and: email with code should have been sent to the correct email address
        val messages = mailHogTestClient.readMessages()
        assertEquals(1, messages.size)
        val message = messages.first()
        assertEquals("Your registration code for Vehicle Administration", message.subject)
        assertTrue(message.to.contains("foobar@hello.com"))
        val codeRegex = Regex("Here is your registration code: '(?<code>[1-9A-Z]{6})'\\.")
        assertTrue(message.body.matches(codeRegex))
        val codeFromEmail = codeRegex.find(message.body)!!.groups["code"]!!.value
        val foundRequestByCodeFromEmail = registrationRequestRepository.findByCode(codeFromEmail)!!
        assertEquals(
            RegistrationRequestEntity(
                codeFromEmail,
                "foobar",
                "hello",
                "foobar@hello.com"
            ),
            foundRequestByCodeFromEmail
        )
    }
}