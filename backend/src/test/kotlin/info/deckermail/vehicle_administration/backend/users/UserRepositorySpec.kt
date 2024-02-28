package info.deckermail.vehicle_administration.backend.users

import info.deckermail.vehicle_administration.backend.TestBackendApplication
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.*

@Testcontainers
@DataJpaTest
@DirtiesContext
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestBackendApplication::class)
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, statements = [
    "DELETE FROM users_roles;",
    "DELETE FROM users;",
    "DELETE FROM roles;",
])
internal class UserRepositorySpec {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    @Sql(statements = [
        "INSERT INTO roles VALUES (1, 1);", // Admin
        "INSERT INTO roles VALUES (2, 2);", // User
    ])
    internal fun `should persist user with roles`() {
        // given: a user to persist
        val userToPersist = UserEntity(
            username = "foo",
            password = "",
            roles = listOf(RoleEntity(2, Role.USER.roleValue), RoleEntity(1, Role.ADMIN.roleValue))
        )

        // when: persist
        val savedUserEntity = userRepository.save(userToPersist)

        // then: the user has been saved correctly
        assertTrue(savedUserEntity.roles.any { it.toRole() == Role.ADMIN })
        assertTrue(savedUserEntity.roles.any { it.toRole() == Role.USER })

        // and: reading same user again will again return the correct user
        val userEntity = userRepository.findByUsername("foo")!! // user should exist
        assertTrue(userEntity.roles.any { it.toRole() == Role.ADMIN })
        assertTrue(userEntity.roles.any { it.toRole() == Role.USER })
    }

    @Test
    @Sql(statements = [
        "INSERT INTO roles (id, role_value) VALUES (1, 1);", // Admin
        "INSERT INTO roles (id, role_value) VALUES (2, 2);", // User
        "INSERT INTO users (id, username, password, created) VALUES ('40e6215d-b5c6-4896-987c-f30f3678f608'::uuid, 'foobar', '', now());",
        "INSERT INTO users_roles (users_id, roles_id) VALUES ('40e6215d-b5c6-4896-987c-f30f3678f608'::uuid, 1);",
        "INSERT INTO users_roles (users_id, roles_id) VALUES ('40e6215d-b5c6-4896-987c-f30f3678f608'::uuid, 2);",
    ])
    internal fun `should read user with roles correctly`() {
        val userEntity = userRepository.findByUsername("foobar")!! // user should exist
        assertEquals(UUID.fromString("40e6215d-b5c6-4896-987c-f30f3678f608"), userEntity.id)
        assertTrue(userEntity.roles.any { it.toRole() == Role.ADMIN })
        assertTrue(userEntity.roles.any { it.toRole() == Role.USER })
    }
}