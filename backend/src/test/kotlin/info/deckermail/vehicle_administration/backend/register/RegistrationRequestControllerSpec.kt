package info.deckermail.vehicle_administration.backend.register

import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import java.util.*

@WebMvcTest(
    excludeAutoConfiguration = [SecurityAutoConfiguration::class],
    controllers = [RegistrationRequestController::class]
)
internal class RegistrationRequestControllerSpec {

    companion object {
        @JvmStatic
        @Language("JSON")
        fun provideInvalidRegisterRequestPayload() = listOf(
            Arguments.of(
                """
                    {
                      "username": "",
                      "password": "somePassword",
                      "email": "someuser@foobar.com"
                    }
                """.trimIndent()
            ),
            Arguments.of(
                """
                    {
                      "username": "someuser",
                      "password": "",
                      "email": "someuser@foobar.com"
                    }
                """.trimIndent()
            ),
            Arguments.of(
                """
                    {
                      "username": "someuser",
                      "password": "somePassword",
                      "email": ""
                    }
                """.trimIndent()
            ),
            Arguments.of(
                """
                    {
                      "username": "someuser",
                      "password": "",
                      "email": ""
                    }
                """.trimIndent()
            ),
            Arguments.of(
                """
                    {
                      "username": "",
                      "password": "somePassword",
                      "email": ""
                    }
                """.trimIndent()
            ),
            Arguments.of(
                """
                    {
                      "username": "",
                      "password": "",
                      "email": "someuser@foobar.com"
                    }
                """.trimIndent()
            ),
            Arguments.of(
                """
                    {
                      "username": "",
                      "password": "",
                      "email": ""
                    }
                """.trimIndent()
            ),
        )
    }

    @MockBean
    private lateinit var registrationRequestService: RegistrationRequestService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    internal fun `should return conflict status code for thrown IllegalArgumentException`() {
        // given: username to use
        val username = UUID.randomUUID().toString()

        // and: user does already exist
        whenever(registrationRequestService.processRegistrationRequest(any()))
            .thenThrow(IllegalArgumentException::class.java)

        // and: the payload for the register request
        @Language("JSON")
        val payload = """
            {
              "username": "$username",
              "password": "somePassword",
              "email": "$username@foobar.com"
            }
        """.trimIndent()

        // expect: conflict when trying to request to register
        mockMvc.post("/register/request") {
            content = payload
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status {
                isConflict()
            }
        }
    }

    @ParameterizedTest
    @MethodSource("provideInvalidRegisterRequestPayload")
    internal fun `should validate request`(payload: String) {
        // expect: bad request when trying to request to register
        mockMvc.post("/register/request") {
            content = payload
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status {
                isBadRequest()
            }
        }
    }
}