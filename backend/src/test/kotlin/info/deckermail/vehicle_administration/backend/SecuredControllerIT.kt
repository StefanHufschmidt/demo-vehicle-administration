package info.deckermail.vehicle_administration.backend

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@FullIntegrationTest
@Sql(
    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    statements = [
        "DELETE FROM users_roles;",
        "DELETE FROM users;",
    ]
)
internal class SecuredControllerIT {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    internal fun `should be secured`() {
        mockMvc.get("/web/ping") {
            with(user("foo").password("bar").roles("USER", "ADMIN"))
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    @Sql(
        statements = [
            "INSERT INTO users (id, username, password, created) VALUES ('40e6215d-b5c6-4896-987c-f30f3678f608'::uuid, 'foobar', 'superSecurePassword', now())"
        ]
    )
    internal fun `should work with user inside db`() {
        mockMvc.get("/web/ping") {
            headers {
                header(HttpHeaders.AUTHORIZATION, "Basic Zm9vYmFyOnN1cGVyU2VjdXJlUGFzc3dvcmQ=")
            }
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    internal fun `should be blocked for unauthenticated requests`() {
        mockMvc.get("/web/ping")
            .andExpect {
                status { isUnauthorized() }
            }
    }
}