package info.deckermail.vehicle_administration.backend.register

import jakarta.mail.internet.InternetAddress
import jakarta.validation.constraints.NotBlank

data class RegistrationRequestData(
    @field:NotBlank
    val username: String,
    @field:NotBlank
    val password: String,
    val email: InternetAddress,
)
