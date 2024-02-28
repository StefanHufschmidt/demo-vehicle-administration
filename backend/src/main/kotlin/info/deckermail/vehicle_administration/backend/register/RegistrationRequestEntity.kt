package info.deckermail.vehicle_administration.backend.register

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.hibernate.annotations.GenericGenerator

typealias RequestCode = String

@Entity
data class RegistrationRequestEntity(
    @Id
    @GenericGenerator(name = "code", strategy = "info.deckermail.vehicle_administration.backend.register.RegistrationCodeGenerator")
    @GeneratedValue(generator = "code")
    @Column(name = "code", nullable = false)
    val code: RequestCode? = null,
    @Column(name = "username", nullable = false, unique = true)
    val username: String,
    @Column(name = "password", nullable = false)
    val password: String,
    @Column(name = "email", nullable = false, unique = true)
    val email: String,
)
