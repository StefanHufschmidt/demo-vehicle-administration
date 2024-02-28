package info.deckermail.vehicle_administration.backend.login

import jakarta.validation.constraints.NotBlank

data class LoginData(
    @field:NotBlank
    val username: String,
    @field:NotBlank
    val password: String,
)
