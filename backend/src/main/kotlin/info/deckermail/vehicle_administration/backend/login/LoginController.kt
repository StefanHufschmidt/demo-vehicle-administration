package info.deckermail.vehicle_administration.backend.login

import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login")
class LoginController(
    private val authenticationManager: AuthenticationManager,
) {

    companion object {
        @JvmStatic
        private val logger = LoggerFactory.getLogger(LoginController::class.java)
    }

    @PostMapping
    fun login(@RequestBody @Valid loginData: LoginData) {
        logger.info("Login attempt for user ${loginData.username}.")
        val authenticationRequest =
            UsernamePasswordAuthenticationToken.unauthenticated(
                loginData.username, loginData.password)
        authenticationManager.authenticate(authenticationRequest)
    }
}