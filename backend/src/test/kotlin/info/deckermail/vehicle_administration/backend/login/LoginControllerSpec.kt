package info.deckermail.vehicle_administration.backend.login

import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@WebMvcTest(
    excludeAutoConfiguration = [SecurityAutoConfiguration::class]
)
internal class LoginControllerSpec {

    @MockBean
    private lateinit var authenticationManager: AuthenticationManager

    companion object {
        @JvmStatic
        @Language("JSON")
        fun provideInvalidPayload() = listOf(
            Arguments.of(
                """
                {
                  "username": "",
                  "password": "hello"
                }
                """.trimIndent()
            ),
            Arguments.of(
                """
                {
                  "username": "hello",
                  "password": ""
                }
                """.trimIndent()
            ),
            Arguments.of(
                """
                {
                  "username": "",
                  "password": ""
                }
                """.trimIndent()
            ),
            Arguments.of(
                """
                {
                  "username": "hello"
                }
                """.trimIndent()
            ),
            Arguments.of(
                """
                {
                  "password": "hello"
                }
                """.trimIndent()
            ),
            Arguments.of(
                """
                {
                }
                """.trimIndent()
            ),
        )
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @ParameterizedTest
    @MethodSource("provideInvalidPayload")
    internal fun `should reject invalid payload`(invalidPayloadString: String) {
        // expect: bad request when trying to login with invalid payload
        mockMvc.post("/login") {
            content = invalidPayloadString
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status {
                isBadRequest()
            }
        }
    }

    @Test
    internal fun `should be able to login with correct payload`() {
        // given: a correct payload
        @Language("JSON")
        val payload = """
            {
              "username": "foo",
              "password": "bar"
            }
        """.trimIndent()

        // expect: successful login
        mockMvc.post("/login") {
            content = payload
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status {
                isOk()
            }
        }
    }
}